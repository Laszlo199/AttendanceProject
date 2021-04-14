package gui.model;

import be.*;
import be.Record;
import bll.FacadeBLL;
import bll.IFacadeBLL;

import bll.exception.BLLexception;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class StudentDashboardModel {
    private IFacadeBLL logic;

    private ObservableList<Record> recordObservableList;

    public StudentDashboardModel() {
        logic = FacadeBLL.getInstance();
        recordObservableList = FXCollections.observableArrayList();
    }

    public ObservableList<Record> getRecordObservableList() {
        return recordObservableList;
    }

    public ScheduleEntity getCurrentLesson(int courseId) {
        try {
            return logic.getCurrentLessonStudent(courseId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public Course getCourse(int courseId) {
        try {
            return logic.getCourse(courseId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public Subject getSubject(int subjectId) {
        try {
            return logic.getSubject(subjectId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public Teacher getTeacher(int teacherId) {
        try {
            return logic.getTeacher(teacherId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public void createRecord(Record record) {
        try {
            logic.createRecord(record);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public List<Record> getAbsentDays(int studentId) {
        try {
            return logic.getAbsentDays(studentId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public void setAbsentDays(int studentId){
        this.recordObservableList.addAll(getAbsentDays(studentId));
        //this.recordObservableList.add(new Record(1, 1, new Date(System.currentTimeMillis()), 1, false));
    }

    public void createChangeRequest(ChangeRequest newRequest) {
        try {
            logic.createChangeRequest(newRequest);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public String getRandQuote() {
        return logic.getRandomQuote();
    }

    public int getPresentDays(Student student, Months month) {
        try {
            return logic.getNumberOfPresentDays(student, month);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 0;
    }

    public int getAbsentDays(Student loggedStudent, Months month) {
        try {
            return logic.getNumberOfAbsentDays(loggedStudent, month);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 1;
    }
}
