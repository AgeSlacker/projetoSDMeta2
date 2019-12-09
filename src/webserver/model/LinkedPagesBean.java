package webserver.model;

import rmiserver.IServer;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LinkedPagesBean extends BaseBean{

    ArrayList<String> linkList;
    String link;

    public ArrayList<String> getLinkList() {
        return linkList;
    }

    public void setLinkList() {
        try {
            this.linkList = server.getHyperLinks(link);
            System.out.println("Got links from RMI");
            System.out.println(linkList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LinkedPagesBean(IServer server){
        super(server);
    }
}
