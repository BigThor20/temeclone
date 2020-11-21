package main;

import fileio.*;
import org.json.simple.JSONArray;
import users.User;
import entertainment.*;
import users.ViewedVideos;

import java.io.IOException;
import java.util.ArrayList;


public class Database {
    Input input;
    Writer fileWriter ;
    JSONArray arrayResult;
    ArrayList<User> users;
    ArrayList<Movie> movies;
    ArrayList<TvShow> tvShows;

    public Database(Input input) {
        this.input = input;
        users = new ArrayList<>();
        movies = new ArrayList<>();
        tvShows = new ArrayList<>();

    }
    public void init(Writer file, JSONArray arr) throws IOException {
        this.fileWriter = file;
        this.arrayResult = arr;
        addMovieToBd();
        addSerialToBd();
        addUsersToBd();
        commands();
    }
    /**
     * add videos and users in bd
     * */
    public void addMovieToBd(){
        for (MovieInputData data : input.getMovies()){
            Movie video = new Movie(data.getTitle(), data.getYear(), data.getGenres(), data.getDuration());
            movies.add(video);
        }
    }

    public void addSerialToBd(){
        for (SerialInputData data : input.getSerials()){
            TvShow show = new TvShow(data.getTitle(), data.getYear(), data.getGenres(), data.getSeasons());
            tvShows.add(show);
        }
    }
    public void addUsersToBd(){
        for (UserInputData data : input.getUsers()){
            User user = new User(data.getUsername(), data.getSubscriptionType());
            users.add(user);
        }
    }
    /**
     * function for commands: favorite, view, rating
     * */
    public void commands() throws IOException {
        for (User user : users) {
            for (ActionInputData data : input.getCommands()) {
                /**
                 * add movie / tvShow to favoriteList / viewedList
                 * */
                for(Movie movie : movies){
                    if (data.getActionType().equals("command") && data.getType().equals("favorite")) {
                        addToFavorite(movie, user);
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",  "success -> " + data.getTitle() + " was added as favourite"));
                    }
                    if (data.getActionType().equals("command") && data.getType().equals("view")) {
                        addToViewedList(movie, user);
                    }
                }
                for(TvShow tvShow : tvShows){
                    if (data.getActionType().equals("command") && data.getType().equals("favorite")) {
                        addToFavorite(tvShow, user);
                    }
                    if (data.getActionType().equals("command") && data.getType().equals("view")) {
                        addToViewedList(tvShow, user);
                    }
                }
            }
        }
    }

    public void addToFavorite( Video newVideo, User user){
        /**
         * verify if video is viewed
         * */
        for (ViewedVideos i : user.getViewedVideos()){
            /**
             * verify if a video from viewed list has same title
             * with new video and push newVideo to fav list
             * */
            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                user.addToFav(newVideo.getTitle(), newVideo.getYear(), newVideo.getGen() );
            }
        }
    }
    public void addToViewedList(Video newVideo, User user){
        for (ViewedVideos i : user.getViewedVideos()){
            /**
             * verify if video has been viewed
             * */
            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                /**
                 * if it has been viewed increment number of views
                 * */
                i.counter();
            } else{
                user.addToViewed(newVideo);
            }
        }
    }


}
