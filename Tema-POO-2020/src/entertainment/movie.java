package entertainment;

import java.util.ArrayList;
import java.util.List;

public class movie extends video{
    int duration;

    private List<Double> ratings;

    public movie(String title, int year, Genre gen, int duration) {
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
