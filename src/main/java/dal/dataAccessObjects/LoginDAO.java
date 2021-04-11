package dal.dataAccessObjects;

import be.PasswordObject;
import be.UserType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnector;
import dal.exception.DALexception;

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
    public PasswordObject getPassword(String email, UserType userType) throws DALexception {
        if(userType== UserType.STUDENT)
            return getPasswordFromStudentTable(email);
        if(userType == UserType.TEACHER)
           return getPasswordFromTeacherTable(email);
        if(userType == UserType.ADMIN)
            return getPasswordFromAdminTable(email);
       else
           throw new IllegalArgumentException("This user type doesn't exist");
    }

    private PasswordObject getPasswordFromAdminTable(String email) throws DALexception {
        try(Connection connection = dbConnector.getConnection()) {
            String query = "Select Password, Salt FROM Admins Where Email=?;";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            String pass  =rs.getString("Password");
            String salt = rs.getString("Salt");
            return new PasswordObject(pass, salt);
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from admins table");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from admins table");
        }
    }

    private PasswordObject getPasswordFromTeacherTable(String email) throws DALexception {
        try(Connection connection = dbConnector.getConnection()) {
            String query = "Select Password, Salt FROM Teachers Where Email=?;";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            String pass  =rs.getString("Password");
            String salt = rs.getString("Salt");
            return new PasswordObject(pass, salt);
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from teachers table");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from teachers table");
        }
    }

    private PasswordObject getPasswordFromStudentTable(String email) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String query = "Select Password, Salt FROM Students Where Email=?;";
            PreparedStatement pstat = connection.prepareStatement(query);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();
            rs.next();
            String pass  =rs.getString("Password");
            String salt = rs.getString("Salt");
            return new PasswordObject(pass, salt);
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from students table");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a password from students table");
        }
    }

    public boolean emailExists(String email, UserType userType) throws DALexception {
        if(userType== UserType.STUDENT)
            return studentExists(email);
        else if(userType == UserType.TEACHER)
            return teacherExists(email);
        else if(userType ==UserType.ADMIN)
            return adminExists(email);
        else
            throw new IllegalArgumentException("This user type doesn't exist");
    }

    private boolean adminExists(String email) throws DALexception {
        String query = "SELECT COUNT(Email) as total FROM Admins WHERE Email = ?;";
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
            throw new DALexception("Couldn't check if admin exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't check if admin exists");
        }
    }

    private boolean teacherExists(String email) throws DALexception{
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
            throw new DALexception("Couldn't check if teacher exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't check if teacher exists");
        }
    }


    private boolean studentExists(String email) throws DALexception {
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
            throw new DALexception("Couldn't check if student exists");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't check if student exists");
        }
    }
}
