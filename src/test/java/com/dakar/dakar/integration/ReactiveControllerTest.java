package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    /**
     * find a journey by destination using classic method
     */
    @Test
    public void test1ClassicFind() {
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
     * create a journey
     */
    @Test
    public void testSaveJourneyHandler() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "afghanistan", "afghanistan", "o");

        webClient.post().uri("/saveJourneyCouch")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Journey.class);
    }

    /**
     * create a journey error
     */
    @Test
    public void testSaveJourneyHandlerError() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "", "afghanistan", "o");

        webClient.post().uri("/saveJourneyCouch")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus().isEqualTo(422);
    }

    /**
     * create a journey
     */
    @Test
    public void testSaveJourneyJavaxValidator() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "afghanistan", "afghanistan", "o");

        webClient.post().uri("/saveJourneyValidatorJavax")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);
    }

    /**
     * create a journey error
     */
    @Test
    public void testSaveJourneyJavaxValidatorError() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "", "afghanistan", "o");

        webClient.post().uri("/saveJourneyValidatorJavax")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isEqualTo(422);
    }

    /**
     * create a journey
     */
    @Test
    public void testSaveJourneySpringValidator() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "afghanistan", "afghanistan", "o");

        webClient.post().uri("/saveJourneyValidatorSpring")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);
    }

    /**
     * create a journey error
     */
    @Test
    public void testSaveJourneySpringValidatorError() {
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "afghanistan", "afghanistan", "o");

        webClient.post().uri("/saveJourneyValidatorSpring")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);
    }
  
    /**
     * Delete a journey
     */
    @Test
    public void deleteJourney() {
        // Create a journey
        String id = UUID.randomUUID().toString();
        Journey journey = new Journey(id, "afghanistan", "afghanistan", "o");
        webClient.post().uri("/test5")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);

        // Then delete
        this.webClient.delete().uri("/deleteJourney/"+id)
                .exchange()
                .expectStatus()
                .isEqualTo(204);
    }
  
    /**
     * Test resource bundle i18n : English
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
     * Test resource bundle i18n : French
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
}
