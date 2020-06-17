package nl.terrax.tbrestmongodb.controller;

import nl.terrax.tbrestmongodb.domain.Beer;
import nl.terrax.tbrestmongodb.service.BeerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/beers/1.0")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @Get()
    @Produces(MediaType.APPLICATION_JSON)
    public List<Beer> getAllBeers() {
        return beerService.findAll();
    }

    @Get("/{beerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Beer getBeerByName(@PathVariable("beerName") String name) {
        return beerService.findByName(name);
    }

    @Post()
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> saveOrUpdateBeer(@Body Beer beer) {
        beerService.saveBeer(beer);
        return HttpResponse.ok("Beer added successfully");
    }

    @Delete("/{beerName}")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> deleteBeer(@PathVariable("beerName") String name) {
        Beer beer = beerService.findByName(name);
        beerService.deleteBeer(beerService.findByName(name).getId());
        return HttpResponse.ok("Beer deleted successfully");
    }
}