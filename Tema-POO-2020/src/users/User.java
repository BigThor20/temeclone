package users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private  ArrayList<String> favoriteVideos = new ArrayList<String>();
    private  Map<String, Integer> viewedVideos = new HashMap<>();
    private final String type;
    private final String username;
    private int nrOfRating;
    /**
     * Constructor for User
     */
    public User(final String username, final String type, final ArrayList<String> fav,
                final Map<String, Integer> map) {
        this.username = username;
        this.type = type;
        this.favoriteVideos = fav;
        this.viewedVideos = map;
        this.nrOfRating = 0;
    }

    /**
     * getter and setter for username
     */
    public String getUsername() {
        return username;
    }
    /**
     * getter and setter for favorite videos
     */
    public ArrayList<String> getFavoriteVideos() {
        return favoriteVideos;
    }
    /**
     * getter and setter for viewed videos
     */
    public Map<String, Integer> getViewedVideos() {
        return viewedVideos;
    }
    /**
     * getter and setter for user type
     */
    public String getType() {
        return type;
    }
    /**
     * getter and setter for nr of ratings
     */
    public int getNrOfRating() {
        return nrOfRating;
    }
    /**
     * function for add a video in favorite list
     */
    public void addToFav(final String title) {
        favoriteVideos.add(title);
    }
    /**
     * function for add a video in viewed list
     * (create an object and add this to list)
     */
    public void addToViewed(final String newVideo, final int nrOfView) {
        viewedVideos.put(newVideo, nrOfView);
    }
    /**
     * function for verify if a video is in fav list
     */
    public boolean inFavList(final String title) {
        for (String i : favoriteVideos) {
            if (i.equals(title)) {
                return true;
            }
        }
        return false;
    }
    /**
     * function for increment number of ratings
     */
    public void incrementRating() {
        this.nrOfRating++;
    }
    /**
     * function for get nrOfViews of a video
     */
    public int getNrOfView(final String title) {
        int nr = 0;
        for (Map.Entry<String, Integer> mapElement : viewedVideos.entrySet()) {
            if (mapElement.getKey().equals(title)) {
                nr = mapElement.getValue();
                return nr;
            }
        }
        // if video doesn't exist in view list return 0
        return nr;

    }
    /**
     * method for verify if a video it's viewed
     */
    public boolean inViewedList(final String title) {
        if (viewedVideos != null) {
            for (Map.Entry<String, Integer> mapElement : viewedVideos.entrySet()) {
                if (mapElement.getKey().equals(title)) {
                    return true;
                }
            }
        }
        return false;
    }

}
