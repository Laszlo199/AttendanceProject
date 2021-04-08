package gui.controller;

import be.ChangeRequest;
import be.StatusType;
import be.Student;
import be.Teacher;
import bll.FacadeBLL;
import bll.exception.BLLexception;
import gui.model.LoginModel;
import gui.model.TeacherDashboardModel;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author kamila
 */
public class TeacherViewController implements Initializable {

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
    private Teacher loggedTeacher;
    private TeacherDashboardModel model;
    private List<ChangeRequest> requests;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new TeacherDashboardModel();
        setChart();
        setStudentsTable();
        setAbsentList();
        setChangeTable();
        setDate();
        //list of requests to display in the table view
        //requests = model.getAllRequests(loggedTeacher.getId());

    }

    public void setTeacher(Teacher teacher) {
        this.loggedTeacher = teacher;
    }

    public Teacher getLoggedTeacher() {
        return loggedTeacher;
    }

    //when teacher clicks accept button
    private void requestAccepted(ChangeRequest request) {
        model.requestAccepted(request);
    }

    //when teacher clicks decline button
    private void requestDeclined(ChangeRequest request) {
        model.requestDeclined(request);
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
        nameColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("name"));
        typeColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("type"));
        dateColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("date"));
        acceptColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));
        declineColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));

        ObservableList<ChangeRequest> changes = FXCollections.observableArrayList();
        //changes.addAll(requests);
        changes.add(new ChangeRequest(10, StatusType.PENDING));
        changes.add(new ChangeRequest(11, StatusType.PENDING));
        changes.add(new ChangeRequest(12, StatusType.PENDING));


        Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>> cellFactory = new Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>>() {
            @Override
            public TableCell<ChangeRequest, Void> call(final TableColumn<ChangeRequest, Void> param) {
                final TableCell<ChangeRequest, Void> cell = new TableCell<ChangeRequest, Void>() {

                    private final Button btn = new Button("Accept");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ChangeRequest change = getTableView().getItems().get(getIndex());
                            changes.remove(change);
                            model.requestAccepted(change);
                            //we need to make getName() returning real name
                            //System.out.println(change.getName() + " accepted");
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

        Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>> cFactory = new Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>>() {
            @Override
            public TableCell<ChangeRequest, Void> call(final TableColumn<ChangeRequest, Void> param) {
                final TableCell<ChangeRequest, Void> cell = new TableCell<ChangeRequest, Void>() {

                    private final Button btn = new Button("Decline");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ChangeRequest change = getTableView().getItems().get(getIndex());
                            changes.remove(change);
                            model.requestDeclined(change);
                            //we need to make getName() returning real name
                            //System.out.println(change.getName() + " declined");
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

    private void setAbsentList(){
        ObservableList<Student> absentStudents = FXCollections.observableArrayList();
            //absentStudents.addAll((Student) model.getAbsentToday());
        absentStudents.add(new Student(5,"Richard Button", "RB@easv.dk", "photopath", 1, 1));
        absentList.setItems(absentStudents);
    }

    private void setChart(){
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
             //   new PieChart.Data("Absent", model.getNumberOfAbsent()),
            //    new PieChart.Data("Present", model.getNumberOfPresent()));
                new PieChart.Data("Absent", 78),
                new PieChart.Data("Present", 22));
        absenceChart.setData(pieData);
    }


    private void setStudentsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("p_month"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<Student, Double>("p_semester"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("day"));

        ObservableList<Student> students = FXCollections.observableArrayList(model.getAllStudents());
        //studentsTable.setItems(students);
    }


    public void logOut(ActionEvent actionEvent) {
        Stage s = (Stage) dateLabel.getScene().getWindow();
        s.close();
    }
}
