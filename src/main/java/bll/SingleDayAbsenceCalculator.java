package bll;

import be.ScheduleEntity;
import be.Student;
import dal.AbsenceData;
import dal.IAbsenceData;

import java.util.List;

/**
 * @author kamila
 */
public class SingleDayAbsenceCalculator {

    private ScheduleEntity scheduleEntity;

    private IAbsenceData dal;

    public SingleDayAbsenceCalculator(ScheduleEntity scheduleEntity) {
        dal = new AbsenceData();
        this.scheduleEntity = scheduleEntity;
    }

    public List<Student> getAbsentToday() {
        return dal.getAbsentToday(this.scheduleEntity);
    }

    public List<Student> getPresentToday() {
        return dal.getPresentToday(this.scheduleEntity);
    }

    public int getNumberOfAbsentStudents() {
        return dal.getNumberOfAbsentToday(this.scheduleEntity);
    }

    public int getNumberOfPresentStudents() {
        return dal.getNumberOfPresentToday(this.scheduleEntity);
    }
}
