package webserver.model;

import rmiserver.IServer;
import rmiserver.Page;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class TestBean {
    private IServer server;
    private ArrayList<Page> searchResults;

    public TestBean(){
        System.getProperties().put("java.security.policy", "policy.all");
        try{
            server = (IServer) Naming.lookup("//localhost:7000/RMIserver");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public int getTestParam(){
        return 1;
    }

    public void setSearchResults(String keywords) throws RemoteException {
        System.out.println("Setting search results");
        this.searchResults = server.search(null, keywords.split(" "), null, 0);// TODO set logged user
    }

    public ArrayList<Page> getSearchResults() throws RemoteException { // TODO am i going to throw all exceptions?
        System.out.println("Getting search results");
        return this.searchResults;
    }
}
