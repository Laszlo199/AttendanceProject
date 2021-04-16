package dal.dataAccessObjects;

import be.Student;
import be.WeekDay;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class StudentDAO {

    private DBConnector dbConnector;

    public StudentDAO() {
        dbConnector = new DBConnector();
    }


    public  List<Student> getAll() throws DALexception {
        List<Student> students = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT \n" +
                    "  sum(case when r.isPresent= 0 then 1 else 0 end) as Total_Absent,\n" +
                    " sum(case when r.isPresent= 1 then 1 else 0 end) as Total_Present ,\n" +
                    " sum(case when r.isPresent= 0 and se.WeekDay='monday' then 1 else 0 end) as AbsMonday,\n" +
                    " sum(case when r.isPresent= 0 and se.WeekDay='tuesday' then 1 else 0 end) as AbsTue,\n" +
                    " sum(case when r.isPresent= 0 and se.WeekDay='wednesday' then 1 else 0 end) as AbsWed,\n" +
                    " sum(case when r.isPresent= 0 and se.WeekDay='thursday' then 1 else 0 end) as AbsThursday,\n" +
                    " sum(case when r.isPresent= 0 and se.WeekDay='friday' then 1 else 0 end) as AbsFriday,\n" +
                    "\n" +
                    " s.* from Students as s\n" +
                    "join Records r on r.StudentID = s.ID\n" +
                    "join ScheduleEntity se on se.ID = r.ScheduleEntityID\n" +
                    "GROUP BY s.ID,  s.Name, s.Email, s.CourseID, s.Semester, s.[Password], s.PhotoPath, s.Salt";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int totalAbsent = rs.getInt(1);
                int totalPresence = rs.getInt(2);
                Map<WeekDay, Integer> attDays = new HashMap<>();
                attDays.put(WeekDay.MONDAY, rs.getInt(3));
                attDays.put(WeekDay.TUESDAY, rs.getInt(3));
                attDays.put(WeekDay.WEDNESDAY, rs.getInt(3));
                attDays.put(WeekDay.THURSDAY, rs.getInt(3));
                attDays.put(WeekDay.FRIDAY, rs.getInt(3));
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int courseId = rs.getInt("courseID");
                int semester = rs.getInt("semester");
                String photoPath = rs.getString("photoPath");
                Student student = new Student(id, name, email, photoPath, semester, courseId, (totalPresence/totalAbsent+totalPresence), getMinKey(attDays));
                students.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all students");
        }
        return students;
    }

    private WeekDay getMinKey(Map<WeekDay, Integer> map) {
        WeekDay minKey = null;
        int minValue = Integer.MAX_VALUE;
        for(WeekDay key : map.keySet()) {
            int value = map.get(key);
            if(value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }

    protected class GetAll implements Callable<List<Student>>{
        List<Student> students = new ArrayList<>();
        @Override
        public List<Student> call() throws DALexception {
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
                    //students.add(new Student(id, name, email, photoPath, semester, courseId));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new DALexception("Couldn't get all students");
            }
            return students;
        }
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
            String sql = "SELECT * FROM Students WHERE email=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, email);
             ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                int  id = rs.getInt("id");
                String name = rs.getString("name");
               // String email1 = rs.getString("email");
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

    public int getNumberOfAllStudents() throws DALexception {
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "select count(s.ID) as noStudents" +
                    "from Students as s";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt("noStudents");
        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't get number of students");
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't get number of students");
        }
    }
}
