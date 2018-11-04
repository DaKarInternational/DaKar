package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class ReactiveControllerTest {

    @Autowired
    private WebTestClient webClient;

    private String JOURNEY_ID;

    /**
     * Step before each test
     */
    @Before
    public void beforeEach(){
        JOURNEY_ID = UUID.randomUUID().toString();
    }

    /**
     * Step after each test
     */
    @After
    public void afterEach(){
        this.webClient.delete().uri("/deleteJourney/" + JOURNEY_ID)
                .exchange()
                .expectStatus()
                .isEqualTo(204);
    }

    /**
     * find a journey by destination using classic method
     */
    @Test
    public void test1ClassicFind() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Then find it
        this.webClient.get()
                .uri("/test1/afghanistan")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Journey.class)
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().getDestination(), "afghanistan");
                });
    }

    /**
     * find a journey by destination using first hateoas method
     */
    @Test
    public void test2HateoasWithAssembler() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Then find it
        this.webClient.get()
                .uri("/test2/afghanistan")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Journey.class)
//                .isEqualTo(new Journey("afghanistan", "540"));
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().getDestination(), "afghanistan");
                });
    }

    /**
     * find a journey by destination using first second hateoas method
     */
    @Test
    public void test3HateoasWithoutAssembler() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Then find it
        this.webClient.get()
                .uri("/test3/afghanistan")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Journey.class)
//                .isEqualTo(new Journey("afghanistan", "345"));
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().getDestination(), "afghanistan");
                });
    }

    /**
     * create a journey
     */
    @Test
    public void test5ClassicSave() {
        this.webClient.post()
                .uri("/test5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class)
//                .isEqualTo(new Journey("afghanistan", "345"));
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().get(0).getDestination(), "afghanistan");
                });
    }

    /**
     * Delete a journey
     */
    @Test
    public void deleteJourney() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");
        // Then delete
        this.webClient.delete().uri("/deleteJourney/" + JOURNEY_ID)
                .exchange()
                .expectStatus()
                .isEqualTo(204);
    }
  
    /**
     * Test resource bundle i18n : english
     */
    @Test
    public void testi18nEnglish() {
        this.webClient.get()
        .uri("/welcome/en/Damien")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(message -> {
                    Assert.assertEquals(message.getResponseBody(), "Welcome Damien to DaKar!");
                });
    }

    /**
     * Test resource bundle i18n : french
     */
    @Test
    public void testi18nFrench() {
        this.webClient.get().uri("/welcome/fr/Karim")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .consumeWith(message -> {
                    Assert.assertEquals(message.getResponseBody(), "Bienvenue Karim chez DaKar!");
                });
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
