package gui.controller;

import be.*;
import be.Record;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import gui.model.StudentDashboardModel;
import gui.util.DonutChart;
//import gui.util.Resizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {

    @FXML private ImageView studentImage;
    @FXML private Text studentName;
    @FXML private Text studentProgram;
    @FXML private Label teacherName;
    @FXML private Label lessonDuration;
    @FXML private  Label subjectName;
    @FXML private  VBox vBox;
    @FXML private JFXRadioButton presentRadioButton;
    @FXML private JFXRadioButton absentRadioButton;
    @FXML private Text dayLabel;
    @FXML private Text dateLabel;
    @FXML private Text hourLabel;
    @FXML private AnchorPane anchorChart;
    @FXML private ListView<Record> listView;
    @FXML private JFXButton btnSave;

    private final ToggleGroup groupRadioButtons = new ToggleGroup();
    StackPane stackPane = new StackPane();
    private boolean bigChartIsShown;
    ComboBox<Months> comboBox = new ComboBox<>();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    private BarChart<String,Number> barChart =
            new BarChart<String,Number>(xAxis,yAxis);
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    DonutChart donutChart = new DonutChart(pieChartData);
    private final Label caption = new Label("");

    private Student loggedStudent;
    private ScheduleEntity currentLesson;
    private static StudentDashboardModel model = new StudentDashboardModel();
    private DoubleProperty fontSize = new SimpleDoubleProperty(76);
    private int count =0;
   // private StudentDashboardModel studentDashboardModel = new StudentDashboardModel();
    private boolean quoteIsShown = false;
    Label quote = new Label(model.getRandQuote());

    //ObservableList<PieChart.Data> pieChartData = createData(getCurrentMonth());
    //donutChart = new DonutChart(pieChartData);

    public void setLoggedStudent(Student student) {
        this.loggedStudent = student;
        model.setAbsentDays(loggedStudent.getId());
        this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
        showInfoStudent();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox();
        initPieChart();
        comboBoxListener();
        setListView();
        digitalClock();
        initGroupRadioButtons();
        listenForShowingQuote();
        listenerPieChart();
        btnSave.setDisable(true);
       // listenForShowingSecondDiagram();
    }

    private void comboBoxListener() {
        comboBox.valueProperty().addListener(new ChangeListener<Months>() {
            @Override
            public void changed(ObservableValue<? extends Months> observableValue, Months months, Months t1) {
                System.out.println(t1.name());
                changeChart(t1);
            }
        });
    }


    private void showInfoStudent() {
        studentName.setText(this.loggedStudent.getName());
        studentProgram.setText("Semester: "  + String.valueOf(this.loggedStudent.getSemester()));
        if(currentLesson!=null) {
            Subject currentSubject = model.getSubject(currentLesson.getSubjectId());
            subjectName.setText(currentSubject.getName());
            teacherName.setText("with " + model.getTeacher(currentSubject.getTeacherId()).getName());
            lessonDuration.setText(currentLesson.getStartTime() + " - " + currentLesson.getEndTime());
        }
    }

    private void listenerPieChart() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hoveredPieChart();
                unhoveredPieChart();
            }
        });
    }

    private void unhoveredPieChart() {
        donutChart.getData()
                .stream()
                .forEach(data -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        caption.setVisible(false);
                    });
                });
    }

    private void hoveredPieChart() {
        donutChart.getData()
                .stream()
                .forEach(data ->{
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e->{
                        Point2D locationInScene = new Point2D(e.getSceneX(), e.getSceneY());
                        Point2D locationInParent = donutChart.sceneToLocal(locationInScene);
                        caption.relocate(locationInParent.getX(), locationInParent.getY());
                       // caption.setText(String.valueOf(data.getPieValue()));
                       int  avg = (int) ((pieChartData.get(0).getPieValue() / (pieChartData.get(0).getPieValue() +
                               pieChartData.get(1).getPieValue())) *100);
                        if(data.getName().matches("Present"))
                            caption.setText("Presence: " + avg +"%");
                        else if(data.getName().matches("Absent"))
                            caption.setText("Absence: "+ (100-avg)+  "%");
                        caption.setVisible(true);
                    });
                });
    }


    private Months getCurrentMonth(){
        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        String m = currentMonth.toString();
        for (Months month: Months.values()) {
            if(m.equals(month.name()))
                return month;
        }
        return null;
    }


    private void initPieChart() {
        pieChartData.addAll(createData(getCurrentMonth()));
        donutChart.setTitle("Attendance");
        donutChart.setPrefHeight(270);
        donutChart.setPrefWidth(270);
        AnchorPane.setLeftAnchor(donutChart, 10.0);
        AnchorPane.setBottomAnchor(donutChart, 15.0);
        caption.setVisible(false);
        caption.getStyleClass().addAll("chart-line-symbol", "chart-series-line");
        caption.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        caption.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        Group group = new Group(donutChart, caption);
        AnchorPane.setBottomAnchor(group, 10.0);
        AnchorPane.setLeftAnchor(group, 10.0);
        anchorChart.getChildren().add(group);
    }

    /**
     * when the combobox is changed
     * chart shows data for another month
     */
    private void changeChart(Months month) {
        donutChart.getData().clear();
        donutChart.getData().addAll(createData(month));
        hoveredPieChart();
        unhoveredPieChart();
    }

    private void initComboBox() {
        for(Months m: Months.values())
            comboBox.getItems().add(m);
        comboBox.setPrefWidth(131);
        comboBox.setLayoutX(358);
        comboBox.setLayoutY(22);
        comboBox.setPromptText("Select month");
        AnchorPane.setTopAnchor(comboBox, 15.0);
        AnchorPane.setRightAnchor(comboBox, 15.0);
        anchorChart.getChildren().add(comboBox);
    }

    /**
     * when there is enough space we delete the shown diagram
     * and display full diagram for whole year. maybe resize the
     * diagram to make it fill all the available space
     *
     * in the full screen mode show attendance compared to the
     * full group (I have no idea how i can show that)
     * it will be done later
     */
    private void listenForShowingSecondDiagram() {
       anchorChart.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
           if (oldScene == null && newScene != null) {
               newScene.widthProperty().addListener((observableValue, number, t1) -> {
                   if(t1.intValue()> 1500 && !bigChartIsShown){
                       System.out.println("bigger than 500. " + t1.intValue());
                       //delete small diagram and combobox
                       //it can be commented
                       anchorChart.getChildren().removeAll(comboBox, donutChart);
                       bigChartIsShown= true;
                       //add new bigger diagram
                       //TO DO
                       if(count<1)
                           initBarChart();
                       anchorChart.getChildren().add(barChart);
                       count++;
                   }
                   else if (t1.intValue() <= 1500 && bigChartIsShown){
                       anchorChart.getChildren().remove(barChart);
                       //it can be commnented
                       anchorChart.getChildren().addAll(this.comboBox, donutChart);
                       bigChartIsShown=false;

                   }

               });
           }
       });
    }

    /**
     * method is responsible for showing bar chart
     * which corresponds to attendance in each month
     *
     * Also we need to think about the situation
     *
     * later it will be generated once and then it will just
     * appear and disappear for the sake of optimalization
     */
    private void initBarChart() {
        barChart.setTitle("Attendance summary");
        xAxis.setLabel("Month");
        yAxis.setLabel("Presence in %");
        XYChart.Series series1 = new XYChart.Series();
        Calendar time = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        series1.setName("Year: " + simpleDateFormat.format(time.getTime()));
        for(Months m: Months.values()){
            int presentDays = 0;
            int absDays=1;
            int avg = presentDays / (presentDays+absDays);
            Random r = new Random();
            series1.getData().add(new XYChart.Data(m.name(), r.nextInt(100) + 1));
        }
        barChart.setPrefHeight(anchorChart.getHeight()-60);
        barChart.getData().add(series1);
        AnchorPane.setBottomAnchor(barChart, 1.0);
        AnchorPane.setRightAnchor(barChart, 8.0);
    }

    private void listenForShowingQuote() {
        vBox.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
              newScene.heightProperty().addListener((observableValue, number, t1) -> {
                  if(t1.intValue()> 740 && !quoteIsShown)
                      showQuote();
                  else if (t1.intValue() <= 740 && quoteIsShown)
                      deleteQuote();
              });
            }
        });
    }

