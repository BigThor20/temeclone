package actor;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;

    public Actor(String name, String careerDescription, ArrayList<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;

    }

    /**
     * getters and setters for elements
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public int totalNumberOfAwards() {
        int nr = 0;
        for (Map.Entry mapElement : awards.entrySet()) {
            nr = nr + (int) mapElement.getValue();
        }
        return nr;
    }

    public boolean hasAward(String award) {

        for (Map.Entry mapElement : awards.entrySet()) {
            if (mapElement.getKey().toString().equals(award)) {
                return true;
            }
        }
        return false;
    }
}
