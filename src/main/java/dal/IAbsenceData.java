package dal;

import be.Months;
import be.ScheduleEntity;
import be.Student;
import be.Teacher;
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
    int getTotalNumberOfPresentDays(Student student) throws DALexception;
    int getTotalNumberOfAbsentDays(Student student) throws DALexception;
    List<Student> getAllStudents() throws DALexception;
    List<Student> getAbsentToday(ScheduleEntity scheduleEntity) throws DALexception;
    List<Student> getPresentToday(ScheduleEntity scheduleEntity) throws DALexception;
    int getNumberOfPresentToday(ScheduleEntity scheduleEntity, int sem) throws DALexception;
    int getNumberOfAbsentToday(ScheduleEntity scheduleEntity, int sem) throws DALexception;
    int getAbsForDay(Enum dayOfWeek) throws DALexception;
    int getPresentForDay(Enum dayOfWeek) throws DALexception;

    boolean isStudentPresent(int id);

    boolean isDataStudentPresent(int id) throws DALexception;

    int getNumberOfAllStudents(ScheduleEntity currentLesson, int sem) throws DALexception;

    List<Student> getTaughtStudents(Teacher teacher, int sem) throws DALexception;
    int getTotalNoPresentDaysInClass(Teacher teacher, int sem) throws DALexception;

    int getTotalNoAbsentDaysInClass(Teacher teacher, int sem) throws DALexception;
}
