package gui.model;

import be.ChangeRequest;
import be.ScheduleEntity;
import be.Student;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.SingleDayAbsenceCalculator;
import bll.exception.BLLexception;
import java.util.ArrayList;
import java.util.List;

public class TeacherDashboardModel {

    private IFacadeBLL logic;
    private ScheduleEntity currentLesson;
    private SingleDayAbsenceCalculator singleDayAbsenceCalculator;


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
}