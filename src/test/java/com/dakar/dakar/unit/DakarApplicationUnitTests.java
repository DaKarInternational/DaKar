package com.dakar.dakar.unit;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyCriteriaInput;
import com.dakar.dakar.models.StringFilterCriteriaInput;
import com.dakar.dakar.repositories.JourneyRepository;
import com.dakar.dakar.services.implementation.JourneyServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8080/event-emitter"),
                session -> session.send(
                        Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                        .thenMany(session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .log())
                        .then())
                .block(Duration.ofSeconds(10L));

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
        when(journeyRepository.findFirstByDestination(journey.getDestination())).thenReturn(Flux.just(journey));
        Journey journeySaved = journeyService.findByDestination(journey.getDestination()).blockFirst();
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
     * Should find journeys by criteria : destination
     */
    @Test
    public void searchJourneysByCriteriaDestination() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        JourneyCriteriaInput criterias = new JourneyCriteriaInput();
        StringFilterCriteriaInput destinationFilter = new StringFilterCriteriaInput();
        destinationFilter.setContains(journey.getDestination());
        criterias.setDestination(destinationFilter);

        // Find by destination
        when(journeyRepository.findByDestination(criterias.getDestination().getContains())).thenReturn(Flux.just(journey));
        List<Journey> journeys = journeyService.findByCriterias(criterias).collectList().block();
        assertTrue(journeys.size() == 1);
        assertTrue(journeys.get(0).getDestination() == journey.getDestination());
    }

    /**
     * Should find journeys by criteria : price
     */
    @Test
    public void searchJourneysByCriteriaPrice() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        JourneyCriteriaInput criterias = new JourneyCriteriaInput();
        StringFilterCriteriaInput priceFilter = new StringFilterCriteriaInput();
        priceFilter.setContains(journey.getPrice());
        criterias.setPrice(priceFilter);

        // Find by destination
        when(journeyRepository.findByPrice(criterias.getPrice().getContains())).thenReturn(Flux.just(journey));
        List<Journey> journeys = journeyService.findByCriterias(criterias).collectList().block();
        assertTrue(journeys.size() == 1);
        assertTrue(journeys.get(0).getPrice() == journey.getPrice());
    }

    /**
     * Should search journeys by criterias : destination and price
     */
    @Test
    public void searchJourneysByCriteriasDestinationAndPrice() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        JourneyCriteriaInput criterias = new JourneyCriteriaInput();
        StringFilterCriteriaInput destinationFilter = new StringFilterCriteriaInput();
        destinationFilter.setContains(journey.getDestination());
        criterias.setDestination(destinationFilter);
        StringFilterCriteriaInput priceFilter = new StringFilterCriteriaInput();
        priceFilter.setContains(journey.getPrice());
        criterias.setPrice(priceFilter);

        // Find by destination
        when(journeyRepository.findByDestinationAndPrice(criterias.getDestination().getContains(), criterias.getPrice().getContains())).thenReturn(Flux.just(journey));
        List<Journey> journeys = journeyService.findByCriterias(criterias).collectList().block();
        assertTrue(journeys.size() == 1);
        assertTrue(journeys.get(0).getDestination() == journey.getDestination());
        assertTrue(journeys.get(0).getPrice() == journey.getPrice());
    }

    /**
     * Should search journeys with no criterias
     */
    @Test
    public void searchJourneysWithNoCriterias() {
        // Create a journey
        Journey journey = createDefaultJourney();
        Journey journey2 = createDefaultJourney();
        journey2.setPrice("456");
        journey2.setDestination("Afghanistan");
        // Save in database
        saveJourney(journey);
        saveJourney(journey2);

        JourneyCriteriaInput criterias = new JourneyCriteriaInput();
        // Find by destination
        when(journeyRepository.findAll()).thenReturn(Flux.just(journey, journey2));
        List<Journey> journeys = journeyService.findByCriterias(criterias).collectList().block();
        assertTrue(journeys.size() == 2);
    }

    /**
     * Should not find journeys by criteria : destination
     * because wrong destination
     */
    @Test
    public void searchJourneysByCriteriaDestinationFail() {
        // Create a journey
        Journey journey = createDefaultJourney();

        // Save in database
        saveJourney(journey);

        JourneyCriteriaInput criterias = new JourneyCriteriaInput();
        StringFilterCriteriaInput destinationFilter = new StringFilterCriteriaInput();
        destinationFilter.setContains("invalid");
        criterias.setDestination(destinationFilter);

        // Find by destination
        when(journeyRepository.findByDestination(criterias.getDestination().getContains())).thenReturn(null);
        assertNull(journeyService.findByCriterias(criterias));
    }

    /**
     * Create a default journey
     * @return journey
     */
    public Journey createDefaultJourney(){
        Journey journey = new Journey();
        String id = randomUUID().toString();
        journey.setId(id);
        journey.setDestination("Vietnam");
        journey.setPrice("1200");
        return journey;
    }

}
