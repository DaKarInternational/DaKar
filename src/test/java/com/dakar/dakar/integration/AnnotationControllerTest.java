package com.dakar.dakar.integration;

import com.dakar.dakar.controller.AnnotationController;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.implementation.JourneyServiceImpl;
import com.dakar.dakar.services.interfaces.IJourneyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "36000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnnotationControllerTest extends AbstractIntegrationTest{

    @Autowired
    private WebTestClient webClient;

    Flux<Journey> flux = Flux.just(
            new Journey("Jack", "Bauer", "pompei"),
            new Journey("Chloe", "O'Brian", "afghanistan"),
            new Journey("afghanistan", "Bauer", "chicago"),
            new Journey("David", "Palmer", "dubai"),
            new Journey("Michelle", "Dessler", "portugal"));

    @Test
    public void testRouteWithAnnotationHateoasWithAssembler() {
        this.webClient.get().uri("/test2/afghanistan")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Chloe", "O'Brian", "afghanistan"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithoutAssembler() {
        this.webClient.get().uri("/test3/Pompei")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Jack", "Bauer", "Pompei"));
    }

    @Test
    public void testGetAllJourney() { // Fail car liste aléatoire
        this.webClient.get().uri("/allJourney")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Journey.class).isEqualTo(flux.collectList().block());
    }

    @Test
    public void testGetAllJourneyAsFlux() { // Fail car liste aléatoire
        this.webClient.get().uri("/allJourneyFlux")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Journey.class).isEqualTo(flux.collectList().block());
    }

    @Test
    public void testSaveJourney() {
        Journey journey = new Journey("Tokyo", "100", "ok");
        this.webClient.post().uri("/journey")
                .body(BodyInserters.fromObject(journey))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Tokyo", "100", "ok"));
    }
}
