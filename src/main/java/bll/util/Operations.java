package bll.util;

import be.UserType;
import bll.exception.BLLexception;
import dal.FacadeDAL;
import dal.IFacadeDAL;
import dal.exception.DALexception;

/**
 * @author Kuba
 * @date 3/26/2021 5:19 PM
 */
public class Operations {
    private IFacadeDAL facadeDAL = FacadeDAL.getInstance();
    public boolean verifyPassword(String email, String password, UserType userType) throws BLLexception {
        boolean emailExists = false;
        try {
            emailExists = facadeDAL.emailExists(email, userType);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't check if email exists");
        }
        if(emailExists) {
            String hashedPassword = null;
            try {
                hashedPassword = facadeDAL.getPassword(email, userType);
            } catch (DALexception daLexception) {
                daLexception.printStackTrace();
                throw new BLLexception("Couldn't get a password");
            }
            return PasswordHasher.verifyPassword(hashedPassword, password);
        }
        else return false;
    }
}
