package gui.model;

import be.Student;
import be.UserType;
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

    public boolean verifyPassword(String email, String password,
                                  UserType userType) {
        return facadeBLL.verifyPassword(email, password, userType);
    }

    public Student getStudent(String email) {
        return facadeBLL.getStudent(email);
    }
}
