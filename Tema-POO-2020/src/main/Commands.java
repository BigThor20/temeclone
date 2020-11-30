package main;

import entertainment.Movie;
import entertainment.Season;
import entertainment.TvShow;
import entertainment.Video;
import users.User;

import java.util.ArrayList;

public class Commands {
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;
    private final ArrayList<TvShow> tvShows;

    public Commands(final ArrayList<User> users, final ArrayList<Movie> movies,
                    final ArrayList<TvShow> tvShows) {
        this.users = users;
        this.movies = movies;
        this.tvShows = tvShows;
    }
    /**
     * methods for search a user  in database
     */
    public User getUserInDb(final String username) {
        for (User i : users) {
            if (i.getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }
    /**
     * methods for search movie in database
     */
    public Video getMovieInDb(final String title) {
        for (Movie i : movies) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }
    /**
     * methods for search a show in database
     */
    public Video getSerialInDb(final String title) {
        for (TvShow i : tvShows) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }
    /**
     * method for add a video in fav list
     */
    public int addToFavorite(final String username, final String title) {
        User user = null;

        if (getUserInDb(username) != null) {
            user = getUserInDb(username);
        }
        /**
         * verify if user and video exist
         * */
        if (user != null) {
            /**
             * if a video is viewed push in fav list
             * */
            if (user.getViewedVideos().containsKey(title)) {
                /**
                 * verify if it's already in fav list
                 * */
                if (!user.inFavList(title)) {
                    user.addToFav(title);
                } else {
                    return 0;
                }
            } else {
                return 2;
            }
        }
        return 1;
    }
    /**
     * function that add a video to a user's viewed list
     * */
    public final void addToViewedList(final String username, final String title) {
        User user = null;

        /**
         * search user in database
         * */
        if (getUserInDb(username) != null) {
            user = getUserInDb(username);
        }

        if (user != null) {
            /**
             * verify if a video is viewed and
             *  increment number of views, else
             *  add to viewedList
             * */
            if (user.getViewedVideos().containsKey(title)) {
                user.getViewedVideos().put(title, user.getViewedVideos().get(title) + 1);
            } else {
                user.addToViewed(title, 1);
            }
        }
    }

    /**
     * function for get nr of view of a video
     */
    public int getNrOfViews(final String username, final String title) {
        User user = null;
        int nr = 0;

        if (getUserInDb(username) != null) {
            user = getUserInDb(username);
        }
        if (user != null) {
            nr = user.getViewedVideos().get(title);
        }
        return nr;
    }
    /**
     * function for get rating to a video
     * */
    public final int rating(final String username, final String title,
                            final Double rating, final int nrSez) {
        User user = null;
        TvShow serial = null;
        Season season;
        Movie newMovie = null;

        if (getUserInDb(username) != null) {
            user = getUserInDb(username);
        }
        if (getSerialInDb(title) != null) {
            serial = (TvShow) getSerialInDb(title);
        }
        if (getMovieInDb(title) != null) {
            newMovie = (Movie) getMovieInDb(title);
        }

        if (user != null) {
            // verify if it's viewed
            if (user.getViewedVideos().containsKey(title)) {
                // verify if it's serial
                if (serial != null) {
                    season = serial.getSeason(nrSez);
                    //verify if it's already rated by user
                    if (season != null && !season.getRatings().containsKey(username)) {
                        season.addRating(username, rating);
                        user.incrementRating();
                        return 1;
                    } else {
                        return 0;
                    }
                }
                //verify if it's movie
                if (newMovie != null) {
                    //verify if it's already rated by user
                    if (!newMovie.getRatings().containsKey(username)) {
                        newMovie.addRating(username, rating);
                        user.incrementRating();
                        return 1;
                    } else {
                        return 0;
                    }
                }
            } else {
                return 2;
            }

        }
        return 1;
    }

}
