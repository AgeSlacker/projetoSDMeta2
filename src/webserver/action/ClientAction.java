package webserver.action;

public class ClientAction extends BaseAction {

    private String code = "";

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

    public String loginWithFacebook() {
        getClientBean().doLoginWithFacebook();
        return SUCCESS;
    }

    public String receiveCode() {
        System.out.println("Code received...");
        boolean successful = getClientBean().doVerification();
        if (successful) {
            System.out.println("Login with Facebook successful");
            this.session.put("logged", true);
            return SUCCESS;
        } else
            return ERROR;
    }

    public void setCode(String code) {
        System.out.println("Setting code");
        this.code = code;
        getClientBean().setVerifyCode(this.code);
    }
}
