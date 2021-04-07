package dal;

import be.*;
import be.Record;
import dal.dataAccessObjects.*;
import dal.exception.DALexception;

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
    private ChangeRequestDAO changeRequestDAO = new ChangeRequestDAO();
    private RecordDAO recordDAO = new RecordDAO();
    private ScheduleEntityDAO scheduleEntityDAO = new ScheduleEntityDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();

    public static FacadeDAL getInstance(){
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
    public PasswordObject getPassword(String email, UserType userType) throws DALexception {
        return loginDAO.getPassword(email,userType);
    }

    @Override
    public boolean emailExists(String email, UserType userType) throws DALexception {
        return loginDAO.emailExists(email, userType);
    }

    @Override
    public Student getStudent(String email) throws DALexception {
        return studentDAO.getStudent(email);
    }

    @Override
    public void createChangeRequest(ChangeRequest newRequest) throws DALexception {
        changeRequestDAO.create(newRequest);
    }

    @Override
    public void createRecord(Record record) throws DALexception {
        recordDAO.create(record);
    }

    @Override
    public List<Record> getAbsentDays(int studentId) throws DALexception {
        return recordDAO.getAbsentDays(studentId);
    }

    @Override
    public ScheduleEntity getCurrentLesson(int courseId) throws DALexception {
        return scheduleEntityDAO.getCurrentEntity(courseId);
    }

    @Override
    public Subject getSubject(int id) throws DALexception {
        return subjectDAO.getSubject(id);
    }

    @Override
    public Teacher getTeacher(int id) throws DALexception {
        return teacherDAO.getTeacher(id);
    }

    @Override
    public Teacher getTeacher(String email) throws DALexception {
        return teacherDAO.getTeacher(email);
    }

    @Override
    public int getNumberOfPresentDays(Student student, Months month) throws DALexception {
        return absenceData.getNumberOfPresentDays(student, month);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) throws DALexception {
        return absenceData.getNumberOfAbsentDays(student, month);
    }

    /*

    @Override
    public int getNumberOfPresentDays(Student student, Months month) {
        return 0;
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) {
        return 0;
    }

     */

    @Override
    public List<Student> getAllStudents() throws DALexception {
        return studentDAO.getAll();
    }

    @Override
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return absenceData.getAbsentToday(scheduleEntity);
    }

    @Override
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return absenceData.getPresentToday(scheduleEntity);
    }

    @Override
    public int getNumberOfPresentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return absenceData.getNumberOfPresentToday(scheduleEntity);
    }

    @Override
    public int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return absenceData.getNumberOfAbsentToday(scheduleEntity);
    }

    @Override
    public int getAbsForDay(Enum dayOfWeek) throws DALexception {
        return absenceData.getAbsForDay(dayOfWeek);
    }

    @Override
    public int getPresentForDay(Enum dayOfWeek) throws DALexception {
        return absenceData.getPresentForDay(dayOfWeek);
    }
}
