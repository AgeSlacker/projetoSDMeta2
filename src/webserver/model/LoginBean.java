package webserver.model;

import rmiserver.IServer;
import rmiserver.PacketBuilder;
import webserver.Configs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginBean {
    private IServer server;
    private String name;
    private String password;

    public LoginBean(){
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
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PacketBuilder.RESULT doLogin() throws RemoteException {
        return server.login(null,name,password);
    }
}
