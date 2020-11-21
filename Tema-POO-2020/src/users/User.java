package users;

import java.util.ArrayList;
import java.util.List;
import entertainment.*;

public class User {
    private List<Video> favoriteVideos;
    private List<ViewedVideos> viewedVideos;
    private String type;
    private String username;


    /**
     * Constructor for User
     */
    public User( String username, String type){
        this.username = username;
        this.type = type;
        this.favoriteVideos = new ArrayList<>();
        this.viewedVideos = new ArrayList<>();
    }


    /**
     * getter and setter for favorite videos
     */
    public List<Video> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void setFavoriteVideos(List<Video> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    /**
     * getter and setter for viewed videos
     */
    public List<ViewedVideos> getViewedVideos() {
        return viewedVideos;
    }

    public void setViewedVideos(List<ViewedVideos> viewedVideos) {
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
     * function for add a video in favorite list
     * */
    public void addToFav(String title, int year, ArrayList<String> gen){
        Video newVideo = new Video(title, year, gen);
        favoriteVideos.add(newVideo);
    }
    /**
     * function for add a video in viewed list
     * (create an object and add this to list)
     * */
    public void addToViewed(Video newVideo){
        ViewedVideos viewed =  new ViewedVideos(newVideo);
        viewedVideos.add(viewed);
    }
}
