package gui.model;

import be.*;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.exception.BLLexception;
import gui.controller.TeacherViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TeacherDashboardModel {

    private IFacadeBLL logic;
    private ObservableList<Student> obsStudents = FXCollections.observableArrayList();


    public TeacherDashboardModel() {
        logic = FacadeBLL.getInstance();
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
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity){
        try {
            return logic.getAbsentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns list of present students on current lesson
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity){
        try {
            return logic.getPresentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns number of absent students from current lesson
    public int getNumberOfAbsent(ScheduleEntity scheduleEntity){
        try {
            return logic.getNumberOfAbsentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }

    }

    //returns number of present students from current lesson
    public int getNumberOfPresent(ScheduleEntity scheduleEntity){
        try {
            return logic.getNumberOfPresentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }
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

    public Subject getSubject(int subjectId) {
        try {
            return logic.getSubject(subjectId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public int getNumberOfAllStudents(ScheduleEntity currentLesson) {
        try {
            return logic.getNumberOfAllStudents(currentLesson);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 0;
    }
}