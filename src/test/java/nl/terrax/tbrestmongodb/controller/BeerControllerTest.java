package nl.terrax.tbrestmongodb.controller;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import nl.terrax.tbrestmongodb.model.Beer;
import nl.terrax.tbrestmongodb.model.Brewery;
import nl.terrax.tbrestmongodb.model.builder.BeerBuilder;
import nl.terrax.tbrestmongodb.model.builder.BreweryBuilder;
import nl.terrax.tbrestmongodb.service.BeerService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.micronaut.http.HttpRequest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@MicronautTest
class BeerControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    private BeerService beerServiceMock;

    @Test
    void getAllBeers() {
        final Beer beerA = newTestBeerWithId("54651022bffebc03098b4567", "Terra10 Gold", "Terrax Micro-Brewery Inc.", "The Netherlands");
        final Beer beerB = newTestBeerWithId("54651022bffebc03098b4568", "Terra10 Blond", "Terrax Micro-Brewery Inc.", "The Netherlands");
        final List<Beer> allBeers = Arrays.asList(beerA, beerB);

        when(beerServiceMock.findAll()).thenReturn(allBeers);

        HttpResponse<String> response = client.exchange(GET("/beers/1.0"), String.class).blockingFirst();

        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getContentType().isPresent());
        assertEquals(MediaType.APPLICATION_JSON, response.getContentType().get().toString());

        assertTrue(response.getBody().isPresent());
        String result = response.getBody().get();
        assertEquals(2, (int) JsonPath.read(result, "$.length()"));
        assertThrows(PathNotFoundException.class, () -> JsonPath.read(result, "$[0].id"));
        assertEquals("Terra10 Gold", JsonPath.read(result, "$[0].name"));
        assertEquals("Terrax Micro-Brewery Inc.", JsonPath.read(result, "$[0].brewery.name"));
        assertEquals("The Netherlands", JsonPath.read(result, "$[0].brewery.country"));
        assertThrows(PathNotFoundException.class, () -> JsonPath.read(result, "$[1].id"));
        assertEquals("Terra10 Blond", JsonPath.read(result, "$[1].name"));
        assertEquals("Terrax Micro-Brewery Inc.", JsonPath.read(result, "$[1].brewery.name"));
        assertEquals("The Netherlands", JsonPath.read(result, "$[1].brewery.country"));

        verify(beerServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(beerServiceMock);
    }

    @Test
    void getBeerByName() {
        final String beerId = "54651022bffebc03098b4567";
        final String beerName = "Terra10 Gold";
        final String breweryName = "Terrax Micro-Brewery Inc.";
        final String breweryCountry = "The Netherlands";
        final Beer oneBeer = newTestBeerWithId(beerId, beerName, breweryName, breweryCountry);

        when(beerServiceMock.findByName(eq(beerName))).thenReturn(oneBeer);

        String uri = UriBuilder.of("/beers/1.0/{beerName}").expand(Collections.singletonMap("beerName", beerName)).toString();
        HttpResponse<String> response = client.exchange(GET(uri), String.class).blockingFirst();

        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getContentType().isPresent());
        assertEquals(MediaType.APPLICATION_JSON, response.getContentType().get().toString());

        assertTrue(response.getBody().isPresent());
        String result = response.getBody().get();
        assertThrows(PathNotFoundException.class, () -> JsonPath.read(result, "$.id"));
        assertEquals("Terra10 Gold", JsonPath.read(result, "$.name"));
        assertEquals("Terrax Micro-Brewery Inc.", JsonPath.read(result, "$.brewery.name"));
        assertEquals("The Netherlands", JsonPath.read(result, "$.brewery.country"));

        verify(beerServiceMock, times(1)).findByName(eq(beerName));
        verifyNoMoreInteractions(beerServiceMock);
    }

    @Test
    void saveOrUpdateBeer() {
        final String beerName = "Terra10 Gold";
        final String breweryName = "Terrax Micro-Brewery Inc.";
        final String breweryCountry = "The Netherlands";
        final Beer beer = newTestBeer(beerName, breweryName, breweryCountry);

        final HttpResponse<String> response = client.exchange(POST("/beers/1.0", beer), String.class).blockingFirst();

        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getContentType().isPresent());
        assertEquals(MediaType.TEXT_PLAIN, response.getContentType().get().toString());

        assertTrue(response.getBody().isPresent());
        String result = response.getBody().get();
        assertEquals("Beer added successfully", result);

        final ArgumentCaptor<Beer> beerArgumentCaptor = ArgumentCaptor.forClass(Beer.class);
        verify(beerServiceMock, times(1)).saveBeer(beerArgumentCaptor.capture());
        verifyNoMoreInteractions(beerServiceMock);

        final Beer beerArgument = beerArgumentCaptor.getValue();
        assertNull(beerArgument.getId());
        assertEquals(beerName, beerArgument.getName());
        assertEquals(breweryName, beerArgument.getBrewery().getName());
        assertEquals(breweryCountry, beerArgument.getBrewery().getCountry());
    }

    @Test
    void deleteBeer() {
        final Beer beerMock = mock(Beer.class);
        final String beerName = "Terra10 Lager";

        when(beerMock.getId()).thenReturn(new ObjectId("54651022bffebc03098b4567"));
        when(beerServiceMock.findByName(eq(beerName))).thenReturn(beerMock);

        String uri = UriBuilder.of("/beers/1.0/{beerName}").expand(Collections.singletonMap("beerName", beerName)).toString();
        HttpResponse<String> response = client.exchange(DELETE(uri, beerName), String.class).blockingFirst();

        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getContentType().isPresent());
        assertEquals(MediaType.TEXT_PLAIN, response.getContentType().get().toString());

        assertTrue(response.getBody().isPresent());
        String result = response.getBody().get();
        assertEquals("Beer deleted successfully", result);

        verify(beerServiceMock, times(1)).findByName(eq(beerName));
        verify(beerServiceMock, times(1)).deleteBeer(eq(new ObjectId("54651022bffebc03098b4567")));
        verifyNoMoreInteractions(beerServiceMock);
    }


    @MockBean(BeerService.class)
    BeerService mockBeerService() {
        return mock(BeerService.class);
    }

    @SuppressWarnings("SameParameterValue")
    private Beer newTestBeer(String name, String breweryName, String breweryCountry) {
        Brewery brewery = new BreweryBuilder()
                .setName(breweryName)
                .setCountry(breweryCountry)
                .createBrewery();
        return new BeerBuilder()
                .setName(name)
                .setBrewery(brewery)
                .createBeer();
    }

    @SuppressWarnings("SameParameterValue")
    private Beer newTestBeerWithId(String id, String name, String breweryName, String breweryCountry) {
        Brewery brewery = new BreweryBuilder()
                .setName(breweryName)
                .setCountry(breweryCountry)
                .createBrewery();
        return new BeerBuilder()
                .setId(id)
                .setName(name)
                .setBrewery(brewery)
                .createBeer();
    }

}
