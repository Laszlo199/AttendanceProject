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
import javafx.scene.Scene;
import javafx.scene.chart.*;
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
import java.util.Random;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    public Label subject;
    public VBox vBox;
    public AnchorPane quoteBackground;
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
    private DoubleProperty fontSize = new SimpleDoubleProperty(76);
    private StudentDashboardModel studentDashboardModel = new StudentDashboardModel();
    private boolean quoteIsShown = false;
    Label quote = new Label(studentDashboardModel.getRandQuote());
    StackPane stackPane = new StackPane();
    DonutChart donutChart;
    private boolean bigChartIsShown;
    ComboBox<Months> comboBox = new ComboBox<>();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    BarChart<String,Number> barChart =
            new BarChart<String,Number>(xAxis,yAxis);
    @FXML
    private AnchorPane top;
    @FXML
    private GridPane gridPane;
    private Student loggedStudent;
    private ScheduleEntity currentLesson;
    private static StudentDashboardModel model;
    @FXML
    private ListView<Record> listView;
   // private List<Record> absentDays;
    private int count =0;

    public void setLoggedStudent(Student student) {
        this.loggedStudent = student;
        studentDashboardModel.setAbsentDays(loggedStudent.getId());
         //there is an exception when I use that method
         //this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
        initComboBox();
        setListView();
        addPieChart();
        digitalClock();
        initGroupRadioButtons();
        listenForShowingQuote();
        listenForShowingSecondDiagram();
       // listenForResize();
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
                  if(t1.intValue()> 740 && !quoteIsShown){
                      System.out.println("bigger than 760");
                      showQuote();
                  }
                  else if (t1.intValue() <= 740 && quoteIsShown){
                      System.out.println("delete that quote");
                      deleteQuote();
                  }
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


    private void setStudentsData() {
        // TO DO
    }

    private void initGroupRadioButtons() {
        absentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setToggleGroup(groupRadioButtons);
        presentRadioButton.setSelected(true);
    }


    private void addPieChart() {
        ObservableList<PieChart.Data> pieChartData = createData();
        donutChart = new DonutChart(pieChartData);
        donutChart.setTitle("Attendance");
        donutChart.setPrefHeight(270);
        donutChart.setPrefWidth(270);
        AnchorPane.setLeftAnchor(donutChart, 10.0);
        AnchorPane.setBottomAnchor(donutChart, 15.0);
        anchorChart.getChildren().add(donutChart);
    }

    /**
     * creates data for PieChart
     * @return
     */
    private ObservableList<PieChart.Data> createData() {
        return FXCollections.observableArrayList(
                //do we have  a method for getting a number of absent days ??
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
}
