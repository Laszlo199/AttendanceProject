package dal.dataAccessObjects;

import be.Teacher;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    private DBConnector dbConnector;

    public TeacherDAO() {
        dbConnector = new DBConnector();
    }

    public List<Teacher> getAll() throws DALexception {
        List<Teacher> teachers = new ArrayList<>();

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Teachers";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                String photoPath = rs.getString("photoPath");
                teachers.add(new Teacher(id, name, email, photoPath, department));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all teachers");
        }
        return teachers;
    }

    public void create(Teacher teacher) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Teachers([name], email, department, photoPath) VALUES(?,?,?,?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, teacher.getName());
            pstat.setString(2, teacher.getEmail());
            pstat.setString(3, teacher.getDepartment());
            pstat.setString(4, teacher.getPhotoPath());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a teacher");
        }
    }

    public void delete(Teacher teacher) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Teachers WHERE id = ?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, teacher.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete a teacher");
        }
    }

    /**
     *
     * @param oldTeacher teacher to be updated
     * @param newTeacher teacher to use in an update
     */
    public void update(Teacher oldTeacher, Teacher newTeacher) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Teachers SET [name]=?, email=?, department=?, photoPath=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, newTeacher.getName());
            pstat.setString(2, newTeacher.getEmail());
            pstat.setString(3, newTeacher.getDepartment());
            pstat.setString(4, newTeacher.getPhotoPath());
            pstat.setInt(4, oldTeacher.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update a teacher");
        }
    }

    public Teacher getTeacher(String email) throws DALexception{
        Teacher teacher = null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Teachers WHERE email=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, email);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String department = rs.getString("department");
                String photoPath = rs.getString("photoPath");
                teacher = new Teacher(id, name, email, photoPath, department);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a teacher");
        }
        return teacher;
    }

    public Teacher getTeacher(int id) throws DALexception{
        Teacher teacher = null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Teachers WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                String photoPath = rs.getString("photoPath");
                teacher = new Teacher(id, name, email, photoPath, department);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a teacher");
        }
        return teacher;
    }
}
