package gui.model;

import be.ChangeRequest;
import be.Record;
import be.ScheduleEntity;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.managers.ChangeRequestManager;
import bll.managers.RecordManager;
import bll.managers.ScheduleEntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Array;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StudentDashboardModel {
    private IFacadeBLL logic = FacadeBLL.getInstance();
    private ScheduleEntityManager scheduleEntityManager;
    private RecordManager recordManager;
    private ChangeRequestManager changeRequestManager = new ChangeRequestManager();
    private ObservableList<Record> recordObservableList =
            FXCollections.observableArrayList();

    public ObservableList<Record> getRecordObservableList() {
        return recordObservableList;
    }

    public StudentDashboardModel() {
        scheduleEntityManager = new ScheduleEntityManager();
        recordManager = new RecordManager();
    }

    public ScheduleEntity getCurrentLesson(int courseId) {
        return scheduleEntityManager.getCurrentLesson(courseId);
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
