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
    public List<ChangeRequest> getRequestsForTeacher(int teacherId) throws DALexception {
        return changeRequestDAO.getRequestsForTeacher(teacherId);
    }

    @Override
    public void requestAccepted(ChangeRequest changeRequest) throws DALexception {
        ChangeRequest acceptedRequest = new ChangeRequest(changeRequest.getRecordId(), StatusType.ACCEPTED);
        changeRequestDAO.update(changeRequest, acceptedRequest);
        Record oldRecord = recordDAO.getRecord(changeRequest.getRecordId());
        Record newRecord = new Record(oldRecord.getId(), oldRecord.getStudentId(), oldRecord.getDate(), oldRecord.getScheduleEntityId(), true);
        recordDAO.update(oldRecord, newRecord);
    }

    @Override
    public void requestDeclined(ChangeRequest changeRequest) throws DALexception {
        ChangeRequest declinedRequest = new ChangeRequest(changeRequest.getRecordId(), StatusType.DECLINED);
        changeRequestDAO.update(changeRequest, declinedRequest);
    }

    @Override
    public boolean isStudentPresent(int id) {
        return absenceData.isStudentPresent(id);
    }

    @Override
    public boolean isDataStudentPresent(int id) throws DALexception {
        return absenceData.isDataStudentPresent(id);
    }

    @Override
    public int getNumberOfPresentDays(Student student, Months month) throws DALexception {
        return absenceData.getNumberOfPresentDays(student, month);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) throws DALexception {
        return absenceData.getNumberOfAbsentDays(student, month);
    }

    @Override
    public int getTotalNumberOfPresentDays(Student student) throws DALexception {
        return absenceData.getTotalNumberOfPresentDays(student);
    }

    @Override
    public int getTotalNumberOfAbsentDays(Student student) throws DALexception {
        return absenceData.getTotalNumberOfAbsentDays(student);
    }


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
