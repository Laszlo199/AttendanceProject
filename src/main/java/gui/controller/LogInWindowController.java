package gui.controller;

import be.Student;
import be.UserType;
import bll.util.PasswordHasher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.command.Command;
import gui.command.LogInAdmin;
import gui.command.LogInStudent;
import gui.command.LogInTeacher;
import gui.controller.ILogIn;
import gui.model.LoginModel;
import gui.util.AlertDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
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
    private Label logAdmin;
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
       // String password  = passwordField.getText();
        if(!email.isEmpty()){
            pref.put("email", emailField.getText());
        }
    }

    @Override
    public void unsaveUserInPreferences() {
        pref.remove("email");
       // pref.remove("password");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rememberMecheck = rememberMe.isSelected();
        rememberMe.setDisable(true);
        checkPreferences();
        rememberMe.setDisable(false);
        logAdmin.getStyleClass().add("adminLabel");

        String salt = PasswordHasher.generateSalt(512).get();
        String testPass = "admin";

        String key =  PasswordHasher.hashPassword(testPass, salt).get();
        System.out.println(" hashed: " + key);


       // String testPass = "pumpkin";
       // String hashed = PasswordHasher.getHashedPassword(testPass);

        boolean verify = PasswordHasher.verifyPassword(testPass, key, salt);
        System.out.println("password is:" +verify);
        System.out.println("below hashed");
        System.out.println(key);
        System.out.println("below salt");
        System.out.println(salt);



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
     * method is responsible for handling logging in
     * It follows command pattern
     * @param actionEvent
     * @param command
     */
    private void executeLogIn(ActionEvent actionEvent, Command command){
        System.out.println("we got to execute log in method");
        //close this stage
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        command.closeCurrentStage(thisStage);
        //open new stage
        try {
            command.logIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logAdmin(MouseEvent mouseEvent) {
        if(loginModel.verifyPassword(emailField.getText(),
                passwordField.getText(), UserType.ADMIN) && mouseEvent.getClickCount()<2){
            Command command = new LogInAdmin();
            Stage thisStage = (Stage) logAdmin.getScene().getWindow();
            command.closeCurrentStage(thisStage);
            try {
                command.logIn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * event handler for button
     * @param actionEvent
     */
    public void logStudentButton(ActionEvent actionEvent) {
        if(loginModel.verifyPassword(emailField.getText(),
                passwordField.getText(), UserType.STUDENT)){
            //we need to get student
           executeLogIn(actionEvent, new LogInStudent(loginModel.
                   getStudent(emailField.getText())));
           logInStudent.setDisable(true);
        }
        else {
            System.out.println("verification not successful");
            JOptionPane.showMessageDialog(null, "Wrong email or password!");
            System.out.println(emailField.getText());
            System.out.println(passwordField.getText());
            System.out.println("end of that");
        }
    }

    /**
     * event handler for button
     *
     * if password is from preferences there is another way because we have password
     * in the hased version but we don't have in unhashed version but we need that!!
     * lets leave it as it is for now
     * @param actionEvent
     */
    public void logTeacherButton(ActionEvent actionEvent) {
        //if preferences are set remember to check the value from preferences
        // not test because it actually not exists
        if(loginModel.verifyPassword(emailField.getText(),
                passwordField.getText(), UserType.TEACHER)){
            executeLogIn(actionEvent, new LogInTeacher(loginModel.
                    getTeacher(emailField.getText())));
            logInTeacher.setDisable(true);

        }
        else{
            System.out.println("verification not successful");
            JOptionPane.showMessageDialog(null, "fuck you");
            System.out.println(emailField.getText());
            System.out.println(passwordField.getText());
            System.out.println("end of that");
        }

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
