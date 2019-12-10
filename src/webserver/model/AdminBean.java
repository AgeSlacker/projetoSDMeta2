package webserver.model;

import rmiserver.IServer;
import rmiserver.PacketBuilder;

import java.rmi.RemoteException;

public class AdminBean extends BaseBean {
    String indexUrl;

    public AdminBean(IServer server) {
        super(server);
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public PacketBuilder.RESULT doIndexRequest() throws RemoteException {
        return server.indexRequest(indexUrl);
    }
}
