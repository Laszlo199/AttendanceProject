package gui.model;

import be.Course;
import be.Student;
import be.Subject;
import be.Teacher;
import javafx.collections.ObservableList;

public class AdminModel {
    private ObservableList<Teacher> obsTeacher;
    private ObservableList<Student> obsStudent;
    private ObservableList<Subject> obsSubject;
    private ObservableList<Course> obsCourse;



    //Teachers----------
    public ObservableList<Teacher> getAllTeachers() {

        return obsTeacher;
    }
    public void loadTeachers() {

    }
    public void delete(Teacher selectedTeacher) {

    }
    public void save(Teacher teacher) {

    }
    public void updateTeacher(Teacher oldTeacher, Teacher newTeacher){

    }


    //Students---------------------
    public ObservableList<Student> getAllStudents() {

        return obsStudent;
    }
    public void loadStudents() {

    }
    public void delete(Student selectedStudent) {

    }
    public void save(Student student) {

    }
    public void updateStudent(Student oldStudent, Student newStudent){

    }



    //Subjects---------------
    public ObservableList<Subject> getAllSubjects() {

        return obsSubject;
    }
    public void loadSubjects() {

    }
    public void delete(Subject selectedSubject) {

    }
    public void save(Subject subject) {

    }
    public void updateSubject(Subject oldSubject, Subject newSubject){

    }



    //Courses------------
    public ObservableList<Course> getAllCourses() {

        return obsCourse;
    }
    public void loadCourses() {

    }
    public void delete(Course selectedCourse) {

    }
    public void save(Course course) {

    }
    public void updateCourse(Course oldCourse, Course newCourse){

    }
}
