package webserver.action;

import webserver.model.ClientBean;
import webserver.model.SearchBean;

public class SearchAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        session.remove("emptySearch");
        if (getSearchBean().getSearchTerms().isEmpty()) {
            System.out.println(session.toString());
            this.session.put("emptySearch", true);
            this.session.put("name", "TEST");
            System.out.println("returning error");
            return ERROR;
        }
        ClientBean clientBean = getClientBean();
        String user = clientBean != null ? clientBean.getName() : null;
        getSearchBean().setSearchResults(user);
        return SUCCESS;
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
