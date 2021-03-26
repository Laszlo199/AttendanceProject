package gui.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author kamila
 */
public class TeacherViewController{

}
//public class TeacherViewController implements Initializable {}
/*

    @FXML private Text dateLabel;
    @FXML private Text dayLabel;
    @FXML private TableColumn dateColumnn;
    @FXML private TableView changeTable;
    @FXML private TableColumn nameColumnn;
    @FXML private TableColumn typeColumnn;
    @FXML private TableColumn acceptColumnn;
    @FXML private TableColumn declineColumnn;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn monthColumn;
    @FXML private TableColumn semesterColumn;
    @FXML private TableColumn dayColumn;
    @FXML private ListView absentList;
    @FXML private TableView studentsTable;
    @FXML private PieChart absenceChart;

    private StudentModel studentModel;
    private ChangeModel changeModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentModel = new StudentModel();
        changeModel = new ChangeModel();
        setChart();
        setStudentsTable();
        setAbsentList();
        setChangeTable();
        setDate();
    }

    private void setDate() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("  EEEE ");
            dayLabel.setText(LocalDateTime.now().format(formatter));
            DateTimeFormatter form = DateTimeFormatter.ofPattern("  dd.MM.yyyy\n    HH:mm");
            dateLabel.setText(LocalDateTime.now().format(form));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setChangeTable() {
        nameColumnn.setCellValueFactory(new PropertyValueFactory<Change, String>("name"));
        typeColumnn.setCellValueFactory(new PropertyValueFactory<Change, String>("type"));
        dateColumnn.setCellValueFactory(new PropertyValueFactory<Change, String>("date"));
        acceptColumnn.setCellValueFactory(new PropertyValueFactory<Change, Void>(""));
        declineColumnn.setCellValueFactory(new PropertyValueFactory<Change, Void>(""));

        ObservableList<Change> changes = FXCollections.observableArrayList(changeModel.getAllChanges());

        Callback<TableColumn<Change, Void>, TableCell<Change, Void>> cellFactory = new Callback<TableColumn<Change, Void>, TableCell<Change, Void>>() {
            @Override
            public TableCell<Change, Void> call(final TableColumn<Change, Void> param) {
                final TableCell<Change, Void> cell = new TableCell<Change, Void>() {

                    private final Button btn = new Button("Accept");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Change change = getTableView().getItems().get(getIndex());
                            changes.remove(change);
                            System.out.println(change.getName() + " accepted");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        Callback<TableColumn<Change, Void>, TableCell<Change, Void>> cFactory = new Callback<TableColumn<Change, Void>, TableCell<Change, Void>>() {
            @Override
            public TableCell<Change, Void> call(final TableColumn<Change, Void> param) {
                final TableCell<Change, Void> cell = new TableCell<Change, Void>() {

                    private final Button btn = new Button("Decline");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Change change = getTableView().getItems().get(getIndex());
                            changes.remove(change);
                            System.out.println(change.getName() + " declined");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };



        changeTable.setItems(changes);

        acceptColumnn.setCellFactory(cellFactory);
        declineColumnn.setCellFactory(cFactory);
    }

    private void setAbsentList() {
        ObservableList<String> absentStudents = FXCollections.observableArrayList(studentModel.getAbsentToday());
        absentList.setItems(absentStudents);
    }

    private void setChart() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Absent", 12),
                new PieChart.Data("Present", 78)
        );
        absenceChart.setData(pieData);
    }

    private void setStudentsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("p_month"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("p_semester"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("day"));

        ObservableList<Student> students = FXCollections.observableArrayList(studentModel.getAllStudents());
        studentsTable.setItems(students);
    }

    public void logOut(ActionEvent actionEvent) {
        Stage s = (Stage) dateLabel.getScene().getWindow();
        s.close();
    }
}

 */
