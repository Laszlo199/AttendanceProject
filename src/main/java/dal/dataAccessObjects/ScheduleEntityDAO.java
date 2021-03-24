package dal.dataAccessObjects;

import be.ScheduleEntity;
import be.WeekDay;
import dal.DBConnector;

import java.sql.*;
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
                    case "fiday":
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
                    case "fiday":
                        entity = new ScheduleEntity(id, subjectId, WeekDay.FRIDAY, startTime, endTime);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return entity;
    }
}
