import be.Student;
import gui.controller.StudentDashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Kuba
 * @date 3/22/2021 3:08 PM
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
        Parent root = loader.load();

        //for the student dashboard in the stage of development
        StudentDashboardController studentDashboardController = (StudentDashboardController)
        loader.getController();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");
        stage.setScene(scene);
        studentDashboardController.setLoggedStudent(new Student(1, "Dorelia McCawley", "dmccawley0@epa.gov",
                null,
                1, 1));


        stage.show();
    }
}
