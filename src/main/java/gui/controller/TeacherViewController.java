package gui.controller;

import be.*;
import bll.FacadeBLL;
import bll.exception.BLLexception;
import gui.model.LoginModel;
import gui.model.TeacherDashboardModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;


import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


public class TeacherViewController implements Initializable {

    public TableView<Student> studentsOverviewTable;
    public TableColumn<Student, String> nameCol;
    public TableColumn<Student, String> presenceCol;
    public TableColumn<Student, String> mostAbsDayCol;


    @FXML private Text dateLabel;
    @FXML private Text dayLabel;
    @FXML private TableColumn dateColumnn;
    @FXML private TableView changeTable;
    @FXML private TableColumn nameColumnn;
    @FXML private TableColumn typeColumnn;
    @FXML private TableColumn acceptColumnn;
    @FXML private TableColumn declineColumnn;
    @FXML private ListView absentList;
    @FXML private TableView studentsTable;
    @FXML private PieChart absenceChart;
    private Teacher loggedTeacher;
    private ScheduleEntity currentLesson;
    private TeacherDashboardModel model;
    private List<ChangeRequest> requests;

    public void setTeacher(Teacher teacher) {
        this.loggedTeacher = teacher;
        //this.currentLesson = model.getCurrentLesson(loggedTeacher.getId());
        currentLesson = new ScheduleEntity(5, 1, WeekDay.MONDAY, null, null);
        System.out.println("subject: " + model.getSubject(currentLesson.getSubjectId()).getName()); //just checking
        //needs to be here cause we need logged teacher id and current lesson which is instantiated in this method
        setDate();
        setStudentsTableView();
        setAbsentList();
        setChart();
        setChangeTable();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.model = new TeacherDashboardModel();
    }

    private void setAbsentList(){
        if(currentLesson!=null) {
            List<Student> absentStudents = model.getAbsentToday(currentLesson);
            ObservableList<String> absentStudentsNames = FXCollections.observableArrayList();
            for(Student s : absentStudents) absentStudentsNames.add(s.getName());
            absentList.setItems(absentStudentsNames);
        }
    }

    private void setChart(){
        if(currentLesson!=null) {
            int absent = model.getNumberOfAbsent(currentLesson);
            int present = model.getNumberOfPresent(currentLesson);
            int sumOfStudents = absent + present;
            if(sumOfStudents>0) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                        new PieChart.Data("Absent", (absent * 100) / sumOfStudents),
                        new PieChart.Data("Present", (present * 100) / sumOfStudents));
                absenceChart.setData(pieData);

                absenceChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
                    Tooltip toolTip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), toolTip);
                });
            }
        }
    }

    private void setStudentsTableView() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        presenceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(model.getPresenceForStudent(student.getValue(), Timeframe.TOTAL));
            }
        });
        mostAbsDayCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(model.getMostAbsentDay(student.getValue(), Timeframe.MONTH));
            }
        });
        model.loadTableView();
        studentsOverviewTable.setItems(model.getObsStudents());
    }

    public enum Timeframe {
        TODAY,
        MONTH,
        TOTAL
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

        ObservableList<ChangeRequest> changes = FXCollections.observableArrayList(model.getAllRequests(loggedTeacher.getId()));

        nameColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("studentName"));
        typeColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("subjectName"));
        dateColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("date"));
        acceptColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));
        declineColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));

        nameColumnn.setText("Student");
        typeColumnn.setText("Subject");
        dateColumnn.setText("Date");

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

    public void logOut(ActionEvent actionEvent) {
        Stage s = (Stage) dateLabel.getScene().getWindow();
        s.close();
    }
}
