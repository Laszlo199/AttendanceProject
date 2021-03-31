package dal;

import be.Months;
import be.ScheduleEntity;
import be.Student;
import be.WeekDay;
import bll.OverviewAbsenceCalculator;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.dataAccessObjects.StudentDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author kamila
 */
public class AbsenceData implements IAbsenceData {

    DBConnector dbConnector;
    StudentDAO studentDAO;

    public AbsenceData() {
        dbConnector = new DBConnector();
        studentDAO = new StudentDAO();
    }

    @Override
    public int getNumberOfPresentDays(Student student, Months month) {
        return getNumberOfDays(1, student, month);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) {
        return getNumberOfDays(0, student, month);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    @Override
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity) {
        return getStudentsToday(scheduleEntity, 0);
    }

    @Override
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity) {
        return getStudentsToday(scheduleEntity, 1);
    }

    @Override
    public int getNumberOfPresentToday(ScheduleEntity scheduleEntity) {
        return getNumberOfToday(scheduleEntity, 1);
    }

    @Override
    public int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) {
        return getNumberOfToday(scheduleEntity, 0);
    }
    public int getAbsForDay(Enum dayOfWeek){
        return getAllDays(false, dayOfWeek);
    }
    public int getPresentForDay(Enum dayOfWeek){
        return getAllDays(true, dayOfWeek);
    }

    /**
     *
     * @param isPresent 1 - present days, 0 - absent days
     * @return number of those days
     */
    private int getNumberOfDays(int isPresent, Student student, Months month) {
        int number = 0;

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT COUNT(r.ID) AS NumberOfDays " +
                    "FROM Records r " +
                    "WHERE r.StudentID=? AND r.isPresent=? AND MONTH(r.[Date])=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, student.getId());
            pstat.setInt(2, isPresent);
            pstat.setInt(3, month.getValue());
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                number = rs.getInt("NumberOfDays");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return number;
    }

    /**
     *
     * @param scheduleEntity lesson at the moment
     * @param isPresent 1 - present, 0 - absent
     * @return list of all students that are absent / present during current lesson
     */
    private List<Student> getStudentsToday(ScheduleEntity scheduleEntity, int isPresent) {
        List<Student> students = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT s.* " +
                    "FROM Students s " +
                    "JOIN Records r ON s.ID = r.StudentID " +
                    "JOIN ScheduleEntity se ON se.ID = r.ScheduleEntityID " +
                    "WHERE r.isPresent=? AND r.ScheduleEntityID=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, isPresent);
            pstat.setInt(2, scheduleEntity.getId());
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int courseId = rs.getInt("courseID");
                int semester = rs.getInt("semester");
                String photoPath = rs.getString("photoPath");
                students.add(new Student(id, name, email, photoPath, semester, courseId));
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    /**
     * @param scheduleEntity lesson now
     * @param isPresent 1 - present, 0 - absent
     * @return number of present / absent students during current lesson
     */
    private int getNumberOfToday(ScheduleEntity scheduleEntity, int isPresent) {
        int number = 0;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT COUNT(s.id) AS NumberOfStudents" +
                    "FROM Students s " +
                    "JOIN Records r ON s.ID = r.StudentID " +
                    "JOIN ScheduleEntity se ON se.ID = r.ScheduleEntityID " +
                    "WHERE r.isPresent=? AND r.ScheduleEntityID=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, isPresent);
            pstat.setInt(2, scheduleEntity.getId());
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                number = rs.getInt("NumberOfStudents");
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return number;
    }

    private int getAllDays(Boolean isPresent,Enum dayOfWeek) {
        int number = 0;
        String temp = String.valueOf(dayOfWeek).toLowerCase();
        temp = temp.substring(0,1).toUpperCase() + String.valueOf(dayOfWeek).toLowerCase().substring(1);


        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT COUNT(r.ID) AS AbsOrPresentDays " +
                    "FROM Records r " +
                    "JOIN ScheduleEntity se ON se.ID = r.ScheduleEntityID " +
                    "WHERE r.isPresent=? AND se.WeekDay=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setBoolean(1, isPresent);
            pstat.setString(2, temp);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                number = rs.getInt("AbsOrPresentDays");
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return number;
    }
}
