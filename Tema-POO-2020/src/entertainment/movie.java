package entertainment;

import java.util.ArrayList;
import java.util.List;

public class Movie extends Video {
    int duration;

    private List<Double> ratings;

    public Movie(String title, int year, ArrayList<String> gen, int duration) {
        super(title, year, gen);
        this.duration = duration;
        this.ratings =  new ArrayList<>();
    }
    /**
     * getter and setter for duration of a movie
     * */
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * getter and setter for rating of a movie
     * */
    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }
}
