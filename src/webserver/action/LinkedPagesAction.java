package webserver.action;

import webserver.model.LinkedPagesBean;

public class LinkedPagesAction extends BaseAction {
    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    public void setLinkedPagesBean(LinkedPagesBean linkedPagesBean){
        session.put("linkedPagesBean",linkedPagesBean);
    }

    public LinkedPagesBean getLinkedPagesBean(){
        if (!session.containsKey("linkedPagesBean"))
            setLinkedPagesBean(new LinkedPagesBean(getServer()));
        return (LinkedPagesBean) session.get("linkedPagesBean");
    }
}
