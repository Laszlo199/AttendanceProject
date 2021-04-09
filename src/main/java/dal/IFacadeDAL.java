package dal;

import be.*;
import be.Record;
import dal.exception.DALexception;

import java.util.List;

/**
 * @author Kuba
 * @date 3/24/2021 1:36 PM
 */
public interface IFacadeDAL extends IAbsenceData {
    boolean establishedConnection();

    PasswordObject getPassword(String email, UserType userType) throws DALexception;

    boolean emailExists(String email, UserType userType) throws DALexception;

    Student getStudent(String email) throws DALexception;

    void createChangeRequest(ChangeRequest newRequest) throws DALexception;
    void createRecord(Record record) throws DALexception;
    List<Record> getAbsentDays(int studentId)throws DALexception;

    ScheduleEntity getCurrentLessonStudent(int courseId) throws DALexception;

    ScheduleEntity getCurrentLessonTeacher(int teacherId) throws DALexception;

    Subject getSubject(int id) throws DALexception;
    Teacher getTeacher(int id) throws DALexception;
    Teacher getTeacher(String email) throws DALexception;
    List<ChangeRequest> getRequestsForTeacher(int teacherId) throws DALexception;

    void requestAccepted(ChangeRequest changeRequest) throws DALexception;

    void requestDeclined(ChangeRequest changeRequest) throws DALexception;


    boolean isStudentPresent(int id);

    boolean isDataStudentPresent(int id) throws DALexception;

   List<Student> getAllStudents() throws DALexception;

    // teacher
    List<Teacher> getAllTeacher() throws DALexception;
    void updateTeacher(Teacher oldTeacher, Teacher newTeacher) throws DALexception;
    void createTeacher(Teacher teacher) throws DALexception;
    void deleteTeacher(Teacher teacher) throws DALexception;

    //Student
    List<Student> getAllStudents() throws DALexception;
    void updateStudent(Student oldStudent, Student newStudent) throws DALexception;
    void createStudent(Student student) throws  DALexception;
    void deleteStudent(Student student) throws DALexception;

    //Subject
    List<Subject> getAllSubject() throws DALexception;
    void updateSubject(Subject oldSubject, Subject newSubject) throws DALexception;
    void createSubject(Subject subject) throws DALexception;
    void deleteSubject(Subject subject) throws DALexception;

    //Course
    List<Course> getAllCourse() throws DALexception;
    void updateCourse(Course oldCourse,Course newCourse) throws DALexception;
    void createCourse(Course course) throws DALexception;
    void deleteCourse(Course course) throws DALexception;

}
