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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
        Parent root = loader.load();
        stage.setTitle("Attendance tracker");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
