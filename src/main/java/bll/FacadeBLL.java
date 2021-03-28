package bll;

import be.Student;
import be.UserType;
import bll.util.Operations;
import bll.util.PasswordHasher;
import dal.FacadeDAL;
import dal.IFacadeDAL;

/**
 * @author Kuba
 * @date 3/24/2021 1:31 PM
 */
public class FacadeBLL implements IFacadeBLL{
    private static FacadeBLL facadeBLL;
    private IFacadeDAL facadeDAL = FacadeDAL.getInstance();
    private Operations operations = new Operations();

    public static IFacadeBLL getInstance(){
        if(facadeBLL==null)
            facadeBLL = new FacadeBLL();
        return facadeBLL;
    }

    private FacadeBLL() {
    }

    @Override
    public boolean establishedConnection() {
        return facadeDAL.establishedConnection();
    }

    @Override
    public String getHashedPassword(String password) {
        return PasswordHasher.getHashedPassword(password);
    }

    @Override
    public boolean verifyPassword(String email, String password, UserType userType) {
        return operations.verifyPassword(email, password, userType);
    }

    @Override
    public Student getStudent(String email) {
        return facadeDAL.getStudent(email);
    }
}
