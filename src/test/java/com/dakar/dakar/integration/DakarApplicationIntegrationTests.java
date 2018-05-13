package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.JourneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class DakarApplicationIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private JourneyRepository journeyRepository;

    /**
     * save a couple of Journey in the mongo testContainer
     * <p>
     * you should not do that with the repo but with the controllers
     */
    @Before
    public void fillDbWithDumbData() {
        Flux<Journey> flux = Flux.just(
                new Journey("Jack", "Bauer", "afghanistan"),
                new Journey("Chloe", "O'Brian", "afghanistan"),
                new Journey("afghanistan", "Bauer", "afghanistan"),
                new Journey("David", "Palmer", "afghanistan"),
                new Journey("Michelle", "Dessler", "afghanistan"));
        journeyRepository
                .insert(flux)
                .subscribe();
    }

    @Test
    public void gotAllJourney() {
        //TODO: Use the controller and mockmvc to test that
        List<Journey> journeyList = journeyService.allJourney();
        assertNotNull(journeyList);
    }

    public void insertJourney() {
        Journey journey = new Journey();
        //TODO : use the builder pattern to set detination 
        Mono<Journey> journeyList = journeyService.insertNewJourney(journey);
        Mono<Journey> journeyFetched = journeyService.findByDestinationWithMongoRepo("testDestination");
        assertNotNull(journeyList);
        //TODO : check if there are best practices to do tests in reactive 
        assertNotNull(journeyFetched);
    }
}
