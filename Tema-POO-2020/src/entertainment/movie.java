package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie extends Video {
    private int duration;
    private Map<String, Double> ratings;

    public Movie(final String title, final int year, final ArrayList<String> gen,
                 final int duration) {
        super(title, year, gen);
        this.duration = duration;
        this.ratings = new HashMap<String, Double>();
    }
    /**
     * getter and setter for duration of a movie
     */
    public final int getDuration() {
        return duration;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }
    /**
     * getter and setter for rating of a movie
     */
    public final Map<String, Double> getRatings() {
        return ratings;
    }

    public final void setRatings(final Map<String, Double> ratings) {
        this.ratings = ratings;
    }
    /**
     * add rating from a user
     */
    public void addRating(final String username, final Double rating) {
        ratings.put(username, rating);
    }
    /**
     * calculate average rating for a movie
     */
    public Double averageRating() {
        int nr = 0;
        Double soum = 0.0;
        for (Map.Entry<String, Double> mapElement : ratings.entrySet()) {
            soum = soum + mapElement.getValue();
            nr++;
        }
        if (nr != 0) {
            return (soum / nr);
        } else {
            return 0.0;
        }
    }
}