/*
    private void listenForResize() {
        vBox.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).maximizedProperty().addListener((a, b, c) -> {
                            if (c) {
                                System.out.println("I am maximized");
                            }
                        });
                    }
                });
            }
        });
    }

 */

    private void showQuote() {
        quote.getStyleClass().add("labelQuote");
        stackPane.getStyleClass().add("boxQuote");
        HBox.setHgrow(stackPane, Priority.ALWAYS);
        stackPane.getChildren().add(quote);
        vBox.getChildren().add(stackPane);
        quoteIsShown = true;
    }

    private void deleteQuote() {
        stackPane.getChildren().remove(quote);
        vBox.getChildren().removeAll(stackPane);
        quoteIsShown =false;
    }

    private void initGroupRadioButtons() {
        absentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setSelected(true);
    }



    /**
     * creates data for PieChart
     * It has to be updated each time we select another month
     * @return
     */
    private ObservableList<PieChart.Data> createData(Months month) {
        Random r = new Random();
        return FXCollections.observableArrayList(
                new PieChart.Data("Present", r.nextInt(100) + 50),
                new PieChart.Data("Absent", r.nextInt(30)+5));
    }

    private void setListView() {
       listView.setItems(model.getRecordObservableList());
        for (Record r: model.getRecordObservableList()
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
                    editAttendance(lastItem);
                }
                private void editAttendance(Record lastItem) {
                    ChangeRequest newRequest = new ChangeRequest(lastItem.getId(), StatusType.PENDING);
                    model.createChangeRequest(newRequest);
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


        btnSave.setDisable(true);
        absentRadioButton.setDisable(true);
        presentRadioButton.setDisable(true);
    }

    public void isPresent(ActionEvent actionEvent) {
        if (presentRadioButton.isSelected()){
            btnSave.setDisable(false);
        }
    }

    public void isAbsent(ActionEvent actionEvent) {
        if (absentRadioButton.isSelected()){
            btnSave.setDisable(false);
        }
    }
}
