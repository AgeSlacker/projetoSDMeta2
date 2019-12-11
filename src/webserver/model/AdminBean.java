package webserver.model;

import rmiserver.IServer;
import rmiserver.PacketBuilder;
import rmiserver.User;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class AdminBean extends BaseBean {
    String indexUrl;
    ArrayList<User> users;
    String grantedUsername;

    public AdminBean(IServer server) {
        super(server);
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public void setGrantedUsername(String grantedUsername) {
        this.grantedUsername = grantedUsername;
    }

    public void grantAdmin() {
        try {
            System.out.println("Granting admin to " + grantedUsername);
            server.grantAdmin(null, grantedUsername);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers() {
        try {
            this.users = server.listUsers(null);
            users.forEach(u -> System.out.println(u.getUsername() + " " + u.isAdmin()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public PacketBuilder.RESULT doIndexRequest() throws RemoteException {
        return server.indexRequest(indexUrl);
    }


}
