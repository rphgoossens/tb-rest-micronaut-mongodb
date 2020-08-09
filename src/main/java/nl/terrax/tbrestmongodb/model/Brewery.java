package nl.terrax.tbrestmongodb.model;

@SuppressWarnings("unused")
public class Brewery {
    private String name;
    private String country;

    public Brewery() {
    }

    public Brewery(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
