package gui.model;

import be.ChangeRequest;
import be.Record;
import be.ScheduleEntity;
import bll.managers.ChangeRequestManager;
import bll.managers.RecordManager;
import bll.managers.ScheduleEntityManager;

import java.util.List;

public class StudentDashboardModel {

    private ScheduleEntityManager scheduleEntityManager;
    private RecordManager recordManager;
    private ChangeRequestManager changeRequestManager;

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

    public void createChangeRequest(ChangeRequest newRequest) {
        changeRequestManager.create(newRequest);
    }
}
