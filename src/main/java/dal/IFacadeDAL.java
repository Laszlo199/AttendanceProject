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
    ScheduleEntity getCurrentLesson(int courseId) throws DALexception;
    Subject getSubject(int id) throws DALexception;
    Teacher getTeacher(int id) throws DALexception;
    Teacher getTeacher(String email) throws DALexception;
    List<ChangeRequest> getRequestsForTeacher(int teacherId) throws DALexception;

    void requestAccepted(ChangeRequest changeRequest) throws DALexception;

    void requestDeclined(ChangeRequest changeRequest) throws DALexception;

    boolean isStudentPresent(int id);

    boolean isDataStudentPresent(int id) throws DALexception;

   List<Student> getAllStudents() throws DALexception;
}
