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
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author Kuba
 * @date 3/24/2021 9:59 AM
 */
public class LogInWindowController implements Initializable, ILogIn {
    Preferences pref = Preferences.userNodeForPackage(LogInWindowController.class);
    private LoginModel loginModel = LoginModel.getInstance();
    private boolean rememberMecheck ;
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String EMPTY = "empty";
    private static final int DEFAULTLENGTH  = 0;
    private static final String PASSWORDL = "passwordLength";


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
            pref.putInt(PASSWORDL, password.length());
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
        rememberMecheck = rememberMe.isSelected();
        rememberMe.setDisable(true);
        checkPreferences();
        rememberMe.setDisable(false);

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
     * when program is run it checks whether preferences are saved
     * in a file
     */
    private void checkPreferences() {
       String email = pref.get(EMAIL, EMPTY);
       String hashedPassword = pref.get(PASSWORD, EMPTY);
       if(!email.equals(EMPTY) && !hashedPassword.equals(EMPTY)){
           rememberMe.setSelected(true);
           emailField.setText(email);
           String passTemp = getStringWithPasswordLength();
           passwordField.setText(passTemp);
       }
    }

    private String getStringWithPasswordLength() {
        String temp = "";
       int iterations = pref.getInt(PASSWORDL, 0);
       for(int i=0; i< iterations; i++)
           temp+="a";

       return temp;
    }


    /**
     * event handler for button
     * @param actionEvent
     */
    public void logTeacherButton(ActionEvent actionEvent) {
        //if preferences are set remember to check the value from preferences
        // not test because it actually not exists
    }

    /**
     * event handler for button
     * @param actionEvent
     */
    public void logStudentButton(ActionEvent actionEvent) {
    }

    /**
     * method handles check box for saving user in preferences
     * @param actionEvent
     */
    public void rememberMe(ActionEvent actionEvent) {
        if(!passwordField.getText().isEmpty() &&
        !emailField.getText().isEmpty()){
            if(rememberMecheck ==false){
                saveUserInPreferences();
                rememberMecheck = true;
            }
            else{
                unsaveUserInPreferences();
                rememberMecheck = false;
            }
        }
    }
}
