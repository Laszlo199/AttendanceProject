package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DBConnector {
    private SQLServerDataSource dataSource;

    public DBConnector()
    {
        //kamilas DB

        /*
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_10");
        dataSource.setPassword("database000");
        dataSource.setDatabaseName("ItunesGROUP2");

         */


        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_8");
        dataSource.setPassword("potatoe2021");
        dataSource.setDatabaseName("MyItuesKuba");


    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }
}
