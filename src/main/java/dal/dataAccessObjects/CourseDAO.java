package dal.dataAccessObjects;


import be.Course;
import dal.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private DBConnector dbConnector;

    public CourseDAO() {
        dbConnector = new DBConnector();
    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Courses";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                courses.add(new Course(id, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;
    }

    public void create(Course course) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Courses([name]) VALUES (?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, course.getName());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Course oldCourse, Course newCourse) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Courses SET name=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, newCourse.getName());
            pstat.setInt(2, oldCourse.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Course course) {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Courses WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, course.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Course getCourse(int id) {
        Course course = null;
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Course WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String name = rs.getString("name");
                course = new Course(id, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return course;
    }
}
