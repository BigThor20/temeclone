package entertainment;

public class TvShow extends video{
    private Season season;

    /**
     * constructor for a TvShow
     * */
    public TvShow(String title, int year, Genre gen, Season season) {
        super(title, year, gen);
        this.season = season;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

}
