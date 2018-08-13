package com.dakar.dakar.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class FunctionalControllerTest {

//    @Autowired
//    private WebTestClient webClient;

//    @Test
    public void testRoutes() {
//        this.webClient.get().uri("/test1/afghanistan")
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(Journey.class)
//                .consumeWith(journey -> {
//                    Assert.assertEquals(journey.getResponseBody().getDestination(), "afghanistan");
//                });
    }
/*
    @Test
    public void testRouteWithAnnotationHateoasWithAssembler() {
        this.webClient.get().uri("/test2/Afghanistan")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Journey.class)
                .isEqualTo(new Journey("Afghanistan", "540"));
    }

    @Test
    public void testRouteWithAnnotationHateoasWithoutAssembler() {
        this.webClient.get().uri("/test3/Singapour")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Journey.class)
                .isEqualTo(new Journey("Singapour", "345"));
    }*/
}
