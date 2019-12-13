package ws;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws")
public class WebSocket {
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    public static final ConcurrentHashMap<String, WebSocket> sockets = new ConcurrentHashMap<>();

    private Session session;
    private boolean firstMessage = true;
    private String username;

    public WebSocket() {

    }

    @OnOpen
    public void start(Session session) {
        this.session = session;
    }

    @OnClose
    public void end() {

    }

    @OnMessage
    public void incoming(String message) {
        if (firstMessage) {
            firstMessage = false;
            username = message;
            //ServerEndpointConfig.Configurator configurator = new ServerEndpointConfig.Configurator();
            sockets.put(username, this);

        }
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
