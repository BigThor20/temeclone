package entertainment;

public class video {
    /**
     * title of video
     * */
    private String title;
    /**
     * launch year
     * */
    private int year;
    /**
     * category
     * */
    private Genre gen;

    public video(String title, int year, Genre gen) {
        this.title = title;
        this.year = year;
        this.gen = gen;
    }

    /**
     * getter and setter for title
     * */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter and setter for year
     * */
    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }
    /**
     * getter and setter for gen
     * */
    public Genre getGen() {
        return gen;
    }

    public void setGen(Genre gen) {
        this.gen = gen;
    }
}
