package users;

import java.util.ArrayList;
import java.util.List;
import entertainment.*;

public class User {
    private List<video> favoriteVideos;
    private List<ViewedVideos> viewedVideos;
    private UserType type;


    /**
     * Constructor for User
     */
    public User(UserType type){
        this.type = type;
        this.favoriteVideos = new ArrayList<>();
        this.viewedVideos = new ArrayList<>();
    }

    /**
     * getter and setter for favorite videos
     */
    public List<video> getFavoriteVideos() {
        return favoriteVideos;
    }

    public void setFavoriteVideos(List<video> favoriteVideos) {
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
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * function for add a video in favorite list
     * */
    public void addToFav(video newVideo){
        favoriteVideos.add(newVideo);
    }
    /**
     * function for add a video in viewed list
     * (create an object and add this to list)
     * */
    public void addToViewed(video newVideo){
        ViewedVideos viewed =  new ViewedVideos(newVideo);
        viewedVideos.add(viewed);
    }
}
