package com.dakar.dakar;

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

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient(timeout = "36000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnnotationControllerTest {

    @Autowired
    private IJourneyService userService;

    @Autowired
    private WebTestClient webClient;



    @Test
    public void testrouteWithAnnotationHateoasWithAssembler() {
        this.webClient.get().uri("/testlist")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Journey.class).isEqualTo(null);
    }
}
