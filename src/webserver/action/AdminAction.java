package webserver.action;

import webserver.model.AdminBean;

import java.rmi.RemoteException;

public class AdminAction extends BaseAction {
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String adminEnter() {
        getAdminBean().setUsers();
        try {
            getServer().adminInPage(getClientBean().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String indexNewPage() {
        try {
            getAdminBean().doIndexRequest();
            return SUCCESS;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    public String grantAdmin() {
        getAdminBean().grantAdmin();
        return SUCCESS;
    }

    public AdminBean getAdminBean() {
        if (!session.containsKey("adminBean"))
            setAdminBean(new AdminBean(getServer()));
        return (AdminBean) session.get("adminBean");
    }

    public void setAdminBean(AdminBean adminBean) {
        session.put("adminBean", adminBean);
    }

}
