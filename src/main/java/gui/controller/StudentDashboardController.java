package gui.controller;

import be.Record;
import be.ScheduleEntity;
import be.Student;
import gui.model.StudentDashboardModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {

    private Student loggedStudent;
    private ScheduleEntity currentLesson;
    private StudentDashboardModel model;

    public void setLoggedStudent(Student student) {
        this.loggedStudent = student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
    }

    //creates a new Record
    public void setCurrentAttendance() {
        LocalDate currentDate = LocalDate.now();
        //here basing on what a user chooses
        boolean isPresent = false;
        //0 is a temporary id
        Record record = new Record(0, loggedStudent.getId(), Date.valueOf(currentDate), currentLesson.getId(), isPresent);
        model.createRecord(record);
    }


}
