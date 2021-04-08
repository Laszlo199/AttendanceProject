package gui.model;

import be.Student;
import be.Teacher;
import be.User;
import be.UserType;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.exception.BLLexception;
import gui.controller.TeacherViewController;

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

    public boolean verifyPassword(String email, String password, UserType userType) {
        try {
            return facadeBLL.verifyPassword(email, password, userType);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return false;
        }
    }

    public Student getStudent(String email) {
        try {
            return facadeBLL.getStudent(email);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public User getTeacher(String email) {
        try {
            return facadeBLL.getTeacher(email);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return null;
    }
}
