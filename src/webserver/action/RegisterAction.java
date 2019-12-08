package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import webserver.model.RegisterBean;

import java.util.Map;

public class RegisterAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;

    @Override
    public String execute() throws Exception {
        session.remove("errorUserExists");
        switch (getRegisterBean().tryRegister()) {
            case SUCCESS:
                return SUCCESS;
            case ER_USER_EXISTS:
                session.put("errorUserExists",true);
                return ERROR;
            default:
                return ERROR;
        }
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setRegisterBean(RegisterBean registerBean){
        session.put("registerBean",registerBean);
    }

    public RegisterBean getRegisterBean(){
        if (!session.containsKey("registerBean"))
            setRegisterBean(new RegisterBean());
        return (RegisterBean) session.get("registerBean");
    }
}
