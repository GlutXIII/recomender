package connector;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import recommendingSystem.BaseClientService;

import java.sql.*;

public class Connector extends BaseClientService {

    private static final String DATABASE_NAME = "jdbc:mysql://localhost/recomending_system";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "myRootPassword";
    public Connection connection = null;

    private void runConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DATABASE_NAME, DATABASE_USERNAME, DATABASE_PASSWORD);
        //System.out.println("Connected to the MySQL server successfully.");
    }

    private void closeConnection() throws SQLException {
        connection.close();
        //System.out.println("Connection was closed");
    }

    public boolean checkCredentials(String login, String password) {
        try {
            runConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password );
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert("Failed", "Recheck your credentials");
                return true;
            } else {
                showAlert("Success", "Welcome to recommending system");
               return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean registerCheck(String login,String password,String gender,String occupation,String age){
        try{
            runConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(login,password) VALUES(?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatementProfile = connection.prepareStatement("INSERT INTO user_profile(gender,age,occupation) VALUES(?,?,?)");
            preparedStatementProfile.setString(1,gender);
            preparedStatementProfile.setString(2,age);
            preparedStatementProfile.setString(3,occupation);
            preparedStatementProfile.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }



}
