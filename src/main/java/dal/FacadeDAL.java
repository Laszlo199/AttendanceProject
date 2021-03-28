package dal;

import be.Student;
import be.UserType;
import bll.FacadeBLL;
import dal.dataAccessObjects.LoginDAO;
import dal.dataAccessObjects.StudentDAO;

/**
 * @author Kuba
 * @date 3/24/2021 1:36 PM
 */
public class FacadeDAL implements IFacadeDAL{
    private static FacadeDAL facadeDAL;
 private DBConnector dbConnector = new DBConnector();
 private LoginDAO loginDAO = new LoginDAO();
 private StudentDAO studentDAO = new StudentDAO();

    public static IFacadeDAL getInstance(){
        if(facadeDAL==null)
            facadeDAL = new FacadeDAL();
        return facadeDAL;
    }

    private FacadeDAL() {
    }

    @Override
    public boolean establishedConnection() {
        return dbConnector.isDbConnected();
    }

    @Override
    public String getPassword(String email, UserType userType) {
        return loginDAO.getPassword(email,userType);
    }

    @Override
    public boolean emailExists(String email, UserType userType) {
        return loginDAO.emailExists(email, userType);
    }

    @Override
    public Student getStudent(String email) {
        return studentDAO.getStudent(email);
    }
}
