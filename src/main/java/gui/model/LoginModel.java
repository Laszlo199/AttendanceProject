package gui.model;

import bll.FacadeBLL;
import bll.IFacadeBLL;

/**
 * @author Kuba
 * @date 3/24/2021 1:24 PM
 */
public class LoginModel {
    private static LoginModel loginModel;
    private IFacadeBLL facadeBLL = FacadeBLL.getInstance();

    public static LoginModel getInstance(){
        if(loginModel==null)
            loginModel = new LoginModel();
        return loginModel;
    }

    private LoginModel() {
    }

    public boolean establishedConnection() {
        return facadeBLL.establishedConnection();
    }

    public String getHashedPassword(String password) {
      return   facadeBLL.getHashedPassword( password);
    }
}
