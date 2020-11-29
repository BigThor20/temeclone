package main;

import entertainment.Movie;
import entertainment.TvShow;
import users.User;

import java.util.*;

public class Recommandation {
    ArrayList<User> users;
    ArrayList<Movie> movies;
    ArrayList<TvShow> tvShows;


    public Recommandation(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<TvShow> shows) {
        this.users = users;
        this.movies = movies;
        this.tvShows = shows;
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
    public Movie getMovieInDb(String title){
        for (Movie i : movies){
            if (i.getTitle().equals(title)){
                return i;
            }
        }
        return null;
    }
    public TvShow getSerialInDb(String title){
        for (TvShow i : tvShows){
            if(i.getTitle().equals(title)){
                return i;
            }
        }
        return null;
    }

    /**
     * return first video that don't appears in user's viewed list
     * */
    public String standard(String username){
        User user = null;
        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        //search movies in viewed list
        if (user != null){
            //verify if first video is movie
            for (Movie movie : movies){
                if (!user.inViewedList(movie.getTitle())){
                    return movie.getTitle();
                }
            }
            // verify if first video is serial
            for (TvShow show : tvShows){
                if (!user.inViewedList(show.getTitle())){
                    return show.getTitle();
                }
            }
        }
        return null;
    }
    /**
     * method that return  best unseen video
     * */
    public String bestUnseen(String username){
        User user = null;
        ArrayList<String> listOfMovies;
        ArrayList<String> listOfShows;
        int nrOfMovies = 0;
        int nrOfShows = 0;

        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        for (Movie i : movies){
            nrOfMovies++;
        }
        for (TvShow show : tvShows){
            nrOfShows++;
        }
        //create list from History
        if (user != null){
            // use querry.rating for descendent order list
            listOfMovies = movieRating(nrOfMovies, "desc", null, null);
            for (String movie : listOfMovies){
                if (!user.inViewedList(movie)){
                    return movie;
                }
            }
            //use querry rating for descendent order list
            listOfShows = showRating(nrOfShows, "desc", null, null);
            for (String show : listOfShows){
                if (!user.inViewedList(show)){
                    return show;
                }
            }
        }
        return null;
    }
    /**
     * method for generate best videos
     * */
    public ArrayList<String> movieRating(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings =  new HashMap<String, Double>();

        //put movies in HashTable
        for (Movie movie : movies){
                //filter for year and gen
                    ratings.put(movie.getTitle(), movie.averageRating());
        }
        //sort Movies
        ratings = sortHashMap(ratings, sortType);
        for (Map.Entry mapElement : ratings.entrySet()){
            auxList.add((String) mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i =0;
        while(i < nrOfMovies && i < auxList.size()){
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }

    public ArrayList<String> showRating(int nrOfShows, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings =  new HashMap<String, Double>();

        //put movies in HashTable
        for (TvShow show : tvShows){
            if (show.averageRating() != 0){
                //filter for year and gen
                    ratings.put(show.getTitle(), show.averageRating());
            }
        }
        //sort Movies
        ratings = sortHashMap(ratings, sortType);
        for (Map.Entry mapElement : ratings.entrySet()){
            auxList.add((String) mapElement.getKey());
        }
        // push in list just first nrOfMovies movies
        int i =0;
        while(i < nrOfShows && i < auxList.size()){
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }
    /**
     * method for sort a HashMap with comparator
     * */
    private static Map<String, Double> sortHashMap(Map<String, Double> unsortMap, String sortType) {
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> obj1,
                               Map.Entry<String, Double> obj2) {
                if (! obj1.getValue().equals(obj2.getValue())){
                    if (sortType.equals("desc")){
                        return (obj2.getValue()).compareTo(obj1.getValue());
                    }

                    return (obj1.getValue().compareTo(obj2.getValue()));
                } else {
                    if (sortType.equals("desc")){
                        return (obj2.getKey().compareTo(obj1.getKey()));
                    }
                    return (obj1.getKey().compareTo(obj2.getKey()));
                }

            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    private static Map<String, Integer> sortHashMapInt(Map<String, Integer> unsortMap, String sortType) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1,
                               Map.Entry<String, Integer> obj2) {
                if (! obj1.getValue().equals(obj2.getValue())){
                    if (sortType.equals("desc")){
                        return (obj2.getValue()).compareTo(obj1.getValue());
                    }

                    return (obj1.getValue().compareTo(obj2.getValue()));
                } else {
                    if (sortType.equals("desc")){
                        return (obj2.getKey().compareTo(obj1.getKey()));
                    }
                    return (obj1.getKey().compareTo(obj2.getKey()));
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
     * Method for popular videos
     * */
    public String popularVideo(String username){
        User user = null;
        ArrayList<String> genres = new ArrayList<String>();
        Map<String, Integer> gens =  new HashMap<String, Integer>();
        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        if (user != null){
            if (user.getType().equals("PREMIUM")){
                //create a list with most popular gens
                if (getPopularGen() != null){
                    gens = getPopularGen();
                    for (Map.Entry i : gens.entrySet()){
                        genres.add((String) i.getKey());
                    }
                }
                // test if exist a video from most popular gen unviewed
                for (String gen : genres){
                    for (Movie movie : movies){
                        if (containGen(movie, gen)){
                            //verify if video is not viewed
                            if (!user.inViewedList(movie.getTitle())){
                                return movie.getTitle();
                            }
                        }
                    }
                    for (TvShow show : tvShows){
                        if (containGen(show, gen)){
                            if (!user.inViewedList(show.getTitle())){
                                return show.getTitle();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    //that method return a Map with most popular genres
    public Map<String, Integer> getPopularGen(){
        Map<String, Integer> popular = new Hashtable<String, Integer>();
        for (Movie movie : movies){
            for (String gen : movie.getGen()){
                if (!inHashMap(popular, gen)){
                    // if it's not in map, put in
                    popular.put(gen, nrOfView(movie));
                } else {
                    //if it's already in map increment value
                    popular.put(gen,popular.get(gen) + nrOfView(movie));
                }
            }
        }
        for (TvShow show : tvShows){
            for (String gen : show.getGen()){
                if (!inHashMap(popular, gen)){
                    // if it's not in map, put in
                    popular.put(gen, nrOfView(show));
                } else {
                    //if it's already in map increment value
                    popular.put(gen,popular.get(gen) + nrOfView(show));
                }
            }
        }

        popular = sortHashMapInt(popular,"desc");
        return popular;
    }
    //that method return true if a key exist in Map
    public boolean inHashMap(Map<String, Integer> map, String key){
        if (map != null){
            for (Map.Entry mapElement : map.entrySet()){
                if (mapElement.getKey().equals(key)){
                    return  true;
                }
            }
        }
        return false;
    }
    //that methods return number of a view for a video
    public int nrOfView(Movie movie){
        int nr = 0;
        for (User user : users){
            nr += user.getNrOfView(movie.getTitle());
        }
        return nr;
    }
    public int nrOfView(TvShow show){
        int nr = 0;
        for (User user : users){
            nr += user.getNrOfView(show.getTitle());
        }
        return nr;
    }
    //that method verify if a movie contains gen
    public boolean containGen(Movie movie, String gen){
        for (String str : movie.getGen()){
            if (str.equals(gen)){
                return true;
            }
        }
        return false;
    }
    public boolean containGen(TvShow show, String gen){
        for (String str : show.getGen()){
            if (str.equals(gen)){
                return true;
            }
        }
        return false;
    }
    /**
     * function for favorite video
     * */
    public String favoriteVideo(String username){
        User user = null;
        String mostFavorite = null;
        int mostApparences = 0;
        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        if (user != null){
            if (user.getType().equals("PREMIUM")){
                for (Movie movie : movies){
                    //verify if it's not viewed
                    if (!user.inViewedList(movie.getTitle())){
                        if (nrOfAppInFav(movie.getTitle()) > mostApparences){
                            mostApparences = nrOfAppInFav(movie.getTitle());
                            mostFavorite = movie.getTitle();
                        }
                    }
                }
                for (TvShow show : tvShows){
                    //verify if it's not viewed
                    if (!user.inViewedList(show.getTitle())){
                        if (nrOfAppInFav(show.getTitle()) > mostApparences){
                            mostApparences = nrOfAppInFav(show.getTitle());
                            mostFavorite = show.getTitle();
                        }
                    }
                }
            }
        }
        return mostFavorite;
    }
    //that method return nr of apparences in favorite list of users
    public int nrOfAppInFav(String title){
        int nr = 0;
            for (User user : users){
                for (String video : user.getFavoriteVideos()){
                    if (title.equals(video)){
                        nr++;
                    }
                }
            }
        return  nr;
    }
    /**
     * method for search all videos of a certain genre
     * */
    public ArrayList<String> searchVideos(String username, String gen){
        User user = null;
        ArrayList<String> finalList = new ArrayList<String>();
        Map<String, Double> allVideos = new HashMap<String, Double>();
        if (getUserInDb(username) != null){
            user = getUserInDb(username);
        }
        if (user != null){
            if (user.getType().equals("PREMIUM")){
                for (Movie movie : movies){
                    //verify if it's not viewed
                    if (!user.inViewedList(movie.getTitle())){
                        //verify genre
                        if (containGen(movie, gen)){
                            allVideos.put(movie.getTitle(), movie.averageRating());
                        }
                    }
                }
                for (TvShow show : tvShows){
                    //verify if it's not viewed
                    if (!user.inViewedList(show.getTitle())){
                        //verify genre
                        if ((containGen(show, gen))){
                            allVideos.put(show.getTitle(), show.averageRating());
                        }
                    }
                }
                allVideos = sortHashMap(allVideos, "asc");

                //generate list
                for (Map.Entry mapElement : allVideos.entrySet()){
                    finalList.add((String) mapElement.getKey());
                }
            }
        }
        return finalList;
    }


}
