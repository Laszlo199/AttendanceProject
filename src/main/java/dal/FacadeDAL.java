package dal;

import be.Months;
import be.ScheduleEntity;
import be.Student;
import be.UserType;
import bll.FacadeBLL;
import bll.OverviewAbsenceCalculator;
import dal.dataAccessObjects.LoginDAO;
import dal.dataAccessObjects.StudentDAO;

import java.util.List;

/**
 * @author Kuba
 * @date 3/24/2021 1:36 PM
 */
public class FacadeDAL implements IFacadeDAL, IAbsenceData{
    private static FacadeDAL facadeDAL;
 private DBConnector dbConnector = new DBConnector();
 private LoginDAO loginDAO = new LoginDAO();
 private StudentDAO studentDAO = new StudentDAO();
 private IAbsenceData absenceData = new AbsenceData();

    public static IFacadeDAL getInstance(){
        if(facadeDAL==null)
            facadeDAL = new FacadeDAL();
        return facadeDAL;
    }

    private FacadeDAL() {
    }

    @Override
    public boolean establishedConnection() {
        return dbConnector.isDbConnected();
    }

    @Override
    public String getPassword(String email, UserType userType) {
        return loginDAO.getPassword(email,userType);
    }

    @Override
    public boolean emailExists(String email, UserType userType) {
        return loginDAO.emailExists(email, userType);
    }

    @Override
    public Student getStudent(String email) {
        return studentDAO.getStudent(email);
    }
/*
    @Override
    public int getNumberOfPresentDays(Student student, Months month) {
        return absenceData.getNumberOfPresentDays(student, month);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) {
        return absenceData.getNumberOfAbsentDays(student, month);
    }

 */

    @Override
    public int getNumberOfPresentDays(Student student, Months month) {
        return 0;
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) {
        return 0;
    }

    @Override
    public List<Student> getAllStudents() {
        return null;
    }

    @Override
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity) {
        return null;
    }

    @Override
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity) {
        return null;
    }

    @Override
    public int getNumberOfPresentToday(ScheduleEntity scheduleEntity) {
        return 0;
    }

    @Override
    public int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) {
        return 0;
    }

    @Override
    public int getAbsForDay(Enum dayOfWeek) {
        return 0;
    }

    @Override
    public int getPresentForDay(Enum dayOfWeek) {
        return 0;
    }
}
