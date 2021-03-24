package dal;

import be.ScheduleEntity;
import be.Student;
import bll.OverviewAbsenceCalculator;

import java.util.List;

/**
 * here are the methods i need for my calculator
 * @author Kuba
 * @date 3/22/2021 9:01 PM
 */
public interface IAbsenceData {
    int getNumberOfPresentDays(Student student, OverviewAbsenceCalculator.Timeframe timeframe);
    int getNumberOfAbsentDays(Student student, OverviewAbsenceCalculator.Timeframe timeframe);
    List<Student> getAllStudents();
    List<Student> getAbsentToday(ScheduleEntity scheduleEntity);
    List<Student> getPresentToday(ScheduleEntity scheduleEntity);
    int getNumberOfPresentToday(ScheduleEntity scheduleEntity);
    int getNumberOfAbsentToday(ScheduleEntity scheduleEntity);

}
