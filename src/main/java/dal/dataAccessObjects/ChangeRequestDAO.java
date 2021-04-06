package dal.dataAccessObjects;

import be.ChangeRequest;
import be.StatusType;
import dal.DBConnector;
import dal.exception.DALexception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeRequestDAO {

    private DBConnector dbConnector;

    public ChangeRequestDAO() {
        dbConnector = new DBConnector();
    }

    public List<ChangeRequest> getAll() throws DALexception {
        List<ChangeRequest> requests = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM ChangeRequests";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                int recordId = rs.getInt("recordId");
                String status = rs.getString("status");
                switch (status) {
                    case "pending":
                        requests.add(new ChangeRequest(recordId, StatusType.PENDING));
                    case "accepted":
                        requests.add(new ChangeRequest(recordId, StatusType.ACCEPTED));
                    case "declined":
                        requests.add(new ChangeRequest(recordId, StatusType.DECLINED));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DALexception("Couldn't get all requests");
        }
        return requests;
    }

    public void create(ChangeRequest changeRequest) throws DALexception {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "INSERT INTO ChangeRequests(RecordId, Status) VALUES (?,?)";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, changeRequest.getRecordId());
            pstat.setString(2, String.valueOf(changeRequest.getStatus()).toLowerCase());
            pstat.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DALexception("Couldn't create a new request");
        }
    }

    public void update(ChangeRequest oldRequest, ChangeRequest newRequest) throws DALexception {
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "UPDATE ChangeRequests SET status=? WHERE recordId=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setString(1, String.valueOf(newRequest.getStatus()).toLowerCase());
            pstat.setInt(2, oldRequest.getRecordId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't update a request");
        }
    }

    public void delete(ChangeRequest changeRequest) throws DALexception{
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "DELETE FROM ChangeRequests WHERE recordId=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, changeRequest.getRecordId());
            pstat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't delete a request");
        }
    }

    public ChangeRequest getChangeRequest(int recordId) throws DALexception {
        ChangeRequest request = null;
        try (Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT * FROM ChangeRequests WHERE recordId=?";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, recordId);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                String status = rs.getString("status");
                switch (status) {
                    case "pending":
                        request = new ChangeRequest(recordId, StatusType.PENDING);
                    case "accepted":
                        request = new ChangeRequest(recordId, StatusType.ACCEPTED);
                    case "declined":
                        request = new ChangeRequest(recordId, StatusType.DECLINED);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get a request");
        }
        return request;
    }

    public List<ChangeRequest> getRequestsForTeacher(int teacherId) throws DALexception {
        List<ChangeRequest> requests = new ArrayList<>();
        try(Connection connection = dbConnector.getConnection()) {
            String sql = "SELECT c.RecordID, c.[Status] " +
                    "FROM ChangeRequests c " +
                    "JOIN Records r ON r.id = c.RecordID " +
                    "JOIN ScheduleEntity se ON se.ID = r.ScheduleEntityID " +
                    "JOIN Subjects s ON s.ID = se.SubjectID " +
                    "WHERE s.TeacherID = ? AND c.[Status] = 'pending' ";
            PreparedStatement pstat = connection.prepareStatement(sql);
            pstat.setInt(1, teacherId);
            ResultSet rs = pstat.executeQuery();

            while(rs.next()) {
                int recordId = rs.getInt("recordId");
                requests.add(new ChangeRequest(recordId, StatusType.PENDING));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DALexception("Couldn't get all requests for a teacher");
        }
        return requests;
    }
}
