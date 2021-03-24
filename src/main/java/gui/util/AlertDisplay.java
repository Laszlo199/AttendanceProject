package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;

import java.util.Optional;

/**
 * @author Kuba
 * @date 3/24/2021 2:27 PM
 */
public class AlertDisplay {

    public static void displayAlert(String title, String information, String header,
                             Alert.AlertType alertType)
    {
        TilePane tilePane = new TilePane();
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(information);
        alert.show(); //no other action is needed
    }

}
