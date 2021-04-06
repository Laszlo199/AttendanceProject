package bll.managers;

import be.Teacher;
import dal.dataAccessObjects.TeacherDAO;

public class TeacherManager {
    private TeacherDAO teacherDAO;

    public TeacherManager() {
        teacherDAO = new TeacherDAO();
    }

    public Teacher getTeacher(int id) {
        return teacherDAO.getTeacher(id);
    }
}
