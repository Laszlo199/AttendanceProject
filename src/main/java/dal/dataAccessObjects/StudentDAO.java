package dal.dataAccessObjects;

import be.Student;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private DBConnector dbConnector;

    public StudentDAO() {
        dbConnector = new DBConnector();
    }

    public List<Student> getAll() throws DALexception {
        List<Student> students = new ArrayList<>();

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Students";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int courseId = rs.getInt("courseID");
                int semester = rs.getInt("semester");
                String photoPath = rs.getString("photoPath");
                students.add(new Student(id, name, email, photoPath, semester, courseId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all students");
        }
        return students;
    }

    public void create(Student student) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Students([name], email, courseId, semester, photoPath) VALUES(?,?,?,?,?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, student.getName());
            pstat.setString(2, student.getEmail());
            pstat.setInt(3, student.getCourseID());
            pstat.setInt(4, student.getSemester());
            pstat.setString(5, student.getPhotoPath());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a student");
        }
    }

    public void delete(Student student) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Students WHERE id = ?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, student.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete a student");
        }
    }

    /**
     *
     * @param oldStudent student to be updated
     * @param newStudent values to use in an update
     */
    public void update(Student oldStudent, Student newStudent) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Students SET [name]=?, email=?, courseId=?, semester=?, photoPath=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, newStudent.getName());
            pstat.setString(2, newStudent.getEmail());
            pstat.setInt(3, newStudent.getCourseID());
            pstat.setInt(4, newStudent.getSemester());
            pstat.setString(5, newStudent.getPhotoPath());
            pstat.setInt(6, oldStudent.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update a student");
        }
    }

    public Student getStudent(String email) throws DALexception{
        Student student =null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT FROM Students WHERE email=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, email);
             ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                int  id = rs.getInt("id");
                String name = rs.getString("name");
                String email1 = rs.getString("email");
                int courseId = rs.getInt("courseID");
                int semester = rs.getInt("semester");
                String photoPath = rs.getString("photoPath");
                student = new Student(id, name, email1, photoPath, semester, courseId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a student");
        }
        return student;
    }

    public Student getStudent(int id) throws DALexception{
        Student student = null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Students WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int courseId = rs.getInt("courseID");
                int semester = rs.getInt("semester");
                String photoPath = rs.getString("photoPath");
                student = new Student(id, name, email, photoPath, semester, courseId);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a student");
        }
        return student;
    }
}
