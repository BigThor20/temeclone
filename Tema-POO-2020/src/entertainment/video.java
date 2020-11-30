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

    public Video(final String title, final int year, final ArrayList<String> gen) {
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

    public final void setTitle(final String title) {
        this.title = title;
    }

    /**
     * getter and setter for year
     */
    public int getYear() {
        return year;
    }

    public final void setYear(final int year) {
        this.year = year;
    }

    /**
     * getter and setter for gen
     */
    public ArrayList<String> getGen() {
        return gen;
    }

    public final void setGen(final ArrayList<String> gen) {
        this.gen = gen;
    }

}
