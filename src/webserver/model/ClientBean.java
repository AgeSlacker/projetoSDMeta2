package webserver.model;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuthService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rmiserver.*;
import uc.sd.apis.FacebookApi2;
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
    private static final String NETWORK_NAME = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    private static final Token EMPTY_TOKEN = null;
    WebSocketPusher webSocketPusher = new WebSocketPusher();
    OAuthService service;

    private String authorizationUrl;
    private String verifyCode;

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
        String apiKey = "2951392934911594";
        String apiSecret = "68cd95099080e8c52678cd1ce432b163";
        service = new ServiceBuilder()
                .provider(FacebookApi2.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback("https://localhost:443/receiveCode.action") // Do not change this.
                .scope("public_profile")
                .build();
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

    public void doLoginWithFacebook() {
        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize Scribe here:");
        this.authorizationUrl = authorizationUrl;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public boolean doVerification() {
        Verifier verifier = new Verifier(this.verifyCode);
        System.out.println();
        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println();
        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println();

        if (response.getCode() == 200) {
            // Try to login user
            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
                this.name = (String) jsonObject.get("name");
                System.out.println("Name from json:" + this.name);
                // Security through obfuscation rulezz!
                this.password = "8uh8unuyb87hhsdaiuhasiud98asu e982e9asj d9masjd oasd89sa9ads";
                PacketBuilder.RESULT result = server.login(this, name, password);
                if (result == PacketBuilder.RESULT.ER_NO_USER) {
                    System.out.println("Need to register first");
                    server.register(this, name, password);
                    server.login(this, name, password);
                    return true;
                }
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setVerifyCode(String code) {
        this.verifyCode = code;
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
