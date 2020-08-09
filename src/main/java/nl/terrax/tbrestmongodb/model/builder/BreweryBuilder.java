package nl.terrax.tbrestmongodb.model.builder;

import nl.terrax.tbrestmongodb.model.Brewery;

public class BreweryBuilder {
    private String name;
    private String country;

    public BreweryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BreweryBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public Brewery createBrewery() {
        return new Brewery(name, country);
    }
}