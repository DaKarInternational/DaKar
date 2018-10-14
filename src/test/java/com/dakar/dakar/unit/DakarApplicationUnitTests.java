package com.dakar.dakar.unit;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.implementation.JourneyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DakarApplicationUnitTests {

    @InjectMocks
    private JourneyServiceImpl journeyService;

    @Mock
    private JourneyRepository journeyRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    //stupid test
    public void gotAllJourney() {
        when(journeyRepository.findAll()).thenReturn(Flux.just(new Journey()));

        List<Journey> journeyList = journeyService.allJourney().collectList().block();
        assertNotNull(journeyList);
    }

    /**
     * Should insert a journey
     */
    @Test
    public void insertJourney() {
        Journey journey = new Journey();
        Mono<Journey> journeyMono = Mono.just(journey);

        when(journeyRepository.saveAll((Publisher<Journey>) any())).thenReturn(Flux.just(journey));

        Flux<Journey> journeyInserted = journeyService.saveJourney(journeyMono);
        //TODO : check the business rules instead of just the values
        assertNotNull(journeyInserted);
    }

    /**
     * Should find journey by id
     */
    @Test
    public void findJourneyById() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        // Find by id
        when(journeyRepository.findById(journey.getId())).thenReturn(Mono.just(journey));
        Journey journeySaved = journeyService.findById(journey.getId()).block();
        assertTrue(journey.getId().equals(journeySaved.getId()));
    }

    /**
     * Should find a journey by destination
     */
    @Test
    public void findJourneyByDestination() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        // Find by destination
        when(journeyRepository.findFirstByDestination(journey.getDestination())).thenReturn(Mono.just(journey));
        Journey journeySaved = journeyService.findByDestination(journey.getDestination()).block();
        assertTrue(journey.getDestination().equals(journeySaved.getDestination()));
    }

    /**
     * Should delete journey by id
     */
    @Test
    public void deleteJourneyById() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        Mono<Void> journeyVoid;

        // Delete by id
        when(journeyRepository.deleteById(journey.getId())).thenReturn(Mono.just(journey).then());
        journeyService.deleteJourney(journey.getId());

        // We check that the journey was deleted
        when(journeyRepository.findById(journey.getId())).thenReturn(null);
        assertNull(journeyService.findById(journey.getId()));
    }

    public void saveJourney(Journey journey){
        when(journeyRepository.saveAll((Publisher<Journey>) any())).thenReturn(Flux.just(journey));
        Mono<Journey> journeyMono = Mono.just(journey);
        journeyService.saveJourney(journeyMono).blockFirst();
    }

    /**
     * Create a default journey
     * @return journey
     */
    public Journey createDefaultJourney(){
        Journey journey = new Journey();
        String id = UUID.randomUUID().toString();
        journey.setId(id);
        journey.setDestination("Vietnam");
        journey.setPrice("1200");
        return journey;
    }

}
