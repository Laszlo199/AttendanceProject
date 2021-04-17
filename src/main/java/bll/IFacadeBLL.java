package bll;

import be.*;
import be.Record;
import bll.exception.BLLexception;
import dal.exception.DALexception;
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

    String getRandomQuote();

    void requestDeclined(ChangeRequest changeRequest) throws BLLexception;

    //lesson
    ScheduleEntity getCurrentLessonStudent(int courseId) throws BLLexception;
    ScheduleEntity getCurrentLessonTeacher(int teacherId) throws BLLexception;

    //records - absence
    void createRecord(Record record) throws BLLexception;
    List<Record> getAbsentDays(int studentId) throws BLLexception;
    List<Student> getAbsentToday(ScheduleEntity scheduleEntity) throws BLLexception;
    List<Student> getPresentToday(ScheduleEntity scheduleEntity) throws BLLexception;
    int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) throws BLLexception;
    int getNumberOfPresentToday(ScheduleEntity scheduleEntity) throws BLLexception;
    String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception;
    String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception;
    int getNumberOfPresentDays(Student student, Months month) throws BLLexception;
    int getNumberOfAbsentDays(Student loggedStudent, Months month) throws BLLexception;

    //requests
    void createChangeRequest(ChangeRequest newRequest) throws BLLexception;
    List<ChangeRequest> getRequestsForTeacher(int teacherId) throws BLLexception;
    void requestAccepted(ChangeRequest changeRequest) throws BLLexception;

    //Teacher
    List<Teacher> getAllTeacher() throws BLLexception;
    void updateTeacher(Teacher oldTeacher, Teacher newTeacher) throws BLLexception;
    void createTeacher(Teacher teacher) throws BLLexception;
    void deleteTeacher(Teacher teacher) throws BLLexception;
    Teacher getTeacher(int teacherId) throws BLLexception;
    Teacher getTeacher(String email) throws BLLexception;

    //Student
    List<Student> getAllStudents() throws BLLexception;
    void updateStudent(Student oldStudent, Student newStudent) throws BLLexception;
    void createStudent(Student student) throws BLLexception;
    void deleteStudent(Student student) throws BLLexception;
    Student getStudent(String email) throws BLLexception;

    //Subject
    List<Subject> getAllSubject() throws BLLexception;
    void updateSubject(Subject oldSubject, Subject newSubject) throws BLLexception;
    void createSubject(Subject subject) throws BLLexception;
    void deleteSubject(Subject subject) throws BLLexception;
    Subject getSubject(int subjectId) throws BLLexception;

    //Course
    List<Course> getAllCourse() throws BLLexception;
    void updateCourse(Course oldCourse, Course newCourse) throws BLLexception;
    void createCourse(Course course) throws BLLexception;
    void deleteCourse(Course course) throws BLLexception;
    Course getCourse(int courseId) throws BLLexception;

    int getNumberOfAllStudents(ScheduleEntity currentLesson) throws BLLexception;

    int getClassPresentDays(Teacher teacher, Months month) throws BLLexception;

    int getClassAbsentDays(Teacher teacher, Months month) throws BLLexception;

    int getTotalNoPresentDaysInClass(Teacher teacher) throws BLLexception;

    int getTotalNoAbsentDaysInClass(Teacher teacher) throws BLLexception;
}
