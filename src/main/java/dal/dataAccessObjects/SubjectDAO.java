package dal.dataAccessObjects;


import be.Subject;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private DBConnector dbConnector;

    public SubjectDAO() {
        dbConnector = new DBConnector();
    }

    public List<Subject> getAll() throws DALexception {
        List<Subject> subjects = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Subjects";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacherId");
                int courseId = rs.getInt("courseId");
                subjects.add(new Subject(id, name, teacherId, courseId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all subjects");
        }
        return subjects;
    }

    public void create(Subject subject) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Subjects([name], teacherId, courseId) VALUES (?, ?, ?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, subject.getName());
            pstat.setInt(2, subject.getTeacherId());
            pstat.setInt(3, subject.getCourseId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a subject");
        }
    }

    public void update(Subject oldSubject, Subject newSubject) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Subjects SET name=?, teacherId=? courseId=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, newSubject.getName());
            pstat.setInt(2, newSubject.getTeacherId());
            pstat.setInt(3, newSubject.getCourseId());
            pstat.setInt(4, oldSubject.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update a subject");
        }
    }

    public void delete(Subject subject) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Subjects WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, subject.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete a subject");
        }
    }

    public Subject getSubject(int id) throws DALexception{
        Subject subject = null;
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Subjects WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacherId");
                int courseId = rs.getInt("courseId");
                subject = new Subject(id, name, teacherId, courseId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a subject");
        }
        return subject;
    }
}
