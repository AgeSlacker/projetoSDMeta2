package webserver.model;

import rmiserver.IServer;
import rmiserver.Page;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SearchBean {
    private IServer server;
    private ArrayList<Page> searchResults;
    private String searchTerms ;

    public SearchBean(){
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

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
        System.out.println("Setting search terms to " + searchTerms);
    }

    public void setSearchResults(String user) throws RemoteException {
        System.out.println("Setting search results");
        this.searchResults = server.search(null, searchTerms.split(" "), user, 0);// TODO set logged user
    }

    public ArrayList<Page> getSearchResults() throws RemoteException { // TODO am i going to throw all exceptions?
        System.out.println("Getting search results");
        for (Page p : searchResults) {
            System.out.println(p.getName() + p.getUrl() + p.getDescription());
        }
        return this.searchResults;
    }
}
