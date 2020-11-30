package entertainment;

import java.util.ArrayList;

public class TvShow extends Video {
    private ArrayList<Season> seasonsList;
    private Double rating;
    private int nrOfSez;

    /**
     * constructor for a TvShow
     */
    public TvShow(final String title, final int year, final ArrayList<String> gen,
                  final ArrayList<Season> season, final int nrOfSez) {
        super(title, year, gen);
        this.seasonsList = season;
        this.rating = null;
        this.nrOfSez = nrOfSez;
    }

    public final ArrayList<Season> getSeasonsList() {
        return seasonsList;
    }

    public final void setSeasonsList(final ArrayList<Season> season) {
        this.seasonsList = season;
    }

    public final Double getRating() {
        return rating;
    }

    public final void setRating(final Double rating) {
        this.rating = rating;
    }

    public final int getNrOfSez() {
        return nrOfSez;
    }

    public final void setNrOfSez(final int nrOfSez) {
        this.nrOfSez = nrOfSez;
    }
    /**
     * method that return a season
     * */
    public Season getSeason(final int number) {
        int nr = 1;
        for (Season i : seasonsList) {
            if (nr == number) {
                return i;
            }
            nr++;
        }
        return null;
    }
    /**
     * function for calculate Serial rating
     * */
    public Double averageRating() {

        Double soum = 0.0;
        for (Season i : seasonsList) {
            soum = soum + i.averageRating();

        }
        if (this.nrOfSez != 0) {
            return (soum / this.nrOfSez);
        } else {
            return 0.0;
        }
    }
    /**
     * function for calculate serial duration
     * */
    public int getDuration() {
        int soum = 0;
        for (Season i : seasonsList) {
            soum = soum + i.getDuration();
        }
        if (this.nrOfSez != 0) {
            return soum;
        } else {
            return 0;
        }
    }
}
