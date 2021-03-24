package gui.model;

/**
 * @author Kuba
 * @date 3/24/2021 1:24 PM
 */
public class LoginModel {
    private static LoginModel loginModel;

    public static LoginModel getInstance(){
        if(loginModel==null)
            loginModel = new LoginModel();
        return loginModel;
    }

    private LoginModel() {
    }
}
