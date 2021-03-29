package gui.command;

import be.Student;
import be.User;
import gui.controller.StudentDashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Kuba
 * @date 3/29/2021 5:46 PM
 */
public class LogInStudent extends Command{

    public LogInStudent(User user) {
        super(user);
    }

    //we must remember to close the current stage
    //before calling log in method

    @Override
    public boolean logIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("StudentDashboard.fxml"));
        Parent root = loader.load();
        //for the student dashboard in the stage of development
        StudentDashboardController studentDashboardController =
                (StudentDashboardController) loader.getController();
        studentDashboardController.setLoggedStudent((Student) getUser());
        Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        studentDashboardController.setlisteners(stage);
        stage.show();
        return false;
    }
}
