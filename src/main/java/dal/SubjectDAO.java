package dal;


import be.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private DBConnector dbConnector;

    public SubjectDAO() {
        dbConnector = new DBConnector();
    }

    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Subjects";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacherId");
                subjects.add(new Subject(id, name, teacherId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjects;
    }

    public void create(Subject subject) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Subjects([name], teacherId) VALUES (?, ?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, subject.getName());
            pstat.setInt(2, subject.getTeacherId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Subject oldSubject, Subject newSubject) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Subjects SET name=?, teacherId=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, newSubject.getName());
            pstat.setInt(2, newSubject.getTeacherId());
            pstat.setInt(3, oldSubject.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Subject subject) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Subjects WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, subject.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Subject getSubject(int id) {
        Subject subject = null;
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Subject WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                int teacherId = rs.getInt("teacherId");
                subject = new Subject(id, name, teacherId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subject;
    }
}
