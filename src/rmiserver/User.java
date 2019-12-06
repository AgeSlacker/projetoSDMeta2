package rmiserver;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.util.ArrayList;

class User implements Serializable {
    String username;
    String password;
    boolean admin;
    ArrayList<Search> search_history = new ArrayList<>();
    ArrayList<DatagramPacket> pendingData = new ArrayList<>();

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.admin = isAdmin;
    }

    @Override
    public String toString() {
        return " Admin: " + this.admin + " " + this.username + " " + this.password;
    }
}
