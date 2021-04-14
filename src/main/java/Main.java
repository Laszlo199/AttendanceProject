import be.Student;
import be.Teacher;
import gui.controller.StudentDashboardController;
import gui.controller.TeacherViewController;
import gui.controller.TeacherViewRefactoredController;
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

        /*

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



         */


        /*

       FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
        Parent root = loader.load();
        //Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

         */


/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeacherView.fxml"));
        Parent root = loader.load();
        TeacherViewController teacherViewController = (TeacherViewController) loader.getController();
        teacherViewController.setTeacher(new Teacher(1, "Tom Tom", "tom@ee.dk", null, "IT"));
        //Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

 */


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TeacherViewRefactored.fxml"));
        Parent root = loader.load();
        TeacherViewRefactoredController teacherViewController = (TeacherViewRefactoredController) loader.getController();
        teacherViewController.setTeacher(new Teacher(1, "Tom Tom", "tom@ee.dk", null, "IT"));
        //Stage stage = new Stage();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();





    }
}
