package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.repositories.JourneyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalControllerTest extends AbstractIntegrationTest{

    @Autowired
    private WebTestClient webClient;

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
                new Journey("Pompei", "100"),
                new Journey("Afghanistan", "540"),
                new Journey("Rome", "234"),
                new Journey("Dubai", "109"),
                new Journey("Singapour", "345"));
        journeyRepository
                .insert(flux)
                .subscribe();
    }

    @Test
    public void testRoutes() {
        this.webClient.get().uri("/test1/Pompei")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Pompei", "100"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithAssembler() {
        this.webClient.get().uri("/test2/Afghanistan")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Afghanistan", "540"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithoutAssembler() {
        this.webClient.get().uri("/test3/Singapour")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Singapour", "345"));
    }

}
