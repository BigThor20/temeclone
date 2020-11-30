package entertainment;

import java.util.ArrayList;

public class Video {
    /**
     * title of video
     */
    private String title;
    /**
     * launch year
     */
    private int year;
    /**
     * category
     */
    private ArrayList<String> gen;

    /**
     * marked as viewed
     */

    public Video(String title, int year, ArrayList<String> gen) {
        this.title = title;
        this.year = year;
        this.gen = gen;

    }

    /**
     * getter and setter for title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter and setter for year
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * getter and setter for gen
     */
    public ArrayList<String> getGen() {
        return gen;
    }

    public void setGen(ArrayList<String> gen) {
        this.gen = gen;
    }

}
