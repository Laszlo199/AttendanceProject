package gui.model;

import be.Record;
import be.ScheduleEntity;
import bll.RecordManager;
import bll.ScheduleEntityManager;

public class StudentDashboardModel {

    private ScheduleEntityManager scheduleEntityManager;
    private RecordManager recordManager;

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
}
