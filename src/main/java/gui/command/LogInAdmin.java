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
 * @date 4/11/2021 10:50 AM
 */
public class LogInAdmin extends Command{
    public LogInAdmin(User user) {
        super(user);
    }
    public LogInAdmin() {

    }

    @Override
    public boolean logIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");
        stage.setScene(scene);
        stage.show();
        return true;
    }
}
