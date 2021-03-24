package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private SQLServerDataSource dataSource;

    public DBConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_8");
        dataSource.setPassword("potatoe2021");
        dataSource.setDatabaseName("AttendanceProject");
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

    public boolean isDbConnected() {
        try {
            return getConnection() != null && !getConnection().isClosed();
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
