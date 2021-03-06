package bll.util;

import be.PasswordObject;
import be.Student;
import be.UserType;
import bll.exception.BLLexception;
import dal.FacadeDAL;
import dal.IFacadeDAL;
import dal.exception.DALexception;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Email exists: " + emailExists);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't check if email exists");
        }
        if(emailExists) {
            PasswordObject passwordObject = null;
            try {
                passwordObject = facadeDAL.getPassword(email, userType);
                System.out.println("we got into email exists");

                System.out.println("password verification: " +PasswordHasher.verifyPassword(password, passwordObject.getHashedPassword(),
                        passwordObject.getSalt()));

                return PasswordHasher.verifyPassword(password, passwordObject.getHashedPassword(),
                        passwordObject.getSalt());
            } catch (DALexception daLexception) {
                daLexception.printStackTrace();
                throw new BLLexception("Couldn't get a password");
            }

        }
        else return false;
    }

    public List<Student> getStudentsOnSem(int sem, List<Student> obsStudents) {
        List<Student> students = new ArrayList<>();
        for (Student s: obsStudents)
            if(s.getSemester()==sem)
                students.add(s);
           return students;
    }
}
