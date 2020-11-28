package users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entertainment.*;

public class User {
    private ArrayList<String> favoriteVideos = new ArrayList<String>();
    private Map<String, Integer> viewedVideos =  new HashMap<>();
    private String type;
    private String username;
    public int nrOfRating;


    /**
     * Constructor for User
     */
    public User( String username, String type,ArrayList<String> fav, Map<String,Integer> map){
        this.username = username;
        this.type = type;
        this.favoriteVideos = fav;
        this.viewedVideos =  map;
        this.nrOfRating = 0;
    }

    /**
     * getter and setter for username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * getter and setter for favorite videos
     */
    public ArrayList<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void setFavoriteVideos(ArrayList<String> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    /**
     * getter and setter for viewed videos
     */
    public Map<String, Integer> getViewedVideos() {
        return viewedVideos;
    }

    public void setViewedVideos(Map<String, Integer> viewedVideos) {
        this.viewedVideos = viewedVideos;
    }

    /**
     * getter and setter for user type
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    /**
     * getter and setter for nr of ratings
     * */
    public int getNrOfRating() {
        return nrOfRating;
    }

    public void setNrOfRating(int nrOfRating) {
        this.nrOfRating = nrOfRating;
    }

    /**
     * function for add a video in favorite list
     * */
    public void addToFav(String title){
        favoriteVideos.add(title);
    }
    /**
     * function for add a video in viewed list
     * (create an object and add this to list)
     * */
    public void addToViewed(String newVideo, int nrOfView){
        viewedVideos.put(newVideo,nrOfView);
    }
    /**
     * function for verify if a video is in fav list
     * */
    public boolean inFavList(String title){
        for (String i : favoriteVideos){
            if (i.equals(title)){
                return true;
            }
        }
        return false;
    }
    /**
     * function for increment number of ratings
     * */
    public void incrementRating(){
        this.nrOfRating++;
    }
    /**
     * function for get nrOfViews of a video
     * */
    public int getNrOfView(String title){
        int nr = 0;
        for (Map.Entry mapElement : viewedVideos.entrySet()){
            if (mapElement.getKey().equals(title)){
                nr = (int) mapElement.getValue();
                return nr;
            }
        }
        // if video doesn't exist in view list return 0
        return nr;

    }

}
