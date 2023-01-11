package DAL.DB;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private final SQLServerDataSource dataSource;

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
     * Method to load the config file and is called in the methods that load the different information.
     * @return - config file as a Property type
     * @throws IOException
     */
    private Properties getConfigFile() throws IOException {
        //Create instance of properties
        Properties properties = new Properties();

        //Create instance of fileInputStream with the resource
        FileInputStream fileInputStream = new FileInputStream("Resources/config.properties");

        //Load resource into properties variable
        properties.load(fileInputStream);
        return properties;
    }

    /**
     * Gets the server IP from the config file.
     * @return - returns server IP as String.
     * @throws IOException - throws an exception, if there is a problem with the config file.
     */
    private String getServer() throws IOException {
        //Take the resource we want and return it
        String server = getConfigFile().getProperty("ServerIP");
        return server;
    }

    /**
     * Gets the database name from the config file.
     * @return - returns database name as String.
     * @throws IOException - throws an exception, if there is a problem with the config file.
     */
    private String getDatabase() throws IOException {
        //Take the resource we want and return it
        String database = getConfigFile().getProperty("DB");
        return database;
    }

    /**
     * Gets the username from the config file.
     * @return - returns username as String.
     * @throws IOException - throws an exception, if there is a problem with the config file.
     */
    private String getUsername() throws IOException {
        //Take the resource we want and return it
        String username = getConfigFile().getProperty("Username");
        return username;
    }

    /**
     * Gets the password from the config file.
     * @return - returns password as String.
     * @throws IOException - throws an exception, if there is a problem with the config file.
     */
    private String getPassword() throws IOException {
        //Take the resource we want and return it
        String password = getConfigFile().getProperty("Password");
        return password;
    }

    /**
     * Test main to check if the connector works.
     * @throws SQLException - throws an exception, if there is a communication mishap with the database.
     * @throws IOException - throws an exception, if there is a problem with the config file.
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
