package recomenderEngine;

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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static recommendingSystem.Main.currentUserId;
import static utils.StaticConstant.Paths.DATA_FOR_RECOMENDATION_FILE_PATH;

public class RecomenderEngine {
    public List<RecommendedItem> getRecomendations() throws TasteException {
        DataModel model = null;
        try {
            model = new FileDataModel(new File(DATA_FOR_RECOMENDATION_FILE_PATH));
            UserSimilarity similarity = null;
            similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            List<RecommendedItem> recommendations = recommender.recommend(currentUserId, 10);//currentUserId as first parameter and ilosc filmow as second
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }
            return recommendations;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
