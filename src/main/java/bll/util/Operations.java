package bll.util;

import be.UserType;
import dal.FacadeDAL;
import dal.IFacadeDAL;

/**
 * @author Kuba
 * @date 3/26/2021 5:19 PM
 */
public class Operations {
    private IFacadeDAL facadeDAL = FacadeDAL.getInstance();
    public boolean verifyPassword(String email, String password,
                                  UserType userType) {
        boolean emailExists = facadeDAL.emailExists(email, userType);
        if(emailExists) {
            String hashedPassword = facadeDAL.getPassword(email, userType);
            return PasswordHasher.verifyPassword(hashedPassword, password);
        }
        else return false;
    }
}
