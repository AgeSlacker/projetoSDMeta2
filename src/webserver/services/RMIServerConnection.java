package webserver.services;

import rmiserver.IClient;
import rmiserver.IServer;
import webserver.Configs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerConnection extends UnicastRemoteObject implements IClient {
    IServer server;
    protected RMIServerConnection() throws RemoteException {
        try {
            this.server = (IServer) Naming.lookup(Configs.RMIServerLocation);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printMessage(String message) throws RemoteException {

    }

    @Override
    public boolean isAlive() throws RemoteException {
        return false;
    }

    @Override
    public void setAdmin() throws RemoteException {

    }
}
