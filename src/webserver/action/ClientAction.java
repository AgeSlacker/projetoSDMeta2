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
        System.out.printf("Loging out");
        getClientBean().doLogout();
        if (session.containsKey("logged") && (boolean) session.get("logged") == true) {
            System.out.println(session.toString());
            session.clear(); // TODO sera que precisamos dos dois?
            session.invalidate();
            System.out.println(session.toString());
        }
        return SUCCESS;
    }

}
