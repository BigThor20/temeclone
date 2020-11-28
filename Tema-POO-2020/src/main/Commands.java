package main;

import entertainment.Movie;
import entertainment.Season;
import entertainment.TvShow;
import entertainment.Video;
import users.User;

import java.util.ArrayList;

public class Commands {
    ArrayList<User> users ;
    ArrayList<Movie> movies;
    ArrayList<TvShow> tvShows;

    public Commands(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<TvShow> tvShows) {
        this.users = users;
        this.movies = movies;
        this.tvShows = tvShows;
    }
    /**
     * methods for search a user and a video in db
     * */
    public User getUserInDb(String username){
        for (User i : users ){
            if (i.getUsername().equals(username)){
                return i;
            }
        }
        return null;
    }
    public Video getMovieInDb(String title){
        for (Movie i : movies){
            if (i.getTitle().equals(title)){
                return i;
            }
        }
        return null;
    }
    public Video getSerialInDb(String title){
        for (TvShow i : tvShows){
            if(i.getTitle().equals(title)){
                 return i;
            }
        }
        return null;
    }
    /**
     * method for add a video in fav list
     * */
    public int addToFavorite(String username, String title){
        User user = null;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }

        /**
         * verify if user and video exist
         * */
        if(user != null) {
            /**
             * if a video is viewed push in fav list
             * */
            if(user.getViewedVideos().containsKey(title)){
                /**
                 * verify if it's already in fav list
                 * */
                if (!user.inFavList(title)){
                    user.addToFav(title);
                } else{
                    return 3;
                }
            } else {
                return 2;
            }
        }
        return 1;
    }
    public void addToViewedList(String username, String title){
        User user = null;

        /**
         * search user in database
         * */
        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }

        if (user != null ){
            /**
             * verify if a video is viewed and
             *  increment number of views, else
             *  add to viewedList
             * */
            if (user.getViewedVideos().containsKey(title)){
                user.getViewedVideos().put(title, user.getViewedVideos().get(title) + 1);
            } else {
                user.addToViewed(title,1);
            }
        }
    }
    /**
     * function for get nr of view of a video
     * */
    public int getNrOfViews(String username, String title){
        User user = null;
        int nr = 0;

        if(getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        if (user != null){
            nr = user.getViewedVideos().get(title);
        }
        return nr;
    }



    public int rating(String username, String title, Double rating, int NrSez){
        User user = null;
        TvShow serial = null;
        Season season;
        Movie newMovie = null;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        if(getSerialInDb(title) != null){
            serial = (TvShow) getSerialInDb(title);
        }
        if(getMovieInDb(title) != null){
            newMovie = (Movie) getMovieInDb(title);
        }

        if (user != null ){
            // verify if it's viewed
            if (user.getViewedVideos().containsKey(title)){
                // verify if it's serial
                if (serial != null){
                    season = serial.getSeason(NrSez);
                    //verify if it's already rated by user
                    if (season != null && !season.getRatings().containsKey(username)){
                        season.addRating(username, rating);
                        user.incrementRating();
                        return 1;
                    } else {
                        return 3;
                    }
                }
                //verify if it's movie
                if (newMovie != null){
                    //verify if it's already rated by user
                    if (!newMovie.getRatings().containsKey(username)){
                        newMovie.addRating(username, rating);
                        user.incrementRating();
                        return 1;
                    } else {
                        return 3;
                    }
                }
            } else {
                return 2;
            }

        }
        return 1;
    }

}
