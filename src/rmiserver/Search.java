package rmiserver;

import java.io.Serializable;
import java.util.Date;

class Search implements Serializable {
    Date time;
    String query;

    public Search(String query) {
        this.time = new Date(System.currentTimeMillis());
        this.query = query;
    }
}

