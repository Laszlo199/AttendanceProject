package dal;

import be.Student;
import be.UserType;

/**
 * @author Kuba
 * @date 3/24/2021 1:36 PM
 */
public interface IFacadeDAL {
    boolean establishedConnection();

    String getPassword(String email, UserType userType);

    boolean emailExists(String email, UserType userType);

    Student getStudent(String email);
}
