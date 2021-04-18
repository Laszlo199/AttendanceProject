package gui.command;

import be.Student;
import be.Teacher;
import be.User;
import gui.controller.StudentDashboardController;
import gui.controller.TeacherViewController;
import gui.controller.TeacherViewRefactoredController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Kuba
 * @date 4/7/2021 3:07 PM
 */
public class LogInTeacher extends Command{

    public LogInTeacher(User user) {
        super(user);
    }

    @Override
    public boolean logIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeacherViewRefactored.fxml"));
        Parent root = loader.load();
        TeacherViewRefactoredController teacherViewController =
                (TeacherViewRefactoredController) loader.getController();
        teacherViewController.setTeacher((Teacher) getUser());
        Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/MainStyle.css");
        stage.setScene(scene);
        stage.show();
        return true;
    }
}
