package dal.dataAccessObjects;

import be.Record;
import dal.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordDAO {

    private DBConnector dbConnector;

    public RecordDAO() {
        dbConnector = new DBConnector();
    }

    public List<Record> getAll() {
        List<Record> records = new ArrayList<>();

        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Records";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("studentID");
                int scheduleEntityId = rs.getInt("scheduleEntityId");
                boolean isPresent = rs.getBoolean("isPresent");
                Date date = rs.getDate("date");
                records.add(new Record(id, studentId, date, scheduleEntityId, isPresent));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return records;
    }

    public void create(Record record) {
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO Records(studentId, scheduleEntityId, isPresent, [date]) VALUES (?,?,?,?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, record.getStudentId());
            pstat.setInt(2, record.getScheduleEntityId());
            pstat.setBoolean(3, record.isPresent());
            pstat.setDate(4, (java.sql.Date) record.getDate());
            pstat.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Record record) {
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM Records WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, record.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Record oldRecord, Record newRecord) {
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE Records SET studentId=?, scheduleEntityId=?, isPresent=?, [date]=? WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, newRecord.getStudentId());
            pstat.setInt(2, newRecord.getScheduleEntityId());
            pstat.setBoolean(3, newRecord.isPresent());
            pstat.setDate(4, (java.sql.Date) newRecord.getDate());
            pstat.setInt(5, oldRecord.getId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Record getRecord(int id) {
        Record record = null;
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM Records WHERE id=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, id);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                int studentId = rs.getInt("studentID");
                int scheduleEntityId = rs.getInt("scheduleEntityId");
                boolean isPresent = rs.getBoolean("isPresent");
                Date date = rs.getDate("date");
                record = new Record(id, studentId, date, scheduleEntityId, isPresent);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return record;
    }
}
