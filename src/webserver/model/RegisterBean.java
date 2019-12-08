package webserver.model;

import rmiserver.IServer;
import rmiserver.PacketBuilder;
import webserver.Configs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RegisterBean {
    IServer server;
    String name;
    String password;

    public RegisterBean(){
        try {
            server = (IServer) Naming.lookup(Configs.RMIServerLocation);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public PacketBuilder.RESULT tryRegister() throws RemoteException {
        return server.register(null,name,password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
