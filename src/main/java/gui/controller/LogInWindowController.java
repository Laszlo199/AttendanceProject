package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import gui.controller.ILogIn;
import gui.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Kuba
 * @date 3/24/2021 9:59 AM
 */
public class LogInWindowController implements Initializable, ILogIn {
    private LoginModel loginModel = LoginModel.getInstance();


    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXTextField passwordField;
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

    }

    @Override
    public void unsaveUserInPreferences() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       checkConnection();

    }

    /**
     * as long as we don't establish connection with Database buttons will be disabled
     * if connection won't be established in 3 sec show alert to the user
     */
    private void checkConnection() {
        while (!loginModel.establishedConnection()){
            
        }

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
