package bll.managers;

import be.ChangeRequest;
import dal.dataAccessObjects.ChangeRequestDAO;

public class ChangeRequestManager {

    private ChangeRequestDAO changeRequestDAO;

    public ChangeRequestManager() {
        changeRequestDAO = new ChangeRequestDAO();
    }
    public void create(ChangeRequest newRequest) {
        changeRequestDAO.create(newRequest);
    }
}
