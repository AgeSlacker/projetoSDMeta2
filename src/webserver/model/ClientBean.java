package webserver.model;

import rmiserver.IClient;
import rmiserver.IServer;
import rmiserver.PacketBuilder;
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
        String finalMessage = message;
        ArrayList<String> messages;
        WebSocket webSocket = sockets.get(name);
        if (webSocket != null) {
            webSocket.sendMessage(finalMessage);
        } else {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    int tries = 500;
                    while (tries-- > 0) {
                        WebSocket webSocket = sockets.get(name);
                        if (webSocket != null) {
                            webSocket.sendMessage(finalMessage);
                            return;
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
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public void setAdmin() throws RemoteException {
        this.isAdmin = true;
    }
    /*
    boolean rebindServer() {
        while (retries < 60) {
            try {
                this.server = (IServer) Naming.lookup("//localhost:7000/RMIserver");
                System.out.println("Waiting for server...");
                if (this.username != null) {
                    this.server.setLogged(this, username);
                }
                return true; // rebound successfully
            } catch (NotBoundException e) {

            } catch (MalformedURLException e) {
                //e.printStackTrace();
            } catch (RemoteException e) {
                //e.printStackTrace();
            }
            retries++;
            System.out.println("Rebind failed (" + retries + ")");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Servers must be down. Sorry for the inconvenience");
        return false;
    }*/
}
