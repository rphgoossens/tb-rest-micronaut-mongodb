package nl.terrax.tbrestmongodb.service;

import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.repository.BeerRepository;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Singleton
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    public BeerServiceImpl (BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll().blockingGet();
    }

    @Override
    public Beer findByName(@NotEmpty String name) {
        return beerRepository.find(name).blockingGet();
    }

    @Override
    public void saveBeer(@NotNull Beer beer) {
        beerRepository.create(beer).blockingGet();
    }

    @Override
    public void updateBeer(@NotNull Beer beer) {
        beerRepository.update(beer).blockingGet();
    }

    @Override
    public void deleteBeer(@NotEmpty ObjectId id) {
        beerRepository.delete(id).blockingGet();
    }

}
