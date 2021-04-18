package dal;

import be.*;
import be.Record;
import dal.exception.DALexception;

import java.util.Date;
import java.util.List;

/**
 * @author Kuba
 * @date 3/24/2021 1:36 PM
 */
public interface IFacadeDAL extends IAbsenceData {

    boolean establishedConnection();
    PasswordObject getPassword(String email, UserType userType) throws DALexception;
    boolean emailExists(String email, UserType userType) throws DALexception;

    //absences
    ScheduleEntity getCurrentLessonStudent(int courseId) throws DALexception;
    ScheduleEntity getCurrentLessonTeacher(int teacherId) throws DALexception;
    void createChangeRequest(ChangeRequest newRequest) throws DALexception;
    List<ChangeRequest> getRequestsForTeacher(int teacherId) throws DALexception;
    void createRecord(Record record) throws DALexception;
    List<Record> getAbsentDays(int studentId)throws DALexception;
    void requestAccepted(ChangeRequest changeRequest) throws DALexception;
    void requestDeclined(ChangeRequest changeRequest) throws DALexception;
    boolean isStudentPresent(int id);
    boolean isDataStudentPresent(int id) throws DALexception;


    // teacher
    List<Teacher> getAllTeacher() throws DALexception;
    void updateTeacher(Teacher oldTeacher, Teacher newTeacher) throws DALexception;
    void createTeacher(Teacher teacher) throws DALexception;
    void deleteTeacher(Teacher teacher) throws DALexception;
    Teacher getTeacher(int id) throws DALexception;
    Teacher getTeacher(String email) throws DALexception;

    //Student
    List<Student> getAllStudents() throws DALexception;
    void updateStudent(Student oldStudent, Student newStudent) throws DALexception;
    void createStudent(Student student) throws  DALexception;
    void deleteStudent(Student student) throws DALexception;
    Student getStudent(String email) throws DALexception;

    //Subject
    List<Subject> getAllSubject() throws DALexception;
    void updateSubject(Subject oldSubject, Subject newSubject) throws DALexception;
    void createSubject(Subject subject) throws DALexception;
    void deleteSubject(Subject subject) throws DALexception;
    Subject getSubject(int id) throws DALexception;

    //Course
    List<Course> getAllCourse() throws DALexception;
    void updateCourse(Course oldCourse,Course newCourse) throws DALexception;
    void createCourse(Course course) throws DALexception;
    void deleteCourse(Course course) throws DALexception;
    Course getCourse(int courseId) throws DALexception;

    int getNumberOfAllStudents(ScheduleEntity currentLesson) throws DALexception;

   List<Student> getTaughtStudents(Teacher teacher) throws DALexception;




    // Absent Date From Records by StudentID.
    boolean hasRecordToday(int studentId,java.sql.Date date) throws DALexception;
}
