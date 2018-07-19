package wars.star.com.starwars.service.model;

import java.util.ArrayList;

/**
 * Created by T183 on 09-Jul-18.
 */

public class StarWarsCharacters {

    public String name;
    public String height;
    public String mass;
    public String created;
    public int count;
    private ArrayList<StarWarsCharacters> results = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }



    public ArrayList<StarWarsCharacters> getResults() {
        return results;
    }



    public String getMass() {
        return mass;
    }


    public String getCreated() {
        return created;
    }

    public int getCount() {
        return count;
    }
}
