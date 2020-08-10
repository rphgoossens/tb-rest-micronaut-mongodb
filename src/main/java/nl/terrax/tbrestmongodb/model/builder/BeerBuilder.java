package nl.terrax.tbrestmongodb.model.builder;


import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.model.Brewery;
import org.bson.types.ObjectId;

import java.util.Objects;

public class BeerBuilder {
    private String id;
    private String name;
    private Brewery brewery;

    public BeerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public BeerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BeerBuilder setBrewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public Beer createBeer() {
        if (Objects.isNull(id)) {
            return new Beer(null, name, brewery);
        } else {
            return new Beer(new ObjectId(id), name, brewery);
        }
    }
}