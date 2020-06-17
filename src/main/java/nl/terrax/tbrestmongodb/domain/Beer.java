package nl.terrax.tbrestmongodb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

public class Beer {

    @JsonIgnore
    private ObjectId id;
    private String name;
    private Brewery brewery;

    public Beer() {
    }

    public Beer(ObjectId id, String name, Brewery brewery) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }
}
