package connector;


import model.Recommendations;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

import java.sql.*;

public class Connector extends BaseClientService {

    private static final String DATABASE_NAME = "jdbc:mysql://localhost/recomending_system";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "ZAQ!2wsx";

    private Connection connection = null;

    public void runConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_NAME, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
        System.out.println("Connected to the MySQL server successfully.");
    }

    public void closeConnection() throws SQLException {
        connection.close();
        //System.out.println("Connection was closed");
    }

    public Connection getConnection() {
        return connection;
    }
}
