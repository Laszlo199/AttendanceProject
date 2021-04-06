package dal.dataAccessObjects;

import be.ScheduleEntity;
import be.WeekDay;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DBConnector;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public List<ScheduleEntity> getAll() {
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
        }
        return entities;
    }

    public void create(ScheduleEntity entity) {
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
        }
    }

    public void delete(ScheduleEntity entity) {
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM ScheduleEntity WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, entity.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(ScheduleEntity oldEntity, ScheduleEntity newEntity) {
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
        }
    }

    public ScheduleEntity getScheduleEntity(int id) {
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
        }
        return entity;
    }

    /**
     *
     * @param courseId course of the student that is currently logged in
     * @return null if there isnt any current lesson, ScheduleEntity if there is a lesson at the moment
     */
    public ScheduleEntity getCurrentEntity(int courseId) {
        ScheduleEntity currentLesson = null;
        LocalDate currentDate = LocalDate.now();
        String day = currentDate.getDayOfWeek().toString();
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT se.* " +
                    "FROM ScheduleEntity se " +
                    "JOIN Subjects s ON se.SubjectID = s.ID " +
                    "WHERE s.courseId = ? AND se.WeekDay = ? AND (? BETWEEN se.StartTime AND se.EndTime)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, courseId);
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
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.MONDAY, startTime, endTime);
                    case "tuesday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.TUESDAY, startTime, endTime);
                    case "wednesday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.WEDNESDAY, startTime, endTime);
                    case "thursday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.THURSDAY, startTime, endTime);
                    case "friday":
                        currentLesson = new ScheduleEntity(id, subjectId, WeekDay.FRIDAY, startTime, endTime);
                }
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return currentLesson;
    }
}
