package bll.managers;

import be.ScheduleEntity;
import dal.dataAccessObjects.ScheduleEntityDAO;

public class ScheduleEntityManager {
    private ScheduleEntityDAO scheduleEntityDAO;

    public ScheduleEntityManager() {
        scheduleEntityDAO = new ScheduleEntityDAO();
    }


    public ScheduleEntity getCurrentLesson(int courseId) {
        return scheduleEntityDAO.getCurrentEntity(courseId);
    }
}
