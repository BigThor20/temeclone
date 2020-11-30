package actor;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription, final ArrayList<String>
            filmography, final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;

    }

    /**
     * getters and setters for elements
     */
    public final  String getName() {
        return name;
    }

    public final String getCareerDescription() {
        return careerDescription;
    }

    public final ArrayList<String> getFilmography() {
        return filmography;
    }

    public final Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * function for calculate total number of awards
     * */
    public final int totalNumberOfAwards() {
        int nr = 0;
        for (Map.Entry<ActorsAwards, Integer> mapElement : awards.entrySet()) {
            nr = nr + mapElement.getValue();
        }
        return nr;
    }
    /**
     * function that return true if actor has an award
     * */
    public final boolean hasAward(final String award) {

        for (Map.Entry<ActorsAwards, Integer> mapElement : awards.entrySet()) {
            if (mapElement.getKey().toString().equals(award)) {
                return true;
            }
        }
        return false;
    }
}
