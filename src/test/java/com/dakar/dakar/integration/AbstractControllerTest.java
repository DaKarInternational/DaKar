package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {

    @Autowired
    protected WebTestClient webClient;

    protected String JOURNEY_ID;

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
