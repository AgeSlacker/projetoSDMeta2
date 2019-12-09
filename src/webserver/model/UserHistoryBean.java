package webserver.model;

import rmiserver.IServer;
import rmiserver.Search;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserHistoryBean extends BaseBean {
    ArrayList<Search> list;

    public UserHistoryBean(IServer server){
        super(server);
    }

    public ArrayList<Search> getList() {
        System.out.println("List gotten");
        System.out.printf(list.toString());
        return list;
    }

    public void setList(String user) {
        System.out.println("List being set for " + user);
        try {
            this.list = server.getUserHistory(null,user);
            Collections.sort(list, new Comparator<Search>() {
                @Override
                public int compare(Search o1, Search o2) {
                    return  o2.getDate().compareTo(o1.getDate());
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
