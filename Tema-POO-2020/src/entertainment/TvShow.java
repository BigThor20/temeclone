package entertainment;

import java.util.ArrayList;

public class TvShow extends Video {
    private ArrayList<Season> season;

    /**
     * constructor for a TvShow
     * */
    public TvShow(String title, int year, ArrayList<String> gen, ArrayList<Season> season) {
        super(title, year, gen);
        this.season = season;
    }

    public ArrayList<Season> getSeason() {
        return season;
    }

    public void setSeason(ArrayList<Season> season) {
        this.season = season;
    }

}
