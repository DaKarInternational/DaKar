package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class AbstractControllerTest {

    @Autowired
    protected WebTestClient webClient;

    // Journey data
    protected String JOURNEY_ID;
    protected static final String JOURNEY_DESTINATION = "afghanistan";
    protected static final String JOURNEY_PRICE = "1000";
    protected static final String JOURNEY_OWNER = "DaKar";

    // Journey requests
    protected static final String REQUEST_ALL_JOURNEY = "allJourney";
    protected static final String REQUEST_CREATE_JOURNEY = "createJourney";
    protected static final String REQUEST_UPDATE_JOURNEY = "updateJourney";
    protected static final String REQUEST_FIND_JOURNEY_BY_ID = "findJourneyById";
    protected static final String REQUEST_SEARCH_JOURNEY_BY_CRITERIAS = "searchJourney";

    // To parse result as json
    public static final Gson GSON = new Gson();

    /**
     * Step before each test
     */
    @Before
    public void beforeEach(){
        JOURNEY_ID = UUID.randomUUID().toString();
    }

    /**
     * Create a journey for testing
     * @param id
     * @param destination
     * @param price
     * @param owner
     */
    public void createDefaultJourney(String id, String destination, String price, String owner){
        // Create a journey
        Journey journey = new Journey(id, price, destination, owner);
        webClient.post().uri("/test5")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);
    }
}
