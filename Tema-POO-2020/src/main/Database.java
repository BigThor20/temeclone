package main;

import actor.Actor;
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
    ArrayList<Actor> actors;

    public Database(Input input) {
        this.input = input;
        users = new ArrayList<>();
        movies = new ArrayList<>();
        tvShows = new ArrayList<>();
        actors = new ArrayList<>();

    }
    public void init(Writer file, JSONArray arr) throws IOException {
        this.fileWriter = file;
        this.arrayResult = arr;
        addMovieToBd();
        addSerialToBd();
        addUsersToBd();
        addActorsToBd();
        commands();
        queries();
    }
    /**
     * add videos, users and actors in bd
     * */
    public void addMovieToBd(){
        for (MovieInputData data : input.getMovies()){
            Movie video = new Movie(data.getTitle(), data.getYear(), data.getGenres(), data.getDuration());
            movies.add(video);
        }
    }

    public void addSerialToBd(){
        for (SerialInputData data : input.getSerials()){
            TvShow show = new TvShow(data.getTitle(), data.getYear(), data.getGenres(), data.getSeasons(), data.getNumberSeason());
            tvShows.add(show);
        }
    }
    public void addUsersToBd(){
        for (UserInputData data : input.getUsers()){
            User user = new User(data.getUsername(), data.getSubscriptionType(),
                    data.getFavoriteMovies(), data.getHistory());
            users.add(user);
        }
    }
    public void addActorsToBd(){
        for (ActorInputData data : input.getActors()){
            Actor actor = new Actor(data.getName(), data.getCareerDescription(),
                    data.getFilmography(), data.getAwards());
            actors.add(actor);
        }
    }
    /**
     * function for commands: favorite, view, rating
     * */
    public void commands() throws IOException {
            Commands command = new Commands(users, movies, tvShows);
            int nrOfViews;
            int favList;
            int rating;
            for (ActionInputData data : input.getCommands()) {
                /**
                 * add movie / tvShow to favoriteList / viewedList
                 * */
                    if (data.getActionType().equals("command") && data.getType().equals("favorite")) {
                        favList = command.addToFavorite(data.getUsername(), data.getTitle());
                        if (favList == 1){
                            arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                    "success -> " + data.getTitle() + " was added as favourite"));
                        } else if(favList == 2){
                            arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                    "error -> " + data.getTitle() + " is not seen"));
                        } else if (favList == 3){
                            arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                    "error -> " + data.getTitle() + " is already in favourite list"));
                        }
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
                     rating = command.rating(data.getUsername(), data.getTitle(), data.getGrade(), data.getSeasonNumber());

                     if (rating == 1){
                         arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                 "success -> " + data.getTitle() + " was rated with " + data.getGrade() + " by "
                                         + data.getUsername()));
                     } else if (rating == 2){
                         arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "error -> " +
                                 data.getTitle() + " is not seen"));
                     } else if (rating == 3){
                         arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "error -> " +
                                 data.getTitle() + " has been already rated"));
                     }


                }
            }

    }

    public void queries() throws IOException {
        Query query = new Query(users, movies, tvShows, actors);
        ArrayList<String> resultList;


        for (ActionInputData data : input.getCommands()){
            if (data.getActionType().equals("query") && data.getObjectType().equals("actors") &&
            data.getCriteria().equals("average")){
                resultList = query.averageActors(data.getNumber(), data.getSortType());
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList ));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("actors") &&
                    data.getCriteria().equals("awards")){
                resultList = query.awardsActors(data.getSortType(), data.getFilters().get(3));;
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList ));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("users")){
                resultList = query.usersRating(data.getNumber(), data.getSortType());
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));

            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("movies") &&
                    data.getCriteria().equals("ratings")){
                resultList = query.movieRating(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("shows") &&
                    data.getCriteria().equals("ratings")){
                resultList = query.showRating(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("movies") &&
                    data.getCriteria().equals("favorite")){
                resultList = query.favoriteMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("shows") &&
                    data.getCriteria().equals("favorite")){
                resultList = query.favoriteShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("movies") &&
                    data.getCriteria().equals("longest")){
                resultList = query.longestMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("shows") &&
                    data.getCriteria().equals("longest")){
                resultList = query.longestShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("movies") &&
                    data.getCriteria().equals("most_viewed")){
                resultList = query.mostViewedMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
            if (data.getActionType().equals("query") && data.getObjectType().equals("shows") &&
                    data.getCriteria().equals("most_viewed")){
                resultList = query.mostViewedShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                        data.getFilters().get(1));
                arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
            }
        }
    }




}
