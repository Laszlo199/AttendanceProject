package dal;

import be.Months;
import be.ScheduleEntity;
import be.Student;
import bll.OverviewAbsenceCalculator;
import dal.exception.DALexception;

import java.util.List;

/**
 * here are the methods i need for my calculator
 * @author Kuba
 * @date 3/22/2021 9:01 PM
 */
public interface IAbsenceData {
    int getNumberOfPresentDays(Student student, Months month) throws DALexception;
    int getNumberOfAbsentDays(Student student, Months month) throws DALexception;
    List<Student> getAllStudents() throws DALexception;
    List<Student> getAbsentToday(ScheduleEntity scheduleEntity) throws DALexception;
    List<Student> getPresentToday(ScheduleEntity scheduleEntity) throws DALexception;
    int getNumberOfPresentToday(ScheduleEntity scheduleEntity) throws DALexception;
    int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) throws DALexception;
    int getAbsForDay(Enum dayOfWeek) throws DALexception;
    int getPresentForDay(Enum dayOfWeek) throws DALexception;

}
