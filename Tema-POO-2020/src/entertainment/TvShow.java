package entertainment;

import java.util.ArrayList;

public class TvShow extends Video {
    private ArrayList<Season> seasonsList;
    private Double rating ;
    private int nrOfSez;

    /**
     * constructor for a TvShow
     * */
    public TvShow(String title, int year, ArrayList<String> gen, ArrayList<Season> season, int nrOfSez) {
        super(title, year, gen);
        this.seasonsList = season;
        this.rating = null;
        this.nrOfSez = nrOfSez;
    }

    public ArrayList<Season> getSeasonsList() {
        return seasonsList;
    }

    public void setSeasonsList(ArrayList<Season> season) {
        this.seasonsList = season;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getNrOfSez() {
        return nrOfSez;
    }

    public void setNrOfSez(int nrOfSez) {
        this.nrOfSez = nrOfSez;
    }

    public Season getSeason(int number){
        int nr = 1;
        for (Season i : seasonsList){
            if (nr == number){
                return i;
            }
            nr++;
        }
        return  null;
    }
    // function for calculate Serial rating
    public Double averageRating(){

        Double soum = 0.0;
        for (Season i : seasonsList){
            soum = soum + i.averageRating();

        }
        if (this.nrOfSez != 0){
            return (soum / this.nrOfSez);
        } else{
            return 0.0;
        }

    }
    //function for calculate serial duration
    public int getDuration(){
        int soum = 0;
        for (Season i : seasonsList){
            soum = soum + i.getDuration();
        }
        if (this.nrOfSez != 0){
            return  soum;
        } else {
            return 0;
        }
    }

}
