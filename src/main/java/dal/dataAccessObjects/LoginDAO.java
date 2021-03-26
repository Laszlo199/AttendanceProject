package dal.dataAccessObjects;

import be.UserType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kuba
 * @date 3/26/2021 5:41 PM
 */
public class LoginDAO {
    private DBConnector dbConnector = new DBConnector();
    /**
     * implement that method later
     * @param email
     * @param userType
     * @return
     */
    public String getPassword(String email, UserType userType) {
        if(userType== UserType.STUDENT)
            return getPasswordFromStudentTable(email);
       else if(userType == UserType.TEACHER)
           return getPasswordFromTeacherTable(email);
       else
           throw new IllegalArgumentException("This user type doesn't exist");
    }

    private String getPasswordFromTeacherTable(String email) {
        try(Connection connection = dbConnector.getConnection()) {
            String query = "Select Password FROM Teachers Where Email=?;";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            return rs.getString("Email");
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private String getPasswordFromStudentTable(String email) {
        try(Connection connection = dbConnector.getConnection()) {
            String query = "Select Password FROM Students Where Email=?;";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            return rs.getString("Email");
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean emailExists(String email, UserType userType) {
        if(userType== UserType.STUDENT)
            return studentExists(email);
        else if(userType == UserType.TEACHER)
            return teacherExists(email);
        else
            throw new IllegalArgumentException("This user type doesn't exist");
    }

    private boolean teacherExists(String email) {
        String query = "SELECT COUNT(Email) as total FROM Teachers WHERE Email = ?;";
        try(Connection connection = dbConnector.getConnection()) {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            int result = rs.getInt("total");
            if(result>0)
                return true;
            else return false;
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    

    private boolean studentExists(String email) {
        String query = "SELECT COUNT(Email) as total FROM Students WHERE Email = ?;";
        try(Connection connection = dbConnector.getConnection()) {
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            int result = rs.getInt("total");
            if(result>0)
                return true;
            else return false;
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
