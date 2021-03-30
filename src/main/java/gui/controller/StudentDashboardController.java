package gui.controller;

import be.*;
import be.Record;
import com.jfoenix.controls.JFXRadioButton;
import gui.model.StudentDashboardModel;
import gui.util.DonutChart;
import gui.util.Resizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    @FXML
    private JFXRadioButton presentRadioButton;
    @FXML
    private JFXRadioButton absentRadioButton;
    @FXML
    private Text dayLabel;
    @FXML
    private Text dateLabel;
    @FXML
    private Text hourLabel;
    @FXML
    private AnchorPane anchorChart;

    private final ToggleGroup groupRadioButtons = new ToggleGroup();

    private StudentDashboardModel studentDashboardModel = new StudentDashboardModel();


    @FXML
    private AnchorPane top;
    @FXML
    private GridPane gridPane;
    private Student loggedStudent;
    private ScheduleEntity currentLesson;
    private StudentDashboardModel model;
    @FXML
    private ListView<Record> listView;
   // private List<Record> absentDays;

    public void setLoggedStudent(Student student) {
        this.loggedStudent = student;
        studentDashboardModel.setAbsentDays(loggedStudent.getId());
         //there is an exception when I use that method
         //this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
        setListView();
        initPieChart();
        digitalClock();
        initGroupRadioButtons();

       // this.absentDays = model.getAbsentDays(loggedStudent.getId());
    }

    private void initGroupRadioButtons() {
        absentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setSelected(true);
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> pieChartData = createData();
        final DonutChart chart = new DonutChart(pieChartData);
        chart.setTitle("Attendance");
        chart.setPrefHeight(270);
        chart.setPrefWidth(270);
        anchorChart.getChildren().add(chart);
    }

    /**
     * creates data for PieChart
     * @return
     */
    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Present", 13),
                new PieChart.Data("Absent", 25));
    }

    private void setListView() {
      //  System.out.println(loggedStudent.toString());
       // studentDashboardModel.setAbsentDays(loggedStudent.getId());
       listView.setItems(studentDashboardModel.getRecordObservableList());
        for (Record r: studentDashboardModel.getRecordObservableList()
             ) {
            System.out.println(r);
        }
        listView.setCellFactory(new Callback<ListView<Record>, ListCell<Record>>() {
            @Override
            public ListCell<Record> call(ListView<Record> recordListView) {
                return new RecordCell();
            }
        });

    }

    /**
     * method handles clock functionality
     */
    private void digitalClock() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                hourLabel.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Calendar time = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
        dateLabel.setText(simpleDateFormat2.format(time.getTime()));
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("EEEE");
        dayLabel.setText(simpleDateFormat3.format(time.getTime()));
    }


    static class RecordCell extends ListCell<Record> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button button = new Button("Edit");
        Record lastItem;

        public RecordCell() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(lastItem + " : " + event);
                }
            });
        }

        protected void updateItem(Record item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item!=null ? item.toString() : "<null>");
                setGraphic(hbox);
            }
        }
    }

    //when 'save' button is clicked
    //creates a new Record
    public void setCurrentAttendance(boolean isPresent) {
        LocalDate currentDate = LocalDate.now();
        //here basing on what a user chooses
        //0 is a temporary id
        Record record = new Record(0, loggedStudent.getId(), Date.valueOf(currentDate), currentLesson.getId(), isPresent);
        model.createRecord(record);
    }

    /**
     * method checks which radio button is selected and
     * handles save event
     * @param actionEvent
     */
    public void SaveAttendance(ActionEvent actionEvent) {
        boolean isPresent = false;
        if(presentRadioButton.isSelected())
            isPresent  = true;
        setCurrentAttendance(isPresent);
    }

    //when 'edit' button
    //creates a changeRequest
    public void editAttendance() {
        //!! insert actual recordId !!
        ChangeRequest newRequest = new ChangeRequest(0, StatusType.PENDING);
        model.createChangeRequest(newRequest);
    }


    public void setlisteners(Stage stage) {
      //  Resizer.letterbox(stage.getScene(), top);
    }
}
