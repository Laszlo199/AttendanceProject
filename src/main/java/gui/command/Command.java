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
 * @date 3/29/2021 5:33 PM
 */
public abstract class Command {

    public User getUser() {
        return user;
    }

    private User user;

    protected Command(User user) {
        this.user = user;
    }

    protected Command() {

    }

    /**
     * method closes the current stage
     * and opens LogIn window
     */
    public void LogOut(Stage currentStage) throws IOException {
        closeCurrentStage(currentStage);
        openLogInWindow();
    }

    public void closeCurrentStage(Stage currentStage){
        currentStage.close();
    }

    private void openLogInWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public abstract boolean logIn() throws IOException;
}
