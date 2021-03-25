package gui.controller;

import bll.util.PasswordHasher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.controller.ILogIn;
import gui.model.LoginModel;
import gui.util.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

/**
 * @author Kuba
 * @date 3/24/2021 9:59 AM
 */
public class LogInWindowController implements Initializable, ILogIn {
    Preferences pref = Preferences.userNodeForPackage(LogInWindowController.class);
    private LoginModel loginModel = LoginModel.getInstance();


    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXCheckBox rememberMe;
    @FXML
    private JFXButton logInTeacher;
    @FXML
    private JFXButton logInStudent;

    @Override
    public boolean LogIn(String email, String password) {
        return false;
    }

    @Override
    public void saveUserInPreferences() {
        String email = emailField.getText();
        String password  = passwordField.getText();
        if(!email.isEmpty() && !password.isEmpty()){
            pref.put("email", emailField.getText());
            String hashedPassword = loginModel.getHashedPassword(password);
            pref.put("password", hashedPassword );
        }
    }

    @Override
    public void unsaveUserInPreferences() {
        pref.remove("email");
        pref.remove("password");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*

        String salt = PasswordHasher.generateSalt(512).get();
        String testPass = "blabla";

        String key =  PasswordHasher.hashPassword(testPass, salt).get();
        System.out.println(" hashed: " + key);

        boolean verify = PasswordHasher.verifyPassword(testPass,
                key, salt);
        System.out.println("password is:" +verify);

         */
    }


    /**
     * event handler for button
     * @param actionEvent
     */
    public void logTeacherButton(ActionEvent actionEvent) {
    }

    /**
     * event handler for button
     * @param actionEvent
     */
    public void logStudentButton(ActionEvent actionEvent) {
    }
}
