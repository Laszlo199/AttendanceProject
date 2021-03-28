package gui.controller;

import be.*;
import be.Record;
import gui.model.StudentDashboardModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {

    private Student loggedStudent;
    private ScheduleEntity currentLesson;
    private StudentDashboardModel model;
    private List<Record> absentDays;

    public void setLoggedStudent(Student student) {
        this.loggedStudent = student;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentLesson = model.getCurrentLesson(loggedStudent.getCourseID());
        this.absentDays = model.getAbsentDays(loggedStudent.getId());
    }

    //when 'save' button is clicked
    //creates a new Record
    public void setCurrentAttendance() {
        LocalDate currentDate = LocalDate.now();
        //here basing on what a user chooses
        boolean isPresent = false;
        //0 is a temporary id
        Record record = new Record(0, loggedStudent.getId(), Date.valueOf(currentDate), currentLesson.getId(), isPresent);
        model.createRecord(record);
    }

    //when 'edit' button
    //creates a changeRequest
    public void editAttendance() {
        //!! insert actual recordId !!
        ChangeRequest newRequest = new ChangeRequest(0, StatusType.PENDING);
        model.createChangeRequest(newRequest);
    }


}
