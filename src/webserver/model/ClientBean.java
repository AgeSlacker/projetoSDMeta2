package webserver.model;

import rmiserver.*;
import webserver.Configs;
import ws.WebSocket;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static ws.WebSocket.sockets;

public class ClientBean extends UnicastRemoteObject implements IClient {
    private IServer server;
    private String name = null;
    private String password = null;
    private boolean isAdmin = false;
    private ArrayList<String> allSeenServers = new ArrayList<>();
    WebSocketPusher webSocketPusher = new WebSocketPusher();

    public ClientBean() throws RemoteException {
        super();
        try {
            server = (IServer) Naming.lookup(Configs.RMIServerLocation);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        (new Thread(webSocketPusher)).start();
    }

    public void setPassword(String password) {
        if (password.isEmpty())
            password = null;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty())
            name = null;
        this.name = name;
    }

    public boolean getAdmin() {
        return isAdmin;
    }

    public PacketBuilder.RESULT doLogin() throws RemoteException {
        return server.login(this, name, password);
    }

    public void doLogout() {
        try {
            server.unregister(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printMessage(String message) throws RemoteException {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                int tries = 20;
                while (tries-- > 0) {
                    WebSocket webSocket = sockets.get(name);
                    if (webSocket != null) {
                        try {
                            webSocket.sendMessage("NOTIFICATION|" + message);
                            return;
                        } catch (IllegalStateException e) {
                            // no websocket, try latter
                        }
                    }
                    try {
                        System.out.println("Retrying...");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();

    }

    @Override
    public void updateAdminScreen(AdminData adminData) throws RemoteException {
        String topPagesTable = buildTopPagesTable(adminData.topPages);
        String topSearchesTable = buildTopSearchesTable(adminData.topSearches);
        String multicastServers = buildMulticastServersTable(adminData.servers);
        System.out.println("New table: " + topPagesTable);
        synchronized (webSocketPusher) {
            webSocketPusher.topPageTable = topPagesTable;
            webSocketPusher.topSearchesTable = topSearchesTable;
            webSocketPusher.multicastSearchesTable = multicastServers;
            webSocketPusher.notify();
        }

    }

    private String buildTopPagesTable(ArrayList<TopPage> topPages) {
        StringBuilder sb = new StringBuilder();
        sb.append("TOP_PAGES|");
        for (TopPage p : topPages) {
            sb.append("<tr><td>" + p.count + "</td><td>" + p.url + "</td></tr>");
        }
        return sb.toString();
    }

    private String buildTopSearchesTable(ArrayList<TopSearch> topSearches) {
        StringBuilder sb = new StringBuilder();
        sb.append("TOP_SEARCHES|");
        for (TopSearch search : topSearches) {
            sb.append("<tr><td>" + search.count + "</td><td>" + search.search + "</td></tr>");
        }
        return sb.toString();
    }

    private String buildMulticastServersTable(ArrayList<String> servers) {
        StringBuilder sb = new StringBuilder();
        sb.append("ACTIVE_SERVERS|");
        for (String server : servers) {
            if (!allSeenServers.contains(server)) {
                allSeenServers.add(server);
            }
            sb.append("<tr><td class='text-success'>" + server + "</td></tr>");
        }

        for (String server : allSeenServers) {
            if (!servers.contains(server)) {
                // This server went offline
                sb.append("<tr><td class='text-danger'>" + server + "</td></tr>");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public void setAdmin() throws RemoteException {
        this.isAdmin = true;
    }

    class WebSocketPusher implements Runnable {

        String topPageTable;
        String topSearchesTable;
        String multicastSearchesTable;
        int sleepTimeSeconds = 1;
        int tries = 10;

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        wait(); //wait for new updates
                        tries = 10;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (tries-- > 0) {
                        WebSocket webSocket = sockets.get(name);
                        if (webSocket != null) {
                            try {
                                System.out.println("Sending table: " + topPageTable);
                                webSocket.sendMessage(topPageTable);
                                webSocket.sendMessage(topSearchesTable);
                                webSocket.sendMessage(multicastSearchesTable);
                                System.out.println("[WebSocketSender] sent successfully.");
                            } catch (IllegalStateException e) {
                                System.out.println("That illegal expression");
                                continue;
                            }
                            break;
                        }
                        try {
                            System.out.println("Retrying...");
                            wait(sleepTimeSeconds * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
