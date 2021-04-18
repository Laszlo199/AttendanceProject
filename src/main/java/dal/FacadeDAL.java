package dal;

import be.*;
import be.Record;
import dal.dataAccessObjects.*;
import dal.exception.DALexception;

import java.sql.Date;
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
    private CourseDAO courseDAO = new CourseDAO();

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
    public ScheduleEntity getCurrentLessonStudent(int courseId) throws DALexception {
        return scheduleEntityDAO.getCurrentLessonStudent(courseId);
    }

    @Override
    public ScheduleEntity getCurrentLessonTeacher(int teacherId) throws DALexception {
        return scheduleEntityDAO.getCurrentLessonTeacher(teacherId);
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

    //Teacher for AdminView
    @Override
    public void updateTeacher(Teacher oldTeacher, Teacher newTeacher) throws DALexception {
        teacherDAO.update(oldTeacher, newTeacher);
    }

    @Override
    public List<Teacher> getAllTeacher() throws DALexception{
        return teacherDAO.getAll();
    }

    @Override
    public void createTeacher(Teacher teacher) throws DALexception{
        teacherDAO.create(teacher);
    }

    @Override
    public  void deleteTeacher(Teacher teacher) throws DALexception{
        teacherDAO.delete(teacher);
    }

    //Student for AdminView


    @Override
    public void updateStudent(Student oldStudent, Student newStudent) throws DALexception {
        studentDAO.update(oldStudent,newStudent);
    }

    @Override
    public void createStudent(Student student) throws DALexception{
        studentDAO.create(student);
    }

    @Override
    public void deleteStudent(Student student) throws DALexception{
        studentDAO.delete(student);
    }


    //Subject for AdminView
    @Override
    public List<Subject> getAllSubject() throws DALexception {
        return subjectDAO.getAll();
    }


    public void createSubject(Subject subject) throws DALexception{
        subjectDAO.create(subject);
    }

    @Override
    public void updateSubject(Subject oldSubject, Subject newSubject) throws DALexception{
        subjectDAO.update(oldSubject, newSubject);
    }

    @Override
    public void deleteSubject(Subject subject) throws DALexception{
        subjectDAO.delete(subject);
    }

    //Course for AdminView
    @Override
    public List<Course> getAllCourse() throws DALexception {
        return courseDAO.getAll();
    }

    public void createCourse(Course course) throws DALexception{
        courseDAO.create(course);
    }
    @Override
    public void updateCourse(Course oldCourse, Course newCourse) throws DALexception{
        courseDAO.update(oldCourse,newCourse);
    }

    @Override
    public void deleteCourse(Course course) throws DALexception{
        courseDAO.delete(course);
    }

    @Override

    public Course getCourse(int courseId) throws DALexception {
        return courseDAO.getCourse(courseId);
    }

    public int getNumberOfAllStudents(ScheduleEntity currentLesson) throws DALexception {
        return absenceData.getNumberOfAllStudents(currentLesson);
    }

    @Override
    public List<Student> getTaughtStudents(Teacher teacher) throws DALexception {
        return absenceData.getTaughtStudents(teacher);

    }

    @Override
    public boolean hasRecordToday(int studentId, java.sql.Date date) throws DALexception {
        return recordDAO.hasRecordToday(studentId,date);
    }

    @Override
    public int getTotalNoPresentDaysInClass(Teacher teacher) throws DALexception {
        return absenceData.getTotalNoPresentDaysInClass(teacher);
    }

    @Override
    public int getTotalNoAbsentDaysInClass(Teacher teacher) throws DALexception {
        return absenceData.getTotalNoAbsentDaysInClass(teacher);
    }
}
