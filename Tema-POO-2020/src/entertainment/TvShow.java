package entertainment;

import java.util.ArrayList;

public class TvShow extends Video {
    private ArrayList<Season> season;
    private Double rating ;

    /**
     * constructor for a TvShow
     * */
    public TvShow(String title, int year, ArrayList<String> gen, ArrayList<Season> season) {
        super(title, year, gen);
        this.season = season;
        this.rating = null;
    }

    public ArrayList<Season> getSeason() {
        return season;
    }

    public void setSeason(ArrayList<Season> season) {
        this.season = season;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
