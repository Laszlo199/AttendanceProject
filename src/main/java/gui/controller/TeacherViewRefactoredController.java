package gui.controller;

import be.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import gui.model.TeacherDashboardModel;
import gui.strategy.CreateMonthData;
import gui.strategy.CreateTodayData;
import gui.strategy.ICreateDataStrategy;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * @author Kuba && Kamila
 * @date 4/14/2021 8:02 AM
 */
public class TeacherViewRefactoredController implements Initializable {
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

    private static TeacherDashboardModel model;
    private static ScheduleEntity currentLesson;
    private Teacher loggedTeacher;
    private ObservableList<String> comboboxOptions =
            FXCollections.observableArrayList("Today", "Total");;
    private ObservableList<PieChart.Data> pieData;

   static {
       model = TeacherDashboardModel.getInstance();
   }

    /**
     * log out using command pattern
     * @param actionEvent
     */
    public void logOut(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTime();
        changePieChartListener();
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
        dateLabel.setText(simpleDateFormat2.format(time.getTime()));
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("EEEE");
        dayLabel.setText(simpleDateFormat3.format(time.getTime()));
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
                hourLabel.setText(hour + ":" + minute + ":" + second);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread th1 = new Thread(runnable1);
        th1.start();
    }

    private void initStudentsTableView() {
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        attendanceCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(model.getPresenceForStudent(student.getValue(), TeacherViewController.Timeframe.TOTAL));
            }
        });
        dayCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Student, String> student) {
                return new ReadOnlyObjectWrapper<String>(model.getMostAbsentDay(student.getValue(), TeacherViewController.Timeframe.MONTH));
            }
        });
        model.loadTableView();
        tableview.setItems(model.getObsStudents());
    }

    /**
     * pie chart shows ratio abs days to present days
     */
    private void initPieChart() {
        setCombobox();
        if(currentLesson!=null) {
            int absent = model.getNumberOfAbsent(currentLesson);
            int present = model.getNumberOfPresent(currentLesson);
            int noAllStudents = model.getNumberOfAllStudents(currentLesson);
            int sumOfStudents = absent + present + noAllStudents;
            System.out.println("sum of students: " + sumOfStudents);
            if(sumOfStudents>0) {
                pieData = FXCollections.observableArrayList(
                        new PieChart.Data("Absent", (absent * 100) / sumOfStudents),
                        new PieChart.Data("Present", (present * 100) / sumOfStudents),
                        new PieChart.Data("No data", ((noAllStudents - absent - present) * 100) / sumOfStudents)
                );
                pieChart.setData(pieData);
                pieChart.getData().forEach(data -> {
                    String percentage = String.format("%.2f%%", (data.getPieValue() / 100));
                    Tooltip toolTip = new Tooltip(percentage);
                    Tooltip.install(data.getNode(), toolTip);
                });
            }
        }
    }

    /**
     * when user selects other value in combobox show corresponding data
     */
    private void changePieChartListener() {
        selectMonth.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object n) {
                ICreateDataStrategy strategy;
                pieChart.getData().clear();
                if(n.toString().matches("Today")){
                    strategy = new CreateTodayData();
                    pieChart.getData().addAll(strategy.createData(currentLesson,
                            null, null));
                    //later add some inromation if there is no record
                }
                else if(n.toString().matches("Total")){
                    System.out.println("Total");
                    //add later
                }
                else{
                    strategy = new CreateMonthData();
                    //I hope it works
                    pieChart.getData().addAll(strategy.createData(null, Months.valueOf((String) n),
                            loggedTeacher));
                }

            }
        });
    }

    private void setCombobox() {
        for(Months m: Months.values())
            comboboxOptions.add(m.name());
        selectMonth.getItems().addAll(comboboxOptions);
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

    public void setTeacher(Teacher teacher) {
        this.loggedTeacher = teacher;
        this.currentLesson = model.getCurrentLesson(loggedTeacher.getId());
        if(currentLesson==null)
            System.out.println("current lesson is null");
        else
            System.out.println(currentLesson.toString());
        initAbsenceList();
        initPieChart();
        initStudentsTableView();
    }
}
