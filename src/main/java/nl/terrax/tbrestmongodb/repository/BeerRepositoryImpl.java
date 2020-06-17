package nl.terrax.tbrestmongodb.repository;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import nl.terrax.tbrestmongodb.domain.Beer;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class BeerRepositoryImpl implements BeerRepository {

    private final MongoClient mongoClient;

    BeerRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<Beer> create(@NotNull Beer beer) {
        Single<Beer> map = Single.fromPublisher(
                getCollection().insertOne(beer)
        ).map(success -> beer);

        return map;
    }

    @Override
    public Single<List<Beer>> findAll() {
        return Flowable.fromPublisher(
                getCollection().find()
        ).toList();
    }

    @Override
    public Maybe<Beer> find(@NotEmpty String name) {
        return Flowable.fromPublisher(
                getCollection()
                        .find(eq("name", name))
                        .limit(1)
        ).firstElement();
    }

    @Override
    public Single<Beer> update(@NotNull Beer beer) {
        return Single.fromPublisher(
                getCollection().findOneAndReplace(eq("id", beer.getId()), beer)
        ).map(success -> beer);

    }

    @Override
    public Maybe<Beer> delete(ObjectId id) {
        return Flowable.fromPublisher(
                getCollection()
                        .findOneAndDelete(eq("_id", id))
        ).firstElement();
    }

    private MongoCollection<Beer> getCollection() {
        return mongoClient
                .getDatabase("terraxbeer")
                .getCollection("beers", Beer.class);
    }

}
