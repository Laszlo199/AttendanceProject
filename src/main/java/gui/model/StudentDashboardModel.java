package gui.model;

import be.*;
import be.Record;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.managers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class StudentDashboardModel {
    private IFacadeBLL logic;
    private ScheduleEntityManager scheduleEntityManager;
    private RecordManager recordManager;
    private ChangeRequestManager changeRequestManager;
    private TeacherManager teacherManager;
    private SubjectManager subjectManager;
    private ObservableList<Record> recordObservableList;

    public StudentDashboardModel() {
        scheduleEntityManager = new ScheduleEntityManager();
        recordManager = new RecordManager();
        changeRequestManager = new ChangeRequestManager();
        teacherManager = new TeacherManager();
        subjectManager = new SubjectManager();
        logic = FacadeBLL.getInstance();
        recordObservableList = FXCollections.observableArrayList();
    }

    public ObservableList<Record> getRecordObservableList() {
        return recordObservableList;
    }

    public ScheduleEntity getCurrentLesson(int courseId) {
        return scheduleEntityManager.getCurrentLesson(courseId);
    }

    public Subject getSubject(int subjectId) {
        return subjectManager.getSubject(subjectId);
    }

    public Teacher getTeacher(int teacherId) {
        return teacherManager.getTeacher(teacherId);
    }

    public void createRecord(Record record) {
        recordManager.createRecord(record);
    }

    public List<Record> getAbsentDays(int studentId) {
        return recordManager.getAbsentDays(studentId);
    }
    public void setAbsentDays(int studentId){
        this.recordObservableList.addAll(getAbsentDays(studentId));
        //this.recordObservableList.add(new Record(1, 1, new Date(System.currentTimeMillis()), 1, false));
    }

    public void createChangeRequest(ChangeRequest newRequest) {
        changeRequestManager.create(newRequest);
    }

    public String getRandQuote() {
        return logic.getRandomQuote();
    }
}
