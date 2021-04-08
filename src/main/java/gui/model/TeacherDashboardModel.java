package gui.model;

import be.ChangeRequest;
import bll.FacadeBLL;
import bll.IFacadeBLL;
import bll.exception.BLLexception;

import java.util.List;

public class TeacherDashboardModel {

    private IFacadeBLL logic;

    public TeacherDashboardModel() {
        logic = FacadeBLL.getInstance();
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
}
