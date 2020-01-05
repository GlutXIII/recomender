package model;

import connector.Connector;
import recommendingSystem.Main;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static recommendingSystem.Main.currentUserId;
import static utils.StaticConstant.Model.*;
import static utils.StaticConstant.Paths.DATA_FOR_RECOMENDATION_FILE_PATH;

public class DaoService {

    private Connector connector = new Connector();

    public Recommendations getRecomendations() {
        connector.runConnection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = connector.getConnection().prepareStatement("SELECT * from recommendations WHERE user_profile_id = ? ");
            preparedStatement.setString(1, String.valueOf(currentUserId));
            ResultSet resultSet = preparedStatement.executeQuery();
            Recommendations recommendations = new Recommendations();
            resultSet.next();
            recommendations.setMovieId(resultSet.getInt(resultSet.findColumn("movie_id")));
            recommendations.setUserProfileId(currentUserId);
            recommendations.setId(resultSet.getInt(resultSet.findColumn("id")));
            recommendations.setRatingId(resultSet.getInt(resultSet.findColumn("rating_id")));
            connector.closeConnection();
            return recommendations;


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMovie(long movieId) {
        connector.runConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getConnection().prepareStatement("SELECT * from movie WHERE movie_id = ? ");
            preparedStatement.setLong(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("title");
            connector.closeConnection();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerCheck(String login, String password, String gender, String occupation, String age) {
        try {
            connector.runConnection();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement("INSERT INTO users(login,password) VALUES(?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatementProfile = connector.getConnection().prepareStatement("INSERT INTO user_profile(gender,age,occupation) VALUES(?,?,?)");
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

    public void rateMovie(Double rating, String movieId) throws IOException {
        try {
            connector.runConnection();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement("INSERT INTO rating(user_id,movie_id,rating,timestamp) VALUES(?,?,?,2)");
            preparedStatement.setString(1, String.valueOf(currentUserId));
            preparedStatement.setString(2, movieId);
            preparedStatement.setString(3, String.valueOf(rating));
            preparedStatement.executeUpdate();

            FileWriter csvWriter = new FileWriter (DATA_FOR_RECOMENDATION_FILE_PATH, true);
            csvWriter.append("\n");
            csvWriter.append(String.valueOf(currentUserId));
            csvWriter.append(",");
            csvWriter.append(movieId);
            csvWriter.append(",");
            csvWriter.append(String.valueOf(rating));
            csvWriter.append(",");
            csvWriter.append("2");
            csvWriter.flush();
            csvWriter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String findMovieByName(String movieName) {
        connector.runConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getConnection().prepareStatement("SELECT * from movie WHERE  title LIKE ?");
            preparedStatement.setString(1, "%" + movieName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            String result;
            if(resultSet.next()) {
                result = "Id: " + resultSet.getString("movie_id") + " Title: " + resultSet.getString("title");
            }else{
                return null;
            }
            preparedStatement = connector.getConnection().prepareStatement("SELECT * from rating join movie on rating.movie_id = movie.movie_id WHERE title LIKE ? and rating.user_id = ?");
            preparedStatement.setString(1, "%" + movieName + "%");
            preparedStatement.setString(2, String.valueOf(currentUserId));

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                result = result + " Your rating: not rated yet";
            } else {
                result = result + " Your rating: " + resultSet.getString("rating");
            }

            connector.closeConnection();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String findMovieById(String movieId) {
        connector.runConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connector.getConnection().prepareStatement("SELECT * from movie WHERE  movie_id = ?");
            preparedStatement.setString(1, movieId);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String result = "Id: " + movieId + " Title: " + resultSet.getString("title") ;

            preparedStatement = connector.getConnection().prepareStatement("SELECT * from rating WHERE movie_id = ? and rating.user_id = ?");
            preparedStatement.setString(1, movieId);
            preparedStatement.setString(2, String.valueOf(currentUserId));

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                result = result + " Your rating: not rated yet";
            } else {
                result = result + " Your rating: " + resultSet.getString("rating");
            }

            connector.closeConnection();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean checkCredentials(String login, String password) {
        try {
            connector.runConnection();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement("SELECT * from users WHERE login = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            } else {
                currentUserId = resultSet.getInt(resultSet.findColumn(USER_ID));
                connector.closeConnection();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void saveRatingToCsvFile() throws IOException {
        FileWriter csvWriter = new FileWriter (DATA_FOR_RECOMENDATION_FILE_PATH);
        try {
            connector.runConnection();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement("SELECT * FROM recomending_system.rating");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.next()) {
                csvWriter.write(String.valueOf(resultSet.findColumn(USER_ID)));
                csvWriter.append(",");
                csvWriter.append(MOVIE_ID);
                csvWriter.append(",");
                csvWriter.append(RATING);
                csvWriter.append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public boolean isAbleToRateMovie(String movieId) {
        try {
            connector.runConnection();
            PreparedStatement preparedStatement = connector.getConnection().prepareStatement("SELECT * from rating WHERE user_id = ? AND movie_id = ?");
            preparedStatement.setString(1, String.valueOf(currentUserId));
            preparedStatement.setString(2, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                connector.closeConnection();
                return true;
            } else {
                connector.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}