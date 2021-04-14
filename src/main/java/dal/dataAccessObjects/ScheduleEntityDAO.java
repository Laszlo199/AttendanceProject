package dal.dataAccessObjects;

import be.ScheduleEntity;
import be.WeekDay;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleEntityDAO {

    private DBConnector dbConnector;

    public ScheduleEntityDAO() {
        dbConnector = new DBConnector();
    }

    public List<ScheduleEntity> getAll() throws DALexception {
        List<ScheduleEntity> entities = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM ScheduleEntity";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String weekday = rs.getString("weekday");
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                int subjectId = rs.getInt("subjectId");
                switch (weekday) {
                    case "monday":
                        entities.add(new ScheduleEntity(id, subjectId, WeekDay.MONDAY, startTime, endTime));
                    case "tuesday":
                        entities.add(new ScheduleEntity(id, subjectId, WeekDay.TUESDAY, startTime, endTime));
                    case "wednesday":
                        entities.add(new ScheduleEntity(id, subjectId, WeekDay.WEDNESDAY, startTime, endTime));
                    case "thursday":
                        entities.add(new ScheduleEntity(id, subjectId, WeekDay.THURSDAY, startTime, endTime));
                    case "friday":
                        entities.add(new ScheduleEntity(id, subjectId, WeekDay.FRIDAY, startTime, endTime));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all schedule entities");
        }
        return entities;
    }

    public void create(ScheduleEntity entity) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO ScheduleEntity(weekday, startTime, endTime, subjectId) VALUES(?,?,?,?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, String.valueOf(entity.getWeekDay()).toLowerCase());
            pstat.setTime(2, entity.getStartTime());
            pstat.setTime(3, entity.getEndTime());
            pstat.setInt(4, entity.getSubjectId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't create a schedule entity");
        }
    }

    public void delete(ScheduleEntity entity) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM ScheduleEntity WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, entity.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete a schedule entity");
        }
    }

    public void update(ScheduleEntity oldEntity, ScheduleEntity newEntity) throws DALexception{
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE ScheduleEntity SET weekday = ?, startTime = ?, endTime =?, subjectId=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, String.valueOf(newEntity.getWeekDay()));
            pstat.setTime(2, newEntity.getStartTime());
            pstat.setTime(3, newEntity.getEndTime());
            pstat.setInt(4, newEntity.getSubjectId());
            pstat.setInt(5, oldEntity.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update a schedule entity");
        }
    }

    public ScheduleEntity getScheduleEntity(int id) throws DALexception{
        ScheduleEntity entity = null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT *FROM ScheduleEntity WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String weekday = rs.getString("weekday");
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                int subjectId = rs.getInt("subjectId");
                switch (weekday) {
                    case "monday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.MONDAY, startTime, endTime);
                    case "tuesday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.TUESDAY, startTime, endTime);
                    case "wednesday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.WEDNESDAY, startTime, endTime);
                    case "thursday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.THURSDAY, startTime, endTime);
                    case "friday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.FRIDAY, startTime, endTime);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a schedule entity");
        }
        return entity;
    }

    public ScheduleEntity getCurrentLessonStudent(int courseId) throws DALexception {
        String sql = "SELECT se.* " +
                "FROM ScheduleEntity se " +
                "JOIN Subjects s ON se.SubjectID = s.ID " +
                "WHERE s.courseId = ? AND se.WeekDay = ? AND (? BETWEEN se.StartTime AND se.EndTime)";
        return getCurrentEntity(courseId, sql);
    }

    public ScheduleEntity getCurrentLessonTeacher(int teacherId) throws DALexception {
        String sql = "SELECT se.* " +
                "FROM ScheduleEntity se " +
                "JOIN Subjects s ON se.SubjectID = s.ID " +
                "WHERE s.teacherId = ? AND se.WeekDay = ? AND (? BETWEEN se.StartTime AND se.EndTime)";
        return getCurrentEntity(teacherId, sql);
    }

    /**
     *
     * @param input courseId of the student that is currently logged in / teacherId that is currently logged in
     * @return null if there isn't any current lesson, ScheduleEntity if there is a lesson at the moment
     */
    private ScheduleEntity getCurrentEntity(int input, String sql) throws DALexception {
        ScheduleEntity currentLesson = null;
        LocalDate currentDate = LocalDate.now();
        String day = currentDate.getDayOfWeek().toString();
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        try(Connection connection = dbConnector.getConnection()) {

            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, input);
            pstat.setString(2, day);
            pstat.setString(3, currentTime);
            ResultSet rs = pstat.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String weekday = rs.getString("weekday").toLowerCase();
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                int subjectId = rs.getInt("subjectId");
                switch (weekday) {
                    case "monday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.MONDAY, startTime, endTime); break;
                    case "tuesday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.TUESDAY, startTime, endTime); break;
                    case "wednesday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.WEDNESDAY, startTime, endTime); break;
                    case "thursday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.THURSDAY, startTime, endTime); break;
                    case "friday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.FRIDAY, startTime, endTime); break;
                }
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a current lesson");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a current lesson");
        }

        return currentLesson;
    }
}
