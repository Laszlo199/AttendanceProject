package gui.model;

import be.Course;
import be.Student;
import be.Subject;
import be.Teacher;
import bll.IFacadeBLL;
import bll.exception.BLLexception;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {
    IFacadeBLL iFacadeBLL;


    private ObservableList<Teacher> obsTeacher;
    private ObservableList<Student> obsStudent;
    private ObservableList<Subject> obsSubject;
    private ObservableList<Course> obsCourse;



    private AdminModel() {
        obsTeacher = FXCollections.observableArrayList();
        obsStudent= FXCollections.observableArrayList();
        obsSubject = FXCollections.observableArrayList();
        obsCourse = FXCollections.observableArrayList();

    }



    //Teachers----------
    public ObservableList<Teacher> getAllTeachers() {

        return obsTeacher;
    }
    public void loadTeachers() {
        obsTeacher.clear();
        try{
            obsTeacher.addAll(iFacadeBLL.getAllTeacher());
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
    }
    public void delete(Teacher selectedTeacher) {
        try{
          iFacadeBLL.deleteTeacher(selectedTeacher);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsTeacher.remove(selectedTeacher);
    }

    public void save(Teacher teacher) {
        try{
            iFacadeBLL.createTeacher(teacher);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsTeacher.add(teacher);
    }

    public void updateTeacher(Teacher oldTeacher, Teacher newTeacher){
        try{
            iFacadeBLL.updateTeacher(oldTeacher,newTeacher);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsTeacher.clear();
        obsTeacher.addAll();
    }



    //Students---------------------
    public ObservableList<Student> getAllStudents() {

        return obsStudent;
    }

    public void loadStudents() {
        obsStudent.clear();
        try{
            obsStudent.addAll(iFacadeBLL.getAllStudent());
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
    }

    public void delete(Student selectedStudent) {
        try{
            iFacadeBLL.deleteStudent(selectedStudent);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsStudent.remove(selectedStudent);
    }

    public void save(Student student) {
        try{
            iFacadeBLL.createStudent(student);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsStudent.add(student);
    }

    public void updateStudent(Student oldStudent, Student newStudent){
        try{
            iFacadeBLL.updateStudent(oldStudent,newStudent);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsStudent.clear();
        obsStudent.addAll();
    }



    //Subjects---------------
    public ObservableList<Subject> getAllSubjects() {

        return obsSubject;
    }

    public void loadSubjects() {
        obsSubject.clear();
        try{
            obsSubject.addAll(iFacadeBLL.getAllSubject());
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
    }

    public void delete(Subject selectedSubject) {
        try{
            iFacadeBLL.deleteSubject(selectedSubject);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsSubject.remove(selectedSubject);
    }

    public void save(Subject subject) {
        try{
            iFacadeBLL.createSubject(subject);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsSubject.add(subject);
    }

    public void updateSubject(Subject oldSubject, Subject newSubject){
        try{
            iFacadeBLL.updateSubject(oldSubject,newSubject);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsSubject.clear();
        obsSubject.addAll();
    }



    //Courses------------
    public ObservableList<Course> getAllCourses() {

        return obsCourse;
    }

    public void loadCourses() {
        obsCourse.addAll();
        try{
            obsCourse.addAll(iFacadeBLL.getAllCourse());
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
    }

    public void delete(Course selectedCourse) {
        try{
            iFacadeBLL.deleteCourse(selectedCourse);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
       obsCourse.remove(selectedCourse);
    }

    public void save(Course course) {
        try{
            iFacadeBLL.createCourse(course);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsCourse.add(course);
    }

    public void updateCourse(Course oldCourse, Course newCourse){
        try{
            iFacadeBLL.updateCourse(oldCourse,newCourse);
        }catch (BLLexception blLexception){
            blLexception.printStackTrace();
        }
        obsCourse.clear();
        obsCourse.addAll();
    }

}
