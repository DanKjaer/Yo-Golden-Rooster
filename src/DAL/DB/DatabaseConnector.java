package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
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

    /**
     * Gets the server IP from the config file.
     * @return - returns server IP as String.
     * @throws IOException - Throws an exception, if there is a problem with the config file.
     */
    private String getServer() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("Resources/config.properties");
        properties.load(fileInputStream);
        String server = properties.getProperty("ServerIP");
        return server;
    }

    /**
     * Gets the database name from the config file.
     * @return - returns database name as String.
     * @throws IOException - Throws an exception, if there is a problem with the config file.
     */
    private String getDatabase() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("Resources/config.properties");
        properties.load(fileInputStream);
        String database = properties.getProperty("DB");
        return database;
    }

    /**
     * Gets the username from the config file.
     * @return - returns username as String.
     * @throws IOException - Throws an exception, if there is a problem with the config file.
     */
    private String getUsername() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("Resources/config.properties");
        properties.load(fileInputStream);
        String username = properties.getProperty("Username");
        return username;
    }

    /**
     * Gets the password from the config file.
     * @return - returns password as String.
     * @throws IOException - Throws an exception, if there is a problem with the config file.
     */
    private String getPassword() throws IOException {
        //Create instance of properties
        Properties properties = new Properties();
        //Create instance of fileInputStream
        FileInputStream fileInputStream = new FileInputStream("Resources/config.properties");
        properties.load(fileInputStream);
        String password = properties.getProperty("Password");
        return password;
    }

    /**
     * Test main to check if the connector works.
     * @throws SQLException - Throws an exception, if there is a communication mishap with the database.
     * @throws IOException - Throws an exception, if there is a problem with the config file.
     */
    public static void main(String[] args) throws SQLException, IOException {
        //Create instance of connector
        DatabaseConnector databaseConnector = new DatabaseConnector();

        //Use Try with resources, the parenthesis is the resource, to establish connection and then check if it works.
        try (Connection connection = databaseConnector.getConnection()) {
            System.out.println("Is it open? " + !connection.isClosed());
        }
    }
}
