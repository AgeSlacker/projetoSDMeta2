package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import webserver.model.TestBean;

import java.util.Map;

public class TestAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    @Override
    public String execute() throws Exception {
        System.out.println("Action ran");
        getTestBean().setSearchResults("latina");
        return SUCCESS;
    }
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public TestBean getTestBean() {
        if (!session.containsKey("testBean"))
            this.setTestBean(new TestBean());
        return (TestBean) session.get("testBean");
    }

    public void setTestBean(TestBean testBean) {
        this.session.put("testBean", testBean);
    }
}
