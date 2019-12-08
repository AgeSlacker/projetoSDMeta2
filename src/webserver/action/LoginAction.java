package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import webserver.model.LoginBean;

import java.rmi.RemoteException;
import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        System.out.println("Execute method");
        switch (getLoginBean().doLogin()) {
            case SUCCESS:
                session.put("logged",true);
                return SUCCESS;
            case ER_NO_USER:
                session.put("noUserError",true);
                return ERROR;
            case ER_WRONG_PASS:
                session.put("wrongPassError",true);
                return ERROR;
        }
        return SUCCESS;
    }

    public String logout(){
        System.out.printf("Logiing out");
        if(session.containsKey("logged") && (boolean)session.get("logged") == true){
            session.clear();
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public LoginBean getLoginBean(){
        if (!session.containsKey("loginBean")){
            try {
                this.setLoginBean(new LoginBean());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return (LoginBean) session.get("loginBean");
    }

    public void setLoginBean(LoginBean loginBean) {
        this.session.put("loginBean", loginBean);
    }
}
