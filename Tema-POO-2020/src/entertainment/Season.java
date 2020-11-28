package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private Map<String, Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new HashMap<String, Double>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }

    public void addRating(String username, Double rating){
        ratings.put(username,rating);
    }

    // return average rating for a season
    public Double averageRating(){
        int nr = 0;
        double soum = 0.0;
        for (Map.Entry mapElement : ratings.entrySet()){
            soum = soum + (Double)mapElement.getValue();
            nr++;

        }
        //if it hasn't ratings, return 0
        if (nr != 0){
            return (soum / nr);
        } else{
            return 0.0;
        }

    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

