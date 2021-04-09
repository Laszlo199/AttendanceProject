package gui.model;

import be.ChangeRequest;
import be.ScheduleEntity;
import be.Student;
import be.WeekDay;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.util.SingleDayAbsenceCalculator;
import bll.exception.BLLexception;
import gui.controller.TeacherViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TeacherDashboardModel {

    private IFacadeBLL logic;
    private ScheduleEntity currentLesson;
    private SingleDayAbsenceCalculator singleDayAbsenceCalculator;
    private ObservableList<Student> obsStudents = FXCollections.observableArrayList();


    public TeacherDashboardModel() {
        logic = FacadeBLL.getInstance();
        singleDayAbsenceCalculator = new SingleDayAbsenceCalculator(currentLesson);
    }

    public List<ChangeRequest> getAllRequests(int teacherId) {
        try {
            return logic.getRequestsForTeacher(teacherId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public void requestAccepted(ChangeRequest changeRequest) {
        try {
            logic.requestAccepted(changeRequest);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void requestDeclined(ChangeRequest changeRequest) {
        try {
            logic.requestDeclined(changeRequest);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ScheduleEntity getCurrentLesson(int teacherId) {
        try {
            return logic.getCurrentLessonTeacher(teacherId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns list of absent students on current lesson
    public List<Student> getAbsentToday(){
        try {
            return singleDayAbsenceCalculator.getAbsentToday();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns list of present students on current lesson
    public List<Student> getPresentToday(){
        try {
            return singleDayAbsenceCalculator.getPresentToday();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns number of absent students from current lesson
    public int getNumberOfAbsent(){
        try {
            return singleDayAbsenceCalculator.getNumberOfAbsentStudents();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }

    }

    //returns number of present students from current lesson
    public int getNumberOfPresent(){
        try {
            return singleDayAbsenceCalculator.getNumberOfPresentStudents();
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }
    }

    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>();
    }

    public String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) {
        try {
            return logic.getPresenceForStudent(student, timeframe);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return null;
    }

    public String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) {
        try {
            return logic.getMostAbsentDay(student, timeframe);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return "nothing";
    }

    public void loadTableView() {
        try {
            obsStudents.addAll(logic.getAllStudents());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Student> getObsStudents() {
        return obsStudents;
    }
}