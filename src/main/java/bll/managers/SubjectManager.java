package bll.managers;

import be.Subject;
import dal.dataAccessObjects.SubjectDAO;

public class SubjectManager {

    private SubjectDAO subjectDAO;

    public SubjectManager() {
        subjectDAO = new SubjectDAO();
    }

    public Subject getSubject(int id) {
        return subjectDAO.getSubject(id);
    }
}
