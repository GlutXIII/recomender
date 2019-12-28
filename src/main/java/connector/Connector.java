package connector;


import model.Recommendations;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

import java.sql.*;

public class Connector extends BaseClientService {

    private static final String DATABASE_NAME = "jdbc:mysql://localhost/recomending_system";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "ZAQ!2wsx";

    public Connection connection = null;

    public boolean checkCredentials(String login, String password) {
        try {
            runConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert("Failed", "Recheck your credentials");
                return false;
            } else {
                Main.currentUserId = resultSet.getInt(resultSet.findColumn("users_id"));

                closeConnection();
                showAlert("Success", "Welcome to recommending system");
                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Recommendations getRecomendations() {
        runConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connection.prepareStatement("SELECT * from recommendations WHERE user_profile_id = ? ");
            preparedStatement.setString(1, String.valueOf(Main.currentUserId));
            ResultSet resultSet = preparedStatement.executeQuery();
            Recommendations recommendations = new Recommendations();
            resultSet.next();
            recommendations.setMovieId(resultSet.getInt(resultSet.findColumn("movie_id")));
            recommendations.setUserProfileId(Main.currentUserId);
            recommendations.setId(resultSet.getInt(resultSet.findColumn("id")));
            recommendations.setRatingId(resultSet.getInt(resultSet.findColumn("rating_id")));
            closeConnection();
            return recommendations;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMovie(Recommendations recommendations) {
        runConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * from movie WHERE movie_id = ? ");
            preparedStatement.setInt(1, recommendations.getMovieId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("title");
            closeConnection();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerCheck(String login, String password, String gender, String occupation, String age) {
        try {
            runConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(login,password) VALUES(?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatementProfile = connection.prepareStatement("INSERT INTO user_profile(gender,age,occupation) VALUES(?,?,?)");
            preparedStatementProfile.setString(1, gender);
            preparedStatementProfile.setString(2, age);
            preparedStatementProfile.setString(3, occupation);
            preparedStatementProfile.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    private void runConnection(){
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

    private void closeConnection() throws SQLException {
        connection.close();
        //System.out.println("Connection was closed");
    }


}
