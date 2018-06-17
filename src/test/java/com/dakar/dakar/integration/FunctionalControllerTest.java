package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunctionalControllerTest extends AbstractIntegrationTest{

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testRoutes() {
        this.webClient.get().uri("/test1/Chloe")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Chloe", "O'Brian", "afghanistan"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithAssembler() {
        this.webClient.get().uri("/test2/Pompei")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Jack", "Bauer", "Pompei"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithoutAssembler() {
        this.webClient.get().uri("/test3/Pompei")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(new Journey("Jack", "Bauer", "Pompei"));
    }

}
