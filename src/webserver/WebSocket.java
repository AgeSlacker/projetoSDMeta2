package webserver;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/notifications")
public class WebSocket {
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<WebSocket> connections = new CopyOnWriteArraySet<>();
    private Session session;

    public WebSocket() {

    }

    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.add(this);
    }

    @OnClose
    public void end() {
        connections.remove(this);
    }

    @OnMessage
    public void incoming(String message) { // we should never trust the client , and sensitive HTML // should be replaced with &lt; &gt; &quot; &amp; broadcast ("[" + nickname + "]" + message );
    }
}
