package main;

import fileio.*;
import org.json.simple.JSONArray;
import users.User;
import entertainment.*;

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
            Commands command = new Commands(users, movies, tvShows);
            int nrOfViews = 0;
            for (ActionInputData data : input.getCommands()) {
                /**
                 * add movie / tvShow to favoriteList / viewedList
                 * */
                    if (data.getActionType().equals("command") && data.getType().equals("favorite")) {
                        command.addToFavorite(data.getUsername(), data.getTitle());
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "success -> " + data.getTitle() + " was added as favourite"));

                    }
                    if (data.getActionType().equals("command") && data.getType().equals("view")) {
                        command.addToViewedList(data.getUsername(), data.getTitle());
                        nrOfViews = command.getNrOfViews(data.getUsername(), data.getTitle());
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "success -> " + data.getTitle() + " was viewed with total views of "
                                        + nrOfViews));
                    }
                    /**
                     * set rating for movies and tvShows
                     * */
                if (data.getActionType().equals("command") && data.getType().equals("rating")) {
                    command.rating(data.getUsername(), data.getTitle(), data.getGrade(), data.getSeasonNumber());
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                            "success -> " + data.getTitle() + " was rated with " + data.getGrade() + " by "
                                    + data.getUsername()));


                }
            }

    }

//    public void addToFavorite( String title, String username){
//        User user = null;
//        Video newVideo = null;
//        // search for user and movie/serial
//        for (User i : users ){
//            if (i.getUsername().equals(username)){
//                user = i;
//            }
//        }
//        for (TvShow i : tvShows){
//            if(i.getTitle().equals(title)){
//                newVideo = i;
//            }
//        }
//        for (Movie i : movies){
//            if (i.getTitle().equals(title)){
//                newVideo = i;
//            }
//        }
//        /**
//         * verify if video is viewed
//         * */
//        for (ViewedVideos i : user.getViewedVideos()){
//            /**
//             * verify if a video from viewed list has same title
//             * with new video and push newVideo to fav list
//             * */
//            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
//                user.addToFav(newVideo.getTitle(), newVideo.getYear(), newVideo.getGen() );
//            }
//        }
//    }
    // function which add a video in database and return number of view
//    public void addToViewedList(String title, String username){
//        User user = null;
//        Video newVideo = null;
//
//        // search for user and movie/serial
//        for (User i : users ){
//            if (i.getUsername().equals(username)){
//                user = i;
//            }
//        }
//        for (TvShow i : tvShows){
//            if(i.getTitle().equals(title)){
//                newVideo = i;
//            }
//        }
//        for (Movie i : movies){
//            if (i.getTitle().equals(title)){
//                newVideo = i;
//            }
//        }
//        for (ViewedVideos i : user.getViewedVideos()){
//            /**
//             * verify if video has been viewed
//             * */
//            if (i.getVideo().getTitle().equals(newVideo.getTitle())){
//                /**
//                 * if it has been viewed increment number of views
//                 * */
//                i.counter();
//
//            } else{
//                user.addToViewed(newVideo);
//            }
//        }
//
//    }


}
