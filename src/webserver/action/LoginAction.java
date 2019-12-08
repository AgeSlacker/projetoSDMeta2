package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import sun.rmi.runtime.Log;
import webserver.model.LoginBean;

import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
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

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public LoginBean getLoginBean(){
        if (!session.containsKey("loginBean")){
            this.setLoginBean(new LoginBean());
        }
        return (LoginBean) session.get("loginBean");
    }

    public void setLoginBean(LoginBean loginBean) {
        this.session.put("loginBean", loginBean);
    }
}
