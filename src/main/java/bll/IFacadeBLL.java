package bll;

import be.Student;
import be.UserType;

/**
 * @author Kuba
 * @date 3/24/2021 1:31 PM
 */
public interface IFacadeBLL {
    boolean establishedConnection();

    String getHashedPassword(String password);

    boolean verifyPassword(String email, String password, UserType userType);

    Student getStudent(String email);

    String getRandomQuote();
}
