package com.dakar.dakar.integration;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyControllerTest {

    @Autowired
    private WebTestClient webClient;

    /**
     * create a journey
     */
//    @Test
    public void test5ClassicSave() {
        webClient.post().uri("/test5")
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
     * find a journey by destination using graphql
     */
    @Test
    public void findAJourneyByDestinationUsingGraphQl() {

        webClient.post().uri("/test5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class)
//                .isEqualTo(new Journey("afghanistan", "345"));
//                .expectBody()
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody()
                            .get(0).getDestination()
                            , "afghanistan");
                });
        
        GraphQLParameter graphQLParameter = new GraphQLParameter();
//        graphQLParameter.setOperationName("{allJourney}");
        graphQLParameter.setQuery("{allJourney {destination\nprice}}");
//        graphQLParameter.setVariables(new HashMap<>());
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(graphQLParameter), GraphQLParameter.class)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
//                .hasSize(1)
//                .expectBody()
                .consumeWith(journey -> {
                    Assert.assertTrue(journey.getResponseBody().getData().toString().contains("afghanistan"));
                });
    }
}
