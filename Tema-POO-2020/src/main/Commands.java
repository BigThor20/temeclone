package main;

import entertainment.Movie;
import entertainment.TvShow;
import entertainment.Video;
import users.User;
import users.ViewedVideos;

import java.util.ArrayList;

public class Commands {
    ArrayList<User> users;
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
    public void addToFavorite(String username, String title){
        User user = null;
        Video newVideo = null;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }

        if(getMovieInDb(title) != null){
            newVideo = getMovieInDb(title);
        } else if(getSerialInDb(title) != null){
            newVideo = getSerialInDb(title);
        }
        /**
         * verify if user and video exist
         * */
        if(user != null && newVideo != null) {
            /**
             * if a video is viewed push in fav list
             * */
            if(newVideo.getMarkedAsViewed() != 0){
                user.addToFav(newVideo.getTitle(), newVideo.getYear(), newVideo.getGen());
            }

        }
    }
    public void addToViewedList(String username, String title){
        User user = null;
        Video newVideo = null;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }

        if(getMovieInDb(title) != null){
            newVideo = getMovieInDb(title);
        } else if(getSerialInDb(title) != null){
            newVideo = getSerialInDb(title);
        }
        if(user != null && newVideo != null){
            /**
             * if it hasn't view, mark as view and add in list
             * */
            if (newVideo.getMarkedAsViewed() == 0){
                newVideo.setAsViewed();
                user.addToViewed(newVideo);
            } else{
                for (ViewedVideos i : user.getViewedVideos()){
                    if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                        /**
                         * if it has been viewed, increment number of views
                         * */
                        i.counter();
                    }
                }
            }
        }


    }
    /**
     * function for get nr of view of a video
     * */
    public int getNrOfViews(String username, String title){
        User user = null;
        Video newVideo = null;
        int nr = 0;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        // verify if a video is movie or serial
        if(getMovieInDb(title) != null){
            newVideo = getMovieInDb(title);
        } else if(getSerialInDb(title) != null){
            newVideo = getSerialInDb(title);
        }

        if (user != null && newVideo != null){
            for (ViewedVideos i : user.getViewedVideos()){
                if (i.getVideo().getTitle().equals(newVideo.getTitle())){
                    nr = i.getNumberOfViews();
                }
            }
        }
        return nr;
    }

    public void rating(String username, String title, double rating, int seasonNumber){
        User user = null;
        Movie newMovie = null;
        TvShow newSerial = null;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }

        if(getMovieInDb(title) != null){
            newMovie = (Movie) getMovieInDb(title);
        } else if(getSerialInDb(title) != null){
            newSerial = (TvShow) getSerialInDb(title);
        }
            // verify if video is viewed
        if (  newMovie != null){
                /**
                 * set rating for a movie if it was viewed and
                 * hasn't another rating
                 * */
                if (newMovie.getMarkedAsViewed() != 0 && newMovie.getRatings() != null){
                    newMovie.setRatings(rating);
                }
        }

        if ( newSerial != null){
            /**
             * set rating for a tvShow if it was viewed and
             * hasn't another rating
             * */
            if(newSerial.getMarkedAsViewed() != 0 && newSerial.getRating() != null) {
                //rating pe sezon in functie de data.getNumberOfSeason
                //fiecare user da rating pe un singur sezon o data
                //rating ul serialului e media aritmetica a sezoanelor

            }
        }


    }

}
