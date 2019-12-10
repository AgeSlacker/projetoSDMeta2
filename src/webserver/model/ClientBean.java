package webserver.model;

import rmiserver.IClient;
import rmiserver.IServer;
import rmiserver.PacketBuilder;
import webserver.Configs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    @Override
    public void printMessage(String message) throws RemoteException {
        System.out.println("RMI sent : " + message);
    }

    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public void setAdmin() throws RemoteException {
        this.isAdmin = true;
    }
}
