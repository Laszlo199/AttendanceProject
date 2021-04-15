package gui.model;

import be.*;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.exception.BLLexception;
import gui.controller.TeacherViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TeacherDashboardModel {

    private  IFacadeBLL logic;
    private  ObservableList<Student> obsStudents = FXCollections.observableArrayList();
    private static TeacherDashboardModel instance;
    ExecutorService executorService = Executors.newSingleThreadExecutor();


    private TeacherDashboardModel() {
        logic = FacadeBLL.getInstance();
    }

    public void loadCache() {
        LoadData loadData = new LoadData();
        loadData.valueProperty().addListener((observableValue, students, t1) -> {
            obsStudents.addAll(t1);
        });
        executorService.execute(loadData);
    }

    public class LoadData extends Task<List<Student>>{
        @Override
        protected List<Student> call() throws Exception {
            List<Student> students = new ArrayList<>();
            if(isCancelled())
                return null;
            students.addAll(logic.getAllStudents());
            this.updateValue(students);
            return students;
        }
    }

    public static TeacherDashboardModel getInstance(){
     if(instance==null)
        instance =new TeacherDashboardModel();
     return instance;
    }

    public List<ChangeRequest> getAllRequests(int teacherId) {
        try {
            return logic.getRequestsForTeacher(teacherId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public void requestAccepted(ChangeRequest changeRequest) {
        try {
            logic.requestAccepted(changeRequest);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public void requestDeclined(ChangeRequest changeRequest) {
        try {
            logic.requestDeclined(changeRequest);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ScheduleEntity getCurrentLesson(int teacherId) {
        try {
            return logic.getCurrentLessonTeacher(teacherId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns list of absent students on current lesson
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity){
        try {
            return logic.getAbsentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns list of present students on current lesson
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity){
        try {
            return logic.getPresentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    //returns number of absent students from current lesson
    public int getNumberOfAbsent(ScheduleEntity scheduleEntity){
        try {
            return logic.getNumberOfAbsentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }

    }

    //returns number of present students from current lesson
    public int getNumberOfPresent(ScheduleEntity scheduleEntity){
        try {
            return logic.getNumberOfPresentToday(scheduleEntity);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return Integer.parseInt(null);
        }
    }


    public String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) {
        try {
            return logic.getPresenceForStudent(student, timeframe);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return null;
    }

    public String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) {
        try {
            return logic.getMostAbsentDay(student, timeframe);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return "nothing";
    }

    public void loadTableView() {
        try {
            obsStudents.addAll(logic.getAllStudents());
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
    }

    public ObservableList<Student> getObsStudents() {
        return obsStudents;
    }

    public Subject getSubject(int subjectId) {
        try {
            return logic.getSubject(subjectId);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
            return null;
        }
    }

    public int getNumberOfAllStudents(ScheduleEntity currentLesson) {
        try {
            return logic.getNumberOfAllStudents(currentLesson);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 0;
    }

    public int getClassPresentDays(Teacher teacher, Months month) {
        try {
            return  logic.getClassPresentDays(teacher, month);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 0;
    }

    public int getClassAbsentDays(Teacher teacher, Months month) {
        try {
            return logic.getClassAbsentDays(teacher, month);
        } catch (BLLexception blLexception) {
            blLexception.printStackTrace();
        }
        return 1;
    }
}