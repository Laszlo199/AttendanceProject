package gui.controller;

import be.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import gui.command.Command;
import gui.command.LogInAdmin;
import gui.command.LogInStudent;
import gui.command.LogInTeacher;
import gui.model.TeacherDashboardModel;
import gui.strategy.CreateMonthData;
import gui.strategy.CreateTodayData;
import gui.strategy.CreateTotalData;
import gui.strategy.ICreateDataStrategy;
import gui.util.HoverChart;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author Kuba && Kamila
 * @date 4/14/2021 8:02 AM
 */
public class TeacherViewRefactoredController implements Initializable {
    public StackPane stackPie;
    public TableColumn semCol;
    @FXML
    private JFXComboBox selectSemPieChart;
    @FXML
    private JFXComboBox semesterTablevView;
    @FXML
    private AnchorPane pieAnchor;
    @FXML
    private TableView changeTable;
    @FXML
    private TableColumn nameColumnn;
    @FXML
    private TableColumn typeColumnn;
    @FXML
    private TableColumn dateColumnn;
    @FXML
    private TableColumn acceptColumnn;
    @FXML
    private TableColumn declineColumnn;
    @FXML
    private TableView tableview;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn attendanceCol;
    @FXML
    private TableColumn dayCol;
    @FXML
    private Label dayLabel2;
    @FXML
    private JFXComboBox selectMonth;
    @FXML
    private PieChart pieChart;
    @FXML
    private JFXListView absenceList;
    @FXML
    private TextField searchField;
    @FXML
    private JFXToggleButton switchAttendance;
    @FXML
    private ImageView imageView;
    @FXML
    private Text teacherName;
    @FXML
    private Text teacherProgram;
    @FXML
    private Text dayLabel;
    @FXML
    private Text dateLabel;
    @FXML
    private Text hourLabel;
    @FXML
    private Label lblNoData;

    private static final Label caption = new Label("");
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>> cFactory;
    Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>> cellFactory;
    private static TeacherDashboardModel model;
    private static ScheduleEntity currentLesson;
    private Teacher loggedTeacher;
    private ObservableList<String> comboboxOptions =FXCollections.observableArrayList("Current lesson", "All year");
    private ObservableList<String> semesters =
            FXCollections.observableArrayList("1st sem", "2nd sem", "3rd sem", "4th sem");
    private volatile ObservableList<PieChart.Data> pieData;
    boolean alreadyInitialized =false;

   static {
       model = TeacherDashboardModel.getInstance();
   }
    public void setTeacher(Teacher teacher) {
        this.loggedTeacher = teacher;
        this.currentLesson = model.getCurrentLesson(loggedTeacher.getId());


        if(currentLesson!=null) {
            dayLabel2.setText(model.getSubject(currentLesson.getSubjectId()).getName());
        }
        teacherName.setText(loggedTeacher.getName());
        teacherProgram.setText(loggedTeacher.getDepartment());
        initAbsenceList();
        initPieChart();
        initStudentsTableView();
        setChangeTableView();
        showPhoto();
        showInfoTeacher();
    }

