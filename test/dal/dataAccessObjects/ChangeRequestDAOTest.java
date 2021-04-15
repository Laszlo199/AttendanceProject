package dal.dataAccessObjects;

import dal.exception.DALexception;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kuba
 * @date 4/15/2021 8:41 PM
 */
class ChangeRequestDAOTest {

    @Test
    void getRequestsForTeacher() {
        ChangeRequestDAO changeRequestDAO = new ChangeRequestDAO();
        try {
            System.out.println(changeRequestDAO.getRequestsForTeacher(1).toString());
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
        }
    }
}