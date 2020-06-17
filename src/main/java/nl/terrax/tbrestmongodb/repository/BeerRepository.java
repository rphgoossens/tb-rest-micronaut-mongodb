package nl.terrax.tbrestmongodb.repository;

import nl.terrax.tbrestmongodb.domain.Beer;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BeerRepository {

    Single<Beer> create(@NotNull Beer beer);

    Single<List<Beer>> findAll();

    Maybe<Beer> find(@NotEmpty String name);

    Single<Beer> update(@NotNull Beer beer);

    Maybe<Beer> delete(@NotNull ObjectId id);

}
