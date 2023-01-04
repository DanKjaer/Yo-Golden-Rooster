package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private SQLServerDataSource dataSource;

    public DatabaseConnector() throws IOException {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(getServer());
        dataSource.setDatabaseName(getDatabase());
        dataSource.setUser(getUsername());
        dataSource.setPassword(getPassword());
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    private String getServer() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/config.properties");
        properties.load(fileInputStream);
        String server = properties.getProperty("ServerIP");
        return server;
    }

    private String getDatabase() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/config.properties");
        properties.load(fileInputStream);
        String database = properties.getProperty("DB");
        return database;
    }

    private String getUsername() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/config.properties");
        properties.load(fileInputStream);
        String username = properties.getProperty("Username");
        return username;
    }

    /**
     * Gets the password from the config file.
     * @return - returns password as String.
     * @throws IOException
     */
    private String getPassword() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/config.properties");
        properties.load(fileInputStream);
        String password = properties.getProperty("Password");
        return password;
    }

    public static void main(String[] args) throws SQLException, IOException {

        DatabaseConnector databaseConnector = new DatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here

        System.out.println(databaseConnector.getPassword());
    }
}
