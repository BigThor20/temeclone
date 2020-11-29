package main;

import actor.Actor;
import entertainment.Movie;
import entertainment.TvShow;
import entertainment.Video;
import users.User;

import java.util.*;


public class Query {
    ArrayList<User> users ;
    ArrayList<Movie> movies;
    ArrayList<TvShow> tvShows;
    ArrayList<Actor> actors;

    public Query(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<TvShow> tvShows, ArrayList<Actor> actors) {
        this.users = users;
        this.movies = movies;
        this.tvShows = tvShows;
        this.actors = actors;
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
     * method for sort a HashMap by 2 criteries
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
    //sort for int
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

    public ArrayList<String> averageActors( int nrOfActors, String sortType){
        ArrayList<String> filmography = new ArrayList<String>();
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> actorsAverage =  new HashMap<String, Double>();
        Movie movie = null;
        TvShow tvShow = null;
        Double soum = 0.0;
        int nr = 0;
        for (Actor actor : actors){
            filmography = actor.getFilmography();
            soum = 0.0;
            nr = 0;

            for (String video : filmography){
                // get video from database
                if (getMovieInDb(video) != null){
                    movie = (Movie) getMovieInDb(video);
                } else {
                    movie = null;
                }
                if (getSerialInDb(video) != null){
                    tvShow = (TvShow) getSerialInDb(video);
                } else {
                    tvShow = null;
                }

                //get average rating for a video
                if (movie != null){
                    if (movie.averageRating() != 0){
                        soum = soum + movie.averageRating();
                        nr++;
                    }
                }
                if (tvShow != null){
                    if (tvShow.averageRating() != 0){
                        soum = soum + tvShow.averageRating();
                        nr++;
                    }
                }
            }
            // calculate average and push in HashMap
            if (nr != 0){
                actorsAverage.put(actor.getName(), soum / nr);
            }
        }

        actorsAverage = sortHashMap(actorsAverage, sortType);

        for (Map.Entry mapElement : actorsAverage.entrySet()){
            auxList.add((String) mapElement.getKey());
        }
        //aici e o problema pt ca unii actori sunt mai multi decat final list

        int i = 0;
        while(i < nrOfActors && i < auxList.size()){
            finalList.add(auxList.get(i));
            i++;
        }
        return finalList;
    }
    /**
     * function for generate all actors with specify awards
     * */
    public ArrayList<String> awardsActors(String sortType, List<String> awards){
        ArrayList<String> finalList = new ArrayList<String>();
        Map<String, Integer> totalAwards =  new HashMap<String, Integer>();
        for (Actor actor : actors){
            if (verifyAwards(actor, awards) != null){
                //put total number of awards in HashMap
                totalAwards.put(actor.getName(), actor.totalNumberOfAwards());
            }
        }
        //sort HashMap by sortType criteria
        totalAwards = sortHashMapInt(totalAwards, sortType);

        for (Map.Entry mapElement : totalAwards.entrySet()){
            finalList.add((String) mapElement.getKey());
        }

        return finalList;
    }
    // function for verify if an actor has all awards from list
    public Actor verifyAwards(Actor actor , List<String> awards){
        boolean hasAwards = false;
        for (String award : awards){
            if (award != null){
                hasAwards = false;
                // search award
                for (Map.Entry mapElement : actor.getAwards().entrySet()){
                    // if i found award set hasAwards to true
                    if (mapElement.getKey().equals(award)){
                        hasAwards = true;
                    }
                }
                // if we don't find an award return null
                if (!hasAwards ){
                    return null;
                }
            }
        }
        // if we find all awards return actor
        if (hasAwards ){
            return actor;
        }
       return null;
    }
    /**
     * function for generate list with actors which contains
     * all keywords in description
     * */
    public ArrayList<String> filterDescription(String sortType, List<String> words){
        ArrayList<String> finalList = new ArrayList<String>();
        Map<String, Integer> correctActors =  new HashMap<String, Integer>();
        for (Actor actor : actors){
            if (verifyDescription(actor,words) != null){
                correctActors.put(actor.getName(), 1);
            }
        }
        //sort HashMap just by alphabetic order, because values are 1(all)
        correctActors = sortHashMapInt(correctActors, sortType);
        //generate final List
        for (Map.Entry mapElement : correctActors.entrySet()){
            finalList.add((String) mapElement.getKey());
        }
        return finalList;
    }

    public Actor verifyDescription(Actor actor, List<String> words){
        //generate an array of word from actor description
        String[] wordsArray = actor.getCareerDescription().split("[^a-zA-Z]+");
        boolean contains = false;
       for (String keyword : words){
           contains = false;
           // search keyword in actorDescription
           for (String word : wordsArray){
               if (keyword.equals(word)){
                   contains = true;
               }
           }
           // if i don't find a keyword in actor desciption return null
           if (contains != true){
               return null;
           }
       }
       if (contains){
           return actor;
       }
        return null;
    }
    /**
     * function for filter movie by year and genre
     * */
    public Movie movieFilter(Movie movie,List<String> years, List<String> genre){
        int y;
        boolean confirm_year = false;
        boolean confirm_gen = false;
        //verify if it's same year with filter
        if (years != null){
            for (String year : years){
                if (year != null){
                    y = Integer.parseInt(year);
                    if (movie.getYear() == y){
                        confirm_year = true;
                    }
                }
            }
        } else {
            confirm_year = true;
        }

        //verify if it's same gen with filter
        if (genre != null){
            for (String filterGen : genre){
                if (filterGen != null){
                    for (String gen : movie.getGen()){
                        //if exist one gen in movie genres set confirm to true
                        if (filterGen.equals(gen)){
                            confirm_gen = true;
                        }
                    }
                }
            }
        } else {
            confirm_gen = true;
        }


        if (confirm_gen && confirm_year){
            return movie;
        } else {
            return null;
        }
    }

    public ArrayList<String> movieRating(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings =  new HashMap<String, Double>();


        //put movies in HashTable
        for (Movie movie : movies){
            if (movie.averageRating() != 0){
                //filter for year and gen
                if (movieFilter(movie,years,gen) != null){
                    ratings.put(movie.getTitle(), movie.averageRating());
                }
            }
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

        System.out.println("final: " + finalList);
        return finalList;
    }
    /**
     * function for filter movie by year and genre
     * */
    public TvShow showFilter(TvShow show,List<String> years, List<String> genre){
        int y;
        boolean confirm_year = false;
        boolean confirm_gen = false;
        //verify if it's same year with filter
        if (years != null){
            for (String year : years){
                if (year != null){
                    y = Integer.parseInt(year);
                    if (show.getYear() == y){
                        confirm_year = true;
                    }
                }
            }
        } else{
            confirm_year = true;
        }

        //verify if it's same gen with filter
        if (genre != null){
            for (String filterGen : genre){
                for (String gen : show.getGen()){
                    //if exist one gen in movie genres set confirm to true
                    if (filterGen.equals(gen)){
                        confirm_gen = true;
                    }
                }
            }
        } else {
            confirm_gen = true;
        }


        if (confirm_gen && confirm_year){
            return show;
        } else {
            return null;
        }
    }
    public ArrayList<String> showRating(int nrOfShows, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Double> ratings =  new HashMap<String, Double>();

        //put movies in HashTable
        for (TvShow show : tvShows){
            if (show.averageRating() != 0){
                //filter for year and gen
                    if (showFilter(show,years,gen) != null){
                        ratings.put(show.getTitle(), show.averageRating());
                    }


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
    public ArrayList<String> favoriteMovies(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> favAppears =  new HashMap<String, Integer>();
        int nrOfAppear = 0;

        for (Movie movie : movies){
            nrOfAppear = 0;
            // verify filters
            if (movieFilter(movie,years,gen) != null){
                for (User user : users){
                    //iterate in favoriteList of every user
                    for(String title : user.getFavoriteVideos()){
                        if (movie.getTitle().equals(title)){
                            nrOfAppear++;
                        }
                    }
                }
                //push movie and nr of apparences in HashMap
                if (nrOfAppear != 0){
                    favAppears.put(movie.getTitle(), nrOfAppear);
                }

            }
        }
        //sort HashMap by sortType criteria
        favAppears = sortHashMapInt(favAppears, sortType);

        for (Map.Entry mapElement : favAppears.entrySet()){
            auxList.add((String) mapElement.getKey());
        }for (Map.Entry mapElement : favAppears.entrySet()){
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

    public ArrayList<String> favoriteShows(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> favAppears =  new HashMap<String, Integer>();
        int nrOfAppear = 0;

        for (TvShow show : tvShows){
            nrOfAppear = 0;
            // verify filters
            if (showFilter(show,years,gen) != null){
                for (User user : users){
                    //iterate in favoriteList of every user
                    for(String title : user.getFavoriteVideos()){
                        if (show.getTitle().equals(title)){
                            nrOfAppear++;
                        }
                    }
                }
                //push movie and nr of apparences in HashMap
                if (nrOfAppear != 0){
                    favAppears.put(show.getTitle(), nrOfAppear);
                }
            }
        }
        //sort HashMap by sortType criteria
        favAppears = sortHashMapInt(favAppears, sortType);

        for (Map.Entry mapElement : favAppears.entrySet()){
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
    /**
     * functions for sort movies and tvShow by duration
     * */
    public ArrayList<String> longestMovies(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> longest =  new HashMap<String, Integer>();

        for (Movie movie : movies){
            if (movieFilter(movie,years,gen) != null){
                longest.put(movie.getTitle(), movie.getDuration());
            }
        }
        //sort hashmap
        longest = sortHashMapInt(longest, sortType);

        for (Map.Entry mapElement : longest.entrySet()){
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

    public ArrayList<String> longestShows(int nrOfShows, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> longest =  new HashMap<String, Integer>();

        for (TvShow show : tvShows){
            if (showFilter(show,years,gen) != null){
                if (show.getDuration() != 0){
                    longest.put(show.getTitle(), show.getDuration());
                }
            }
        }
        //sort hashmap
        longest = sortHashMapInt(longest, sortType);

        for (Map.Entry mapElement : longest.entrySet()){
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
     * functions for sort movies/tvShows by number of views
     * */
    public ArrayList<String> mostViewedMovies(int nrOfMovies, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> mostView =  new HashMap<String, Integer>();
        int soumOfView;

        for (Movie movie : movies){
            soumOfView = 0;
            if (movieFilter(movie,years,gen) != null){
                for (User user : users){
                    soumOfView += user.getNrOfView(movie.getTitle());
                }
                if (soumOfView != 0){
                    mostView.put(movie.getTitle(), soumOfView);
                }
            }
        }
        //sort hashmap
        mostView = sortHashMapInt(mostView, sortType);

        for (Map.Entry mapElement : mostView.entrySet()){
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
    public ArrayList<String> mostViewedShows(int nrOfShows, String sortType, List<String> years, List<String> gen){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> mostView =  new HashMap<String, Integer>();
        int soumOfView;

        for (TvShow show : tvShows){
            soumOfView = 0;
            if (showFilter(show,years,gen) != null){
                for (User user : users){
                    soumOfView += user.getNrOfView(show.getTitle());
                }
                if (soumOfView != 0){
                    mostView.put(show.getTitle(), soumOfView);
                }
            }
        }
        //sort hashmap
        mostView = sortHashMapInt(mostView, sortType);

        for (Map.Entry mapElement : mostView.entrySet()){
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

    public ArrayList<String> usersRating(int nrOfUsers, String sortType){
        ArrayList<String> finalList = new ArrayList<String>();
        ArrayList<String> auxList = new ArrayList<String>();
        Map<String, Integer> ratings =  new HashMap<String, Integer>();

        //add user in HashMap
        for (User user : users){
            if (user.getNrOfRating() != 0){
                ratings.put(user.getUsername(), user.getNrOfRating());
            }

        }
        //sort HashMap by 2 criteria
        ratings = sortHashMapInt(ratings, sortType);
        // transform Hashmap in a String list
        for (Map.Entry mapElement : ratings.entrySet()){
            auxList.add((String) mapElement.getKey());
        }

        //copy first nrOfUsers elements in list

        int i =0;
        while(i < nrOfUsers && i < auxList.size()){
            finalList.add(auxList.get(i));
            i++;
        }

        return finalList;
    }




}
