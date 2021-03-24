package dal;

import be.ScheduleEntity;
import be.Student;
import bll.OverviewAbsenceCalculator;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.dataAccessObjects.StudentDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public int getNumberOfPresentDays(Student student, OverviewAbsenceCalculator.Timeframe timeframe) {
        return getNumberOfDays(1, student, timeframe);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, OverviewAbsenceCalculator.Timeframe timeframe) {
        return getNumberOfDays(0, student, timeframe);
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

    /**
     *
     * @param isPresent 1 - present days, 0 - absent days
     * @return number of those days
     */
    private int getNumberOfDays(int isPresent, Student student, OverviewAbsenceCalculator.Timeframe timeframe) {
        int number = 0;
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();

        try(Connection connection = dbConnector.getConnection()) {

            switch (timeframe) {
                case MONTH -> {
                    String sql = "SELECT COUNT(r.ID) AS NumberOfDays " +
                            "FROM Records r " +
                            "WHERE r.StudentID=? AND r.isPresent=? AND MONTH(r.[Date])=?";
                    PreparedStatement pstat = connection.prepareStatement(sql);
                    pstat.setInt(1, student.getId());
                    pstat.setInt(2, isPresent);
                    pstat.setInt(3, month);
                    ResultSet rs = pstat.executeQuery();
                    while(rs.next()) {
                        number = rs.getInt("NumberOfDays");
                    }
                }
                case SEMESTER -> {
                    String sql = "SELECT COUNT(r.ID) AS NumberOfDays " +
                            "FROM Records r " +
                            "WHERE r.StudentID=? AND r.isPresent=?";
                    PreparedStatement pstat = connection.prepareStatement(sql);
                    pstat.setInt(1, student.getId());
                    pstat.setInt(2, isPresent);
                    ResultSet rs = pstat.executeQuery();
                    while(rs.next()) {
                        number = rs.getInt("NumberOfDays");
                    }
                }
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
}
