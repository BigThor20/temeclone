package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie extends Video {
    int duration;

    private Map<String, Double> ratings ;

    public Movie(String title, int year, ArrayList<String> gen, int duration) {
        super(title, year, gen);
        this.duration = duration;
        this.ratings =  new HashMap<String, Double>();
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
    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Double> ratings) {
        this.ratings = ratings;
    }
    /**
     * add rating from a user
     * */
    public void addRating(String Username, Double rating){
        ratings.put(Username,rating);
    }
    /**
     * calculate average rating for a movie
     * */
    public Double averageRating(){
        int nr = 0;
        Double soum = 0.0;
        for (Map.Entry mapElement : ratings.entrySet()){
            soum = soum + (Double)mapElement.getValue();
            nr++;
        }
        if (nr !=0){
            return (soum / nr);
        } else{
            return 0.0;
        }
    }
}
