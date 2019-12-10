package webserver.action;

public class ClientAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String login() throws Exception {
        System.out.println("Execute method");
        session.remove("noUserError");
        session.remove("wrongPassError");
        switch (getClientBean().doLogin()) {
            case SUCCESS:
                session.put("logged", true);
                return SUCCESS;
            case ER_NO_USER:
                session.put("noUserError", true);
                return ERROR;
            case ER_WRONG_PASS:
                session.put("wrongPassError", true);
                return ERROR;
            default:
                return ERROR;
        }
    }

    public String logout() {
        System.out.printf("Logiing out");
        if (session.containsKey("logged") && (boolean) session.get("logged") == true) {
            session.clear();
        }
        return SUCCESS;
    }

}