    /**
     * log out using command pattern
     * @param actionEvent
     */
    public void logOut(ActionEvent actionEvent) {
        Command command = new LogInTeacher(loggedTeacher);
        Stage thisStage = (Stage) pieChart.getScene().getWindow();
        try {
            command.LogOut(thisStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTime();
        initComboBoxesSem();
        changePieChartListener();
        ChangePieChartListener2();
        changeTableViewListener();
        searchfieldListener();
        absenceList.setPlaceholder(new Label("There are no missing students today"));
    }


    private void changeTableViewListener() {
        semesterTablevView.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object semester) {
                int sem = 0;
                if(semester.toString().matches("1.*"))
                    sem=1;
                if(semester.toString().matches("2.*"))
                    sem=2;
                if(semester.toString().matches("3.*"))
                    sem=3;
                if(semester.toString().matches("4.*"))
                    sem=4;

               model.setStudents(sem);
            }
        });

    }

    private void initComboBoxesSem() {
        semesterTablevView.getItems().addAll(semesters);
        selectSemPieChart.getItems().addAll(semesters);
        selectSemPieChart.getSelectionModel().select(0);

    }


    private void initTime() {
        setDate();
        initClock();
    }

    /**
     * method sets day of week and date in format MM-dd-yyyy
     */
    private void setDate() {
        Calendar time = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("EEEE");
        dateLabel.setText(simpleDateFormat3.format(time.getTime()) + ", " + simpleDateFormat2.format(time.getTime()));
       // dayLabel.setText(simpleDateFormat3.format(time.getTime()) + ", ");
    }

    /**
     * clock functionality running in another thread
     */
    private void initClock() {
        Runnable runnable1 =  () ->{
            Calendar cal;
            while(true){
                cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                int second = cal.get(Calendar.SECOND);
                Platform.runLater(()->hourLabel.setText(hour + ":" + minute + ":" + second));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread th1 = new Thread(runnable1);
        executorService.execute(th1);
    }

    private void initStudentsTableView() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        attendanceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(student.getValue().getTotalPresence() + " %");
            }
        });
        dayCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(student.getValue().getMostAbsWeekday().name());
            }
        });
        semCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(setSemInfo(student.getValue().getSemester() ));
            }

        });
        model.loadCache();
        tableview.setItems(model.getObsStudents());
    }

    private String setSemInfo(int sem) {
        if(sem==1)
            return sem +"st" + " sem";
        else if(sem ==2)
            return sem+"nd sem";
        else if(sem ==3)
            return sem+"rd sem";
        else if(sem ==4)
            return sem+" th sem";
        else
            return "not known";
    }

    /**
     * pie chart shows ratio abs days to present days
     */
    private void initPieChart() {
        caption.setVisible(false);
        stackPie.getChildren().add(caption);
        setCombobox();

        if(currentLesson==null)
            lblNoData.setText("No lesson at the moment");
    }

    private void setCaption() {
        caption.setVisible(false);
        caption.getStyleClass().addAll("chart-line-symbol", "chart-series-line");
        caption.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        caption.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
    }


    private void searchfieldListener() {
        searchField.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.isBlank())
                tableview.setItems(model.getObsStudents());
            else
             tableview.setItems(model.loadData(t1));
        });
    }

    private void clearPieChart(){
        Platform.runLater(()->pieChart.getData().clear());
    }


    /**
     * when user selects other value in combobox show corresponding data
     */
    private void changePieChartListener() {
         selectMonth.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object m) {
                lblNoData.setText("");
                Thread thread = new Thread(() ->{
                    clearPieChart();
                    String s = selectMonth.getSelectionModel().getSelectedItem().toString();
                    String sem = selectSemPieChart.getSelectionModel().getSelectedItem().toString();
                    ICreateDataStrategy strategy  = setStrategy();
                    Months month =null;

                    //just set month
                    if(!s.toString().matches("Current lesson") &&
                            ! s.matches("All year")
                    ) month =Months.valueOf(s);
                    if(s.matches("Current lesson") && currentLesson==null)
                        System.out.println("dont do anything");
                    else {
                        pieData = strategy.createData(currentLesson, month, loggedTeacher, getSemester(sem));
                        Platform.runLater(() -> pieChart.getData().addAll(pieData));
                    }

                });
                executorService.execute(thread);
            }
        });
    }
    //" All students","1st sem", "2nd sem", "3rd sem", "4th sem");
    private void ChangePieChartListener2() {
        selectSemPieChart.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object semester) {
                clearPieChart();
                lblNoData.setText("");
                Thread thread = new Thread(() ->{
                    ICreateDataStrategy strategy  = setStrategy();
                    Months month = null;
                    String s = selectMonth.getSelectionModel().getSelectedItem().toString();
                    System.out.println("Enum: "+ s);
                    if(!s.matches("Current lesson") &&
                           ! s.matches("All year")
                    ) month =Months.valueOf(s);
                    if(s.matches("Current lesson") && currentLesson==null)
                        System.out.println("dont do anything");
                    else {
                        pieData = strategy.createData(currentLesson, month, loggedTeacher, getSemester(semester.toString()));
                        Platform.runLater(() -> pieChart.getData().addAll(pieData));
                    }
                });

                executorService.execute(thread);
            }
        });
    }

    enum Period{
        CURRENT_LESSON,
        ALL_YEAR,
        MONTH
    }

    private ICreateDataStrategy setStrategy(){
      //  switch(selectMonth.toString()){
        switch (selectMonth.getSelectionModel().getSelectedItem().toString()){
            case "Current lesson" : return new CreateTodayData();
            case "All year" :return new CreateTotalData();
            default :return new CreateMonthData();
        }
    }

    private int getSemester(String s){
        if (s.toString().matches("1st sem"))
            return 1;
        else if(s.toString().matches("2nd sem"))
            return 2;
        else if(s.toString().matches("3rd sem"))
            return 3;
        else if(s.toString().matches("4th sem"))
            return 4;
        else return -1;
    }

    private Months getPeriod(String string) {
        if(string.toString().matches("Current lesson"))
            return null;
        if(string.toString().matches("All year"))
            return null;
        else
            return Months.valueOf(string);
    }

    private void createMonthData(ICreateDataStrategy strategy, Months month, int semester ) {
        alreadyInitialized=true;
        pieData= strategy.createData(null, month,
                loggedTeacher, semester);
        Platform.runLater(()-> pieChart.getData().addAll(pieData)
        );
        setCaption();
        HoverChart.listenerPieChart(pieChart, caption, pieChart.getData());
    }

    private void createAllClassesData(ICreateDataStrategy strategy, int semester) {
        alreadyInitialized=true;
        pieData= strategy.createData(null, null,
                loggedTeacher, semester);
        Platform.runLater(()->pieChart.getData().addAll(pieData));
        setCaption();
        HoverChart.listenerPieChart(pieChart, caption, pieChart.getData());
    }

    private void createTodayData(ICreateDataStrategy strategy, int semester){
        strategy = new CreateTodayData();
        pieData= strategy.createData(currentLesson,
                null, null, -1);
        Platform.runLater(() ->pieChart.getData().addAll(pieData));
        HoverChart.listenerPieChart(pieChart, caption, pieChart.getData());
        setCaption();
    }


    private void setCombobox() {
        for(Months m: Months.values())
            comboboxOptions.add(m.name());
        selectMonth.getItems().addAll(comboboxOptions);
        selectMonth.getSelectionModel().selectFirst();
    }

    /**
     * list contains only absent student today
     */
    private void initAbsenceList() {
       if(currentLesson!=null) {
            List<Student> absentStudents = model.getAbsentToday(currentLesson);
            ObservableList<String> absentStudentsNames = FXCollections.observableArrayList();
            for(Student s : absentStudents) absentStudentsNames.add(s.getName());
            absenceList.setItems(absentStudentsNames);
        }
    }

    private void setChangeTableView() {
        setCellValueFact();
        setAcceptButtons();
        setDeclineButtons();
        model.setChanges(loggedTeacher);
        changeTable.setItems(model.getChanges());
        acceptColumnn.setCellFactory(cellFactory);
        declineColumnn.setCellFactory(cFactory);
    }

    private void setDeclineButtons() {
         cFactory =
                new Callback<TableColumn<ChangeRequest, Void>, TableCell<ChangeRequest, Void>>() {
            @Override
            public TableCell<ChangeRequest, Void> call(final TableColumn<ChangeRequest, Void> param) {
                final TableCell<ChangeRequest, Void> cell = new TableCell<ChangeRequest, Void>() {

                    private final Button btn = new Button("Decline");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ChangeRequest change = getTableView().getItems().get(getIndex());
                            model.getChanges().remove(change);
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
    }

    private void setAcceptButtons() {
        cellFactory = new Callback<TableColumn<ChangeRequest, Void>,
                TableCell<ChangeRequest, Void>>() {
            @Override
            public TableCell<ChangeRequest, Void> call(final TableColumn<ChangeRequest, Void> param) {
                final TableCell<ChangeRequest, Void> cell = new TableCell<ChangeRequest, Void>() {

                    private final Button btn = new Button("Accept");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ChangeRequest change = getTableView().getItems().get(getIndex());
                            model.getChanges().remove(change);
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
    }

    private void setCellValueFact() {
        nameColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("studentName"));
        typeColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("subjectName"));
        dateColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, String>("date"));
        acceptColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));
        declineColumnn.setCellValueFactory(new PropertyValueFactory<ChangeRequest, Void>(""));

        nameColumnn.setText("Student");
        typeColumnn.setText("Subject");
        dateColumnn.setText("Date");
    }
    private void showInfoTeacher() {
        teacherName.setText(this.loggedTeacher.getName());
        teacherProgram.setText(this.loggedTeacher.getDepartment());
    }
    private void showPhoto() {
        String path = loggedTeacher.getPhotoPath();
        javafx.scene.image.Image image = new Image(path);
        imageView.setImage(image);
        final Circle clip = new Circle(39, 39, 39);
        clip.setStroke(Color.BLACK);
        imageView.setClip(clip);
    }

}
