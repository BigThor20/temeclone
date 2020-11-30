package main;

import actor.Actor;
import entertainment.Movie;
import entertainment.TvShow;
import users.User;

import java.util.*;


public class Query {
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;
    private final ArrayList<TvShow> tvShows;
    private final ArrayList<Actor> actors;

    public Query(final ArrayList<User> users, final ArrayList<Movie> movies,
                 final ArrayList<TvShow> tvShows, final ArrayList<Actor> actors) {
        this.users = users;
        this.movies = movies;
        this.tvShows = tvShows;
        this.actors = actors;
    }
    /**
     * methods for search a user and a video in db
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
     * search a movie in database
     * */
    public Movie getMovieInDb(final String title) {
        for (Movie i : movies) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }
    /**
     * search a show in database
     * */
    public TvShow getSerialInDb(final String title) {
        for (TvShow i : tvShows) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }
    /**
     * method for sort a HashMap by 2 criteries
     */
    private static Map<String, Double> sortHashMap(final Map<String, Double> unsortMap,
                                                   final String sortType) {
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(final Map.Entry<String, Double> obj1,
                               final Map.Entry<String, Double> obj2) {
                if (!obj1.getValue().equals(obj2.getValue())) {
                    if (sortType.equals("asc")) {
                        return (obj1.getValue().compareTo(obj2.getValue()));
                    }
                    return (obj2.getValue()).compareTo(obj1.getValue());
                } else {
                    if (sortType.equals("asc")) {
                        return (obj1.getKey().compareTo(obj2.getKey()));
                    }
                    return (obj2.getKey().compareTo(obj1.getKey()));
                }

            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    /**
     * sort hashMap for integer value
     * */
    private static Map<String, Integer> sortHashMapInt(final Map<String, Integer> unsortMap,
                                                       final String sortType) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(final Map.Entry<String, Integer> obj1,
                               final Map.Entry<String, Integer> obj2) {
                if (!obj1.getValue().equals(obj2.getValue())) {
                    if (sortType.equals("asc")) {
                        return (obj1.getValue().compareTo(obj2.getValue()));
                    }
                    return (obj2.getValue()).compareTo(obj1.getValue());
                } else {
                    if (sortType.equals("asc")) {
                        return (obj1.getKey().compareTo(obj2.getKey()));
                    }
                    return (obj2.getKey().compareTo(obj1.getKey()));
                }
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    /**
     * method for average query at actors
     * */
    public ArrayList<String> averageActors(final int nrOfActors, final String sortType) {
        ArrayList<String> filmography = new ArrayList<String>();
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> actorsAverage = new HashMap<String, Double>();
        Movie movie = null;
        TvShow tvShow = null;
        Double soum = 0.0;
        int nr = 0;
        for (Actor actor : actors) {
            filmography = actor.getFilmography();
            soum = 0.0;
            nr = 0;

            for (String video : filmography) {
                // get video from database
                if (getMovieInDb(video) != null) {
                    movie = (Movie) getMovieInDb(video);
                } else {
                    movie = null;
                }
                if (getSerialInDb(video) != null) {
                    tvShow = (TvShow) getSerialInDb(video);
                } else {
                    tvShow = null;
                }

                //get average rating for a video
                if (movie != null) {
                    if (movie.averageRating() != 0) {
                        soum = soum + movie.averageRating();
                        nr++;
                    }
                }
                if (tvShow != null) {
                    if (tvShow.averageRating() != 0) {
                        soum = soum + tvShow.averageRating();
                        nr++;
                    }
                }
            }
            // calculate average and push in HashMap
            if (nr != 0) {
                actorsAverage.put(actor.getName(), soum / nr);
            }
        }

        actorsAverage = sortHashMap(actorsAverage, sortType);

        for (Map.Entry<String, Double> mapElement : actorsAverage.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        //aici e o problema pt ca unii actori sunt mai multi decat final list

        int i = 0;
        while (i < nrOfActors && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }

    /**
     * function for generate all actors with specify awards
     */
    public ArrayList<String> awardsActors(final String sortType, final List<String> awards) {
        ArrayList<String> finalList = new ArrayList<String>();
        Map<String, Integer> totalAwards = new HashMap<String, Integer>();
        for (Actor actor : actors) {
            if (verifyAwards(actor, awards)) {
                //put total number of awards in HashMap
                totalAwards.put(actor.getName(), actor.totalNumberOfAwards());
            }
        }
        //sort HashMap by sortType criteria
        totalAwards = sortHashMapInt(totalAwards, sortType);

        for (Map.Entry<String, Integer> mapElement : totalAwards.entrySet()) {
            finalList.add(mapElement.getKey());

        }
        return finalList;
    }
    /**
     * function for verify if an actor has all awards from list
     * */
    public final boolean verifyAwards(final Actor actor, final List<String> awards) {
        for (String award : awards) {
            if (!actor.hasAward(award)) {
                return false;
            }
        }
        return true;
    }

    /**
     * function for generate list with actors which contains
     * all keywords in description
     */
    public ArrayList<String> filterDescription(final String sortType,
                                               final List<String> words) {
        ArrayList<String> finalList = new ArrayList<String>();
        Map<String, Integer> correctActors = new HashMap<String, Integer>();
        for (Actor actor : actors) {
            if (verifyDescription(actor, words) != null) {
                correctActors.put(actor.getName(), 1);
            }
        }
        //sort HashMap just by alphabetic order, because values are 1(all)
        correctActors = sortHashMapInt(correctActors, sortType);
        //generate final List
        for (Map.Entry<String, Integer> mapElement : correctActors.entrySet()) {
            finalList.add(mapElement.getKey());
        }
        return finalList;
    }
    /**
     * method that verify if a description contains words from list
     * */
    public final Actor verifyDescription(final Actor actor, final List<String> words) {
        //generate an array of word from actor description
        String[] wordsArray = actor.getCareerDescription().split("[^a-zA-Z]+");

        boolean contains = false;
        for (String keyword : words) {
            contains = false;
            // search keyword in actorDescription
            for (String word : wordsArray) {
                word = word.toLowerCase();
                if (keyword.equals(word)) {
                    contains = true;
                }
            }
            // if i don't find a keyword in actor desciption return null
            if (!contains) {
                return null;
            }
        }
        if (contains) {
            return actor;
        }
        return null;
    }
    /**
     * function for filter movie by year and genre
     */
    public Movie movieFilter(final Movie movie, final List<String> years,
                             final List<String> genre) {
        int y;
        boolean confirmYear = false;
        boolean confirmGen = false;
        //verify if it's same year with filter
        if (years.get(0) != null) {
            for (String year : years) {
                if (year != null) {
                    y = Integer.parseInt(year);
                    if (movie.getYear() == y) {
                        confirmYear = true;
                    }
                }
            }
        } else {
            confirmYear = true;
        }
        //verify if it's same gen with filter
        if (genre.get(0) != null) {
            for (String filterGen : genre) {
                if (filterGen != null) {
                    for (String gen : movie.getGen()) {
                        //if exist one gen in movie genres set confirm to true
                        if (filterGen.equals(gen)) {
                            confirmGen = true;
                        }
                    }
                }
            }
        } else {
            confirmGen = true;
        }

        if (confirmGen && confirmYear) {
            return movie;
        } else {
            return null;
        }
    }
    /**
     * method that sort movies by rating
     * */
    public final ArrayList<String> movieRating(final int nrOfMovies, final String sortType,
                                         final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings = new HashMap<String, Double>();
        //put movies in HashTable
        for (Movie movie : movies) {
            if (movie.averageRating() != 0) {
                //filter for year and gen
                if (movieFilter(movie, years, gen) != null) {
                    ratings.put(movie.getTitle(), movie.averageRating());
                }
            }
        }
        //sort Movies
        ratings = sortHashMap(ratings, sortType);
        for (Map.Entry<String, Double> mapElement : ratings.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfMovies && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }

        return finalList;
    }
    /**
     * function for filter movie by year and genre
     */
    public TvShow showFilter(final TvShow show, final List<String> years,
                             final List<String> genre) {
        int y;
        boolean confirmYear = false;
        boolean confirmGen = false;
        //verify if it's same year with filter
        if (years.get(0) != null) {
            for (String year : years) {
                if (year != null) {
                    y = Integer.parseInt(year);
                    if (show.getYear() == y) {
                        confirmYear = true;
                    }
                }
            }
        } else {
            confirmYear = true;
        }

        //verify if it's same gen with filter
        if (genre.get(0) != null) {
            for (String filterGen : genre) {
                for (String gen : show.getGen()) {
                    //if exist one gen in movie genres set confirm to true
                    if (filterGen.equals(gen)) {
                        confirmGen = true;
                    }
                }
            }
        } else {
            confirmGen = true;
        }


        if (confirmGen && confirmYear) {
            return show;
        } else {
            return null;
        }
    }
    /**
     * returns first nrOfShows shows by rating
     * */
    public ArrayList<String> showRating(final int nrOfShows, final String sortType,
                                        final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings = new HashMap<String, Double>();

        //put movies in HashTable
        for (TvShow show : tvShows) {
            if (show.averageRating() != 0) {
                //filter for year and gen
                if (showFilter(show, years, gen) != null) {
                    ratings.put(show.getTitle(), show.averageRating());
                }
            }
        }
        //sort Movies
        ratings = sortHashMap(ratings, sortType);
        for (Map.Entry<String, Double> mapElement : ratings.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfShows && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }
    /**
     * return first nrOfMovies by appearing in favorite list
     * */
    public ArrayList<String> favoriteMovies(final int nrOfMovies, final String sortType,
                                            final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> favAppears = new HashMap<String, Integer>();
        int nrOfAppear = 0;

        for (Movie movie : movies) {
            nrOfAppear = 0;
            // verify filters
            if (movieFilter(movie, years, gen) != null) {
                for (User user : users) {
                    //iterate in favoriteList of every user
                    for (String title : user.getFavoriteVideos()) {
                        if (movie.getTitle().equals(title)) {
                            nrOfAppear++;
                        }
                    }
                }
                //push movie and nr of apparences in HashMap
                if (nrOfAppear != 0) {
                    favAppears.put(movie.getTitle(), nrOfAppear);
                }

            }
        }
        //sort HashMap by sortType criteria
        favAppears = sortHashMapInt(favAppears, sortType);

        for (Map.Entry<String, Integer> mapElement : favAppears.entrySet()) {
            auxList.add(mapElement.getKey());

        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfMovies && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;

    }
    /**
     * return first nrOfShows by appearing in favorite lists
     * */
    public final ArrayList<String> favoriteShows(final int nrOfShows, final String sortType,
                                           final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> favAppears = new HashMap<String, Integer>();
        int nrOfAppear = 0;

        for (TvShow show : tvShows) {
            nrOfAppear = 0;
            // verify filters
            if (showFilter(show, years, gen) != null) {
                for (User user : users) {
                    //iterate in favoriteList of every user
                    for (String title : user.getFavoriteVideos()) {
                        if (show.getTitle().equals(title)) {
                            nrOfAppear++;
                        }
                    }
                }
                //push movie and nr of apparences in HashMap
                if (nrOfAppear != 0) {
                    favAppears.put(show.getTitle(), nrOfAppear);
                }
            }
        }
        //sort HashMap by sortType criteria
        favAppears = sortHashMapInt(favAppears, sortType);

        for (Map.Entry<String, Integer> mapElement : favAppears.entrySet()) {
            auxList.add(mapElement.getKey());
        }

        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfShows && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;

    }

    /**
     * functions for sort movies and tvShow by duration
     */
    public ArrayList<String> longestMovies(final int nrOfMovies, final String sortType,
                                           final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> longest = new HashMap<String, Integer>();

        for (Movie movie : movies) {
            if (movieFilter(movie, years, gen) != null) {
                longest.put(movie.getTitle(), movie.getDuration());
            }
        }
        //sort hashmap
        longest = sortHashMapInt(longest, sortType);

        for (Map.Entry<String, Integer> mapElement : longest.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfMovies && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;

    }
    /**
     * sort shows by duration
     * */
    public final ArrayList<String> longestShows(final int nrOfShows, final String sortType,
                                          final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> longest = new HashMap<String, Integer>();

        for (TvShow show : tvShows) {
            if (showFilter(show, years, gen) != null) {
                if (show.getDuration() != 0) {
                    longest.put(show.getTitle(), show.getDuration());
                }
            }
        }
        //sort hashmap
        longest = sortHashMapInt(longest, sortType);

        for (Map.Entry<String, Integer> mapElement : longest.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfShows && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;

    }

    /**
     * functions for sort movies/tvShows by number of views
     */
    public ArrayList<String> mostViewedMovies(final int nrOfMovies, final String sortType,
                                              final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> mostView = new HashMap<String, Integer>();
        int soumOfView;

        for (Movie movie : movies) {
            soumOfView = 0;
            if (movieFilter(movie, years, gen) != null) {
                for (User user : users) {
                    soumOfView += user.getNrOfView(movie.getTitle());
                }
                if (soumOfView != 0) {
                    mostView.put(movie.getTitle(), soumOfView);
                }
            }
        }
        //sort hashmap
        mostView = sortHashMapInt(mostView, sortType);

        for (Map.Entry<String, Integer> mapElement : mostView.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfMovies && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }
    /**
     * returns a list with shows sorted by
     * */
    public final ArrayList<String> mostViewedShows(final int nrOfShows, final String sortType,
                                             final List<String> years, final List<String> gen) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> mostView = new HashMap<String, Integer>();
        int soumOfView;

        for (TvShow show : tvShows) {
            soumOfView = 0;
            if (showFilter(show, years, gen) != null) {
                for (User user : users) {
                    soumOfView += user.getNrOfView(show.getTitle());
                }
                if (soumOfView != 0) {
                    mostView.put(show.getTitle(), soumOfView);
                }
            }
        }
        //sort hashmap
        mostView = sortHashMapInt(mostView, sortType);

        for (Map.Entry<String, Integer> mapElement : mostView.entrySet()) {
            auxList.add(mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i = 0;
        while (i < nrOfShows && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }
    /**
     * returns a list with users sorted by activity
     * */
    public final ArrayList<String> usersRating(final int nrOfUsers, final String sortType) {
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> ratings = new HashMap<String, Integer>();

        //add user in HashMap
        for (User user : users) {
            if (user.getNrOfRating() != 0) {
                ratings.put(user.getUsername(), user.getNrOfRating());
            }

        }
        //sort HashMap by 2 criteria
        ratings = sortHashMapInt(ratings, sortType);
        // transform Hashmap in a String list
        for (Map.Entry<String, Integer> mapElement : ratings.entrySet()) {
            auxList.add(mapElement.getKey());
        }

        //copy first nrOfUsers elements in list

        int i = 0;
        while (i < nrOfUsers && i < auxList.size()) {
            finalList.add(auxList.get(i));
            i++;
        }

        return finalList;
    }


}
