package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import rmiserver.IServer;
import webserver.Configs;
import webserver.model.ClientBean;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public abstract class BaseAction extends ActionSupport implements SessionAware {
    protected Map<String, Object> session;

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setServer(IServer server) {
        session.put("server", server);
    }

    public IServer getServer() {
        if (!session.containsKey("server")) {
            try {
                IServer server = (IServer) Naming.lookup(Configs.RMIServerLocation);
                session.put("server", server);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return (IServer) session.get("server");
    }

    public ClientBean getClientBean() {
        if (!session.containsKey("clientBean")) {
            try {
                this.setClientBean(new ClientBean());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return (ClientBean) session.get("clientBean");
    }

    public void setClientBean(ClientBean clientBean) {
        this.session.put("clientBean", clientBean);
    }
}
