package main;

import actor.Actor;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import users.User;
import entertainment.*;

import java.io.IOException;
import java.util.ArrayList;


public class Database {
    Input input;
    Writer fileWriter;
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
        actions();

    }

    /**
     * add videos, users and actors in bd
     */
    public void addMovieToBd() {
        for (MovieInputData data : input.getMovies()) {
            Movie video = new Movie(data.getTitle(), data.getYear(), data.getGenres(), data.getDuration());
            movies.add(video);
        }
    }

    public void addSerialToBd() {
        for (SerialInputData data : input.getSerials()) {
            TvShow show = new TvShow(data.getTitle(), data.getYear(), data.getGenres(), data.getSeasons(), data.getNumberSeason());
            tvShows.add(show);
        }
    }

    public void addUsersToBd() {
        for (UserInputData data : input.getUsers()) {
            User user = new User(data.getUsername(), data.getSubscriptionType(),
                    data.getFavoriteMovies(), data.getHistory());
            users.add(user);
        }
    }

    public void addActorsToBd() {
        for (ActorInputData data : input.getActors()) {
            Actor actor = new Actor(data.getName(), data.getCareerDescription(),
                    data.getFilmography(), data.getAwards());
            actors.add(actor);
        }
    }

    /**
     * function for commands: favorite, view, rating
     */
    public void actions() throws IOException {
        Commands command = new Commands(users, movies, tvShows);
        int nrOfViews;
        int favList;
        int rating;
       

        Query query = new Query(users, movies, tvShows, actors);
        //ArrayList<String> resultList;

        String result;
        ArrayList<String> resultList = new ArrayList<String>();
        Recommandation recommand = new Recommandation(users, movies, tvShows);


        for (ActionInputData data : input.getCommands()) {
            if (data.getActionType().equals(Constants.COMMAND)) {
                if (data.getType().equals(Constants.FAVORITE)) {
                    favList = command.addToFavorite(data.getUsername(), data.getTitle());
                    if (favList == 1) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "success -> " + data.getTitle() + " was added as favourite"));
                    } else if (favList == 2) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "error -> " + data.getTitle() + " is not seen"));
                    } else if (favList == 3) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "error -> " + data.getTitle() + " is already in favourite list"));
                    }
                } else if (data.getType().equals(Constants.VIEW)) {
                    command.addToViewedList(data.getUsername(), data.getTitle());
                    nrOfViews = command.getNrOfViews(data.getUsername(), data.getTitle());
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                            "success -> " + data.getTitle() + " was viewed with total views of "
                                    + nrOfViews));
                } else if (data.getType().equals(Constants.RATING)) {
                    rating = command.rating(data.getUsername(), data.getTitle(), data.getGrade(), data.getSeasonNumber());
                    if (rating == 1) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?",
                                "success -> " + data.getTitle() + " was rated with " + data.getGrade() + " by "
                                        + data.getUsername()));
                    } else if (rating == 2) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "error -> " +
                                data.getTitle() + " is not seen"));
                    } else if (rating == 3) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "error -> " +
                                data.getTitle() + " has been already rated"));
                    }
                }

            } else if (data.getActionType().equals(Constants.QUERY)) {
                if (data.getObjectType().equals(Constants.ACTORS) && data.getCriteria().equals(Constants.AVERAGE)) {
                    resultList = query.averageActors(data.getNumber(), data.getSortType());
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.ACTORS) && data.getCriteria().equals(Constants.AWARDS)) {
                    resultList = query.awardsActors(data.getSortType(), data.getFilters().get(3));
                    ;
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.ACTORS) && data.getCriteria().equals(Constants.FILTER_DESCRIPTIONS)) {
                    resultList = query.filterDescription(data.getSortType(), data.getFilters().get(2));
                    ;
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.USERS)) {
                    resultList = query.usersRating(data.getNumber(), data.getSortType());
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));

                } else if (data.getObjectType().equals(Constants.MOVIES) && data.getCriteria().equals(Constants.RATINGS)) {
                    resultList = query.movieRating(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.SHOWS) && data.getCriteria().equals(Constants.RATINGS)) {
                    resultList = query.showRating(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.MOVIES) && data.getCriteria().equals(Constants.FAVORITE)) {
                    resultList = query.favoriteMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.SHOWS) && data.getCriteria().equals(Constants.FAVORITE)) {
                    resultList = query.favoriteShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.MOVIES) && data.getCriteria().equals(Constants.LONGEST)) {
                    resultList = query.longestMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.SHOWS) && data.getCriteria().equals(Constants.LONGEST)) {
                    resultList = query.longestShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.MOVIES) && data.getCriteria().equals(Constants.MOST_VIEWED)) {
                    resultList = query.mostViewedMovies(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                } else if (data.getObjectType().equals(Constants.SHOWS) && data.getCriteria().equals(Constants.MOST_VIEWED)) {
                    resultList = query.mostViewedShows(data.getNumber(), data.getSortType(), data.getFilters().get(0),
                            data.getFilters().get(1));
                    arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "Query result: " + resultList));
                }

            } else if (data.getActionType().equals(Constants.RECOMMENDATION)) {
                if (data.getType().equals(Constants.STANDARD)) {
                    result = recommand.standard(data.getUsername());
                    if (result != null) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "StandardRecommendation result: " + result));
                    } else {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "StandardRecommendation cannot be applied!"));
                    }
                } else if (data.getType().equals(Constants.BEST_UNSEEN)) {
                    result = recommand.bestUnseen(data.getUsername());
                    if (result != null) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "BestRatedUnseenRecommendation result: " + result));
                    } else {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "BestRatedUnseenRecommendation cannot be applied!"));
                    }
                } else if (data.getType().equals(Constants.POPULAR)) {
                    result = recommand.popularVideo(data.getUsername());
                    if (result != null) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "PopularRecommendation result: " + result));
                    } else {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "PopularRecommendation cannot be applied!"));
                    }

                } else if (data.getType().equals(Constants.FAVORITE)) {
                    result = recommand.favoriteVideo(data.getUsername());
                    if (result != null) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "FavoriteRecommendation result: " + result));
                    } else {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "FavoriteRecommendation cannot be applied!"));
                    }

                } else if (data.getType().equals(Constants.SEARCH)) {
                    resultList = recommand.searchVideos(data.getUsername(), data.getGenre());
                    if (resultList.size() != 0) {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "SearchRecommendation result: " + resultList));
                    } else {
                        arrayResult.add(fileWriter.writeFile(data.getActionId(), "?", "SearchRecommendation cannot be applied!"));
                    }
                }
            }

        }

    }


}
