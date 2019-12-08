package webserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import webserver.model.SearchBean;

import java.util.Map;

public class SearchAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    @Override
    public String execute() throws Exception {
        if (getSearchBean().getSearchTerms().isEmpty()){
            System.out.println(session.toString());
            this.session.put("emptySearch",true);
            this.session.put("name","TEST");
            System.out.println("returning error");
            return ERROR;
        }
        getSearchBean().setSearchResults();
        return SUCCESS;
    }
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public SearchBean getSearchBean() {
        if (!session.containsKey("searchBean"))
            this.setSearchBean(new SearchBean());
        return (SearchBean) session.get("searchBean");
    }

    public void setSearchBean(SearchBean searchBean) {
        this.session.put("searchBean", searchBean);
    }
}
