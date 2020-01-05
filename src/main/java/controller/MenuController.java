package controller;

import connector.Connector;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.DaoService;
import org.apache.commons.lang.StringUtils;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import recomenderEngine.RecomenderEngine;
import recommendingSystem.BaseClientService;
import recommendingSystem.Main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class MenuController extends BaseClientService {
    public Label label;
    private Main main;

    private RecomenderEngine recomenderEngine = new RecomenderEngine();
    private DaoService daoService = new DaoService();
    @FXML
    public Button logout;

    @FXML
    public Button find;

    @FXML
    public Button findMovie;

    @FXML
    public TextField movieIdOrNameField;

    @FXML
    public TextField movieIdField;

    @FXML
    public Label movieName;

    @FXML
    public Slider rating;

    @FXML
    public ListView<String> recomendationList;


    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
       main.showLoginView();
    }

    @FXML
    public void rate(ActionEvent event) throws IOException {
        if(daoService.isAbleToRateMovie(getMovieIdField())) {
            daoService.rateMovie(getRatingField(), getMovieIdField());
        } else{
            showAlert("Failed", "You have already rated this movie");
        }
    }

    @FXML
    public void findMovie(ActionEvent event) throws IOException {
        String movieNameForm = getMovieIdOrNameField();
        if(StringUtils.isNotEmpty(movieNameForm)) {
            if (movieNameForm.matches("\\d+")) {
                String name = daoService.findMovieById(getMovieIdOrNameField());
                if(StringUtils.isEmpty(name)){
                    showAlert("Failed", "Cannot find any movie.");
                }else{
                    movieName.setText(name);
                }
            }else{
                String result = daoService.findMovieByName(getMovieIdOrNameField());
                if(StringUtils.isEmpty(result)){
                    showAlert("Failed", "Cannot find any movie.");
                }else{
                    movieName.setText(result);
                }
            }
        }else{
            showAlert("Failed", "Put movie name or movie id in appropriate form box.");
        }

    }

    @FXML
    public void find(ActionEvent event) throws IOException {
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recomenderEngine.getRecomendations();
            recomendationList.setEditable(true);

            if(recommendations.isEmpty()){
                showAlert("Failed", "No recommendation found");
                return;
            }

            recomendationList.getItems().clear();
            for (RecommendedItem recommendedItem : recommendations) {
                recomendationList.getItems().add(daoService.getMovie(recommendedItem.getItemID()));
            }
        } catch (TasteException e) {
            showAlert("Failed", "You haven't rated yet");
        }
        //recomendationList.getItems().add(connector.getMovie(connector.getRecomendations()));
    }


    private String getMovieIdField(){
        return movieIdField.getText();
    }

    private Double getRatingField(){
        return rating.getValue();
    }

    private String getMovieIdOrNameField(){
        return movieIdOrNameField.getText();
    }



}
