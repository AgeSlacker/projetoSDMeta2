package webserver.model;

import rmiserver.IServer;
import webserver.Configs;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.BitSet;

public abstract class BaseBean {
    IServer server = null;

    public BaseBean(){
        if (server == null){
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
    }

    public BaseBean(IServer server){
        this.server = server;
    }
}
