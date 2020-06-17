package nl.terrax.tbrestmongodb.service;

import nl.terrax.tbrestmongodb.domain.Beer;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BeerService {
    List<Beer> findAll();

    Beer findByName(@NotEmpty String name);

    void saveBeer(@NotNull Beer beer);

    void updateBeer(@NotNull Beer beer);

    void deleteBeer(@NotEmpty ObjectId id);

}
