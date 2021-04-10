package dal;

import be.Months;
import be.ScheduleEntity;
import be.Student;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.dataAccessObjects.StudentDAO;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
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
    public int getNumberOfPresentDays(Student student, Months month) throws DALexception {
        return getNumberOfDays(1, student, month);
    }

    @Override
    public int getNumberOfAbsentDays(Student student, Months month) throws DALexception {
        return getNumberOfDays(0, student, month);
    }

    @Override
    public int getTotalNumberOfPresentDays(Student student) throws DALexception {
        return getTotalNumberOfDays(1, student);
    }

    @Override
    public int getTotalNumberOfAbsentDays(Student student) throws DALexception {
        return getTotalNumberOfDays(0, student);
    }

    @Override
    public List<Student> getAllStudents() throws DALexception {
        return studentDAO.getAll();
    }

    @Override
    public List<Student> getAbsentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return getStudentsToday(scheduleEntity, 0);
    }

    @Override
    public List<Student> getPresentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return getStudentsToday(scheduleEntity, 1);
    }

    @Override
    public int getNumberOfPresentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return getNumberOfToday(scheduleEntity, 1);
    }

    @Override
    public int getNumberOfAbsentToday(ScheduleEntity scheduleEntity) throws DALexception {
        return getNumberOfToday(scheduleEntity, 0);
    }
    public int getAbsForDay(Enum dayOfWeek) throws DALexception {
        return getAllDays(false, dayOfWeek);
    }
    public int getPresentForDay(Enum dayOfWeek) throws DALexception {
        return getAllDays(true, dayOfWeek);
    }

    @Override
    public boolean isStudentPresent(int id) {

        try(Connection connection = dbConnector.getConnection()) {


        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isDataStudentPresent(int id) throws DALexception {
        Calendar startOfToday = Calendar.getInstance();
        Calendar endOfToday = Calendar.getInstance();
        endOfToday.setTime(startOfToday.getTime());

        startOfToday.set(Calendar.HOUR_OF_DAY, 0);
        startOfToday.set(Calendar.MINUTE, 0);
        startOfToday.set(Calendar.SECOND, 0);
        startOfToday.set(Calendar.MILLISECOND, 0);

        endOfToday.set(Calendar.HOUR_OF_DAY, 23);
        endOfToday.set(Calendar.MINUTE, 59);
        endOfToday.set(Calendar.SECOND, 59);
        endOfToday.set(Calendar.MILLISECOND, 999);
        java.util.Date start = startOfToday.getTime();
        java.util.Date end = endOfToday.getTime();
        java.sql.Date startDate = new java.sql.Date(start.getTime());
        java.sql.Date endDate = new java.sql.Date(end.getTime());

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT COUNT(r.ID) AS NumberOfDays" +
                    " FROM Records r WHERE " +
                    "[Date]>=? AND [Date]<=? AND id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setDate(1, startDate);
            pstat.setDate(2, endDate);
            pstat.setInt(3, id);
            ResultSet rs  = pstat.executeQuery();

            if(rs.getInt("NumberOfDays")>0)
                return true;
            else
                return false;

        } catch (SQLServerException throwables) {
            throw new DALexception("Couldn't check if is there is data about students prence",
                    throwables);
        } catch (SQLException throwables) {
            throw new DALexception("Couldn't check if is there is data about students prence",
                    throwables);

        }
    }

    /**
     *
     * @param isPresent 1 - present days, 0 - absent days
     * @return number of those days
     */
    private int getNumberOfDays(int isPresent, Student student, Months month) throws DALexception {
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
            throw new DALexception("Couldn't get number of days per month");
        }
        return number;
    }

    private int getTotalNumberOfDays(int isPresent, Student student) throws DALexception {
        int number = 0;

        try(Connection connection = dbConnector.getConnection()) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get number of days in total");
        }
        return number;
    }


    /**
     *
     * @param scheduleEntity lesson at the moment
     * @param isPresent 1 - present, 0 - absent
     * @return list of all students that are absent / present during current lesson
     */
    private List<Student> getStudentsToday(ScheduleEntity scheduleEntity, int isPresent) throws DALexception {
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
            throw new DALexception("Couldn't get a list of all students that are absent / present during current lesson");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a list of all students that are absent / present during current lesson");
        }
        return students;
    }

    /**
     * @param scheduleEntity lesson now
     * @param isPresent 1 - present, 0 - absent
     * @return number of present / absent students during current lesson
     */
    private int getNumberOfToday(ScheduleEntity scheduleEntity, int isPresent) throws DALexception {
        int number = 0;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT COUNT(s.id) AS NumberOfStudents " +
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
            throw new DALexception("Couldn't get number of present / absent students during current lesson");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get number of present / absent students during current lesson");
        }
        return number;
    }

    private int getAllDays(Boolean isPresent,Enum dayOfWeek) throws DALexception {
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
            throw new DALexception("Couldn't get number of days of a weekday");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get number of days of a weekday");
        }
        return number;
    }
}
