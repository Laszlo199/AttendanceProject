package bll;

import be.*;
import be.Record;
import bll.exception.BLLexception;
import gui.controller.TeacherViewController;

import java.util.List;

/**
 * @author Kuba
 * @date 3/24/2021 1:31 PM
 */
public interface IFacadeBLL {
    boolean establishedConnection();

    String getHashedPassword(String password);

    boolean verifyPassword(String email, String password, UserType userType) throws BLLexception;

    Student getStudent(String email) throws BLLexception;

    void requestDeclined(ChangeRequest changeRequest) throws BLLexception;

    String getRandomQuote();

    ScheduleEntity getCurrentLesson(int courseId) throws BLLexception;
    Subject getSubject(int subjectId) throws BLLexception;
    Teacher getTeacher(int teacherId) throws BLLexception;
    Teacher getTeacher(String email) throws BLLexception;
    void createRecord(Record record) throws BLLexception;
    List<Record> getAbsentDays(int studentId) throws BLLexception;
    void createChangeRequest(ChangeRequest newRequest) throws BLLexception;
    List<ChangeRequest> getRequestsForTeacher(int teacherId) throws BLLexception;
    void requestAccepted(ChangeRequest changeRequest) throws BLLexception;

    String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception;

    String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception;

    List<Student> getAllStudents() throws BLLexception;
}
