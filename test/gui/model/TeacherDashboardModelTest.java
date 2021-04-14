package gui.model;

import be.ScheduleEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kuba
 * @date 4/14/2021 9:04 AM
 */
class TeacherDashboardModelTest {

    @Test
    void getCurrentLesson() {
        TeacherDashboardModel teacherDashboardModel = new TeacherDashboardModel();
        ScheduleEntity currentLesson = teacherDashboardModel.getCurrentLesson(1);
        System.out.println(currentLesson.toString());
    }
}