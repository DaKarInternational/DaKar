package com.dakar.dakar.integration;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyControllerTest {

    @Autowired
    private WebTestClient webClient;

    /**
     * create a journey with a classic endpoint
     */
    @Test
    public void test5ClassicSave() {
        webClient.post().uri("/test5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class)
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().get(0).getDestination(), "afghanistan");
                });
    }

    /**
     * create a journey with GraphQL
     */
    @Test
    public void graphqlSave() {
        String query = " mutation {\n" +
                "            createJourney(input:{ price:\"ll\" destination:\"tt\" }){\n" +
                "                price\n" +
                "                destination\n" +
                "            }\n" +
                "}\n";
        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(query);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(journey -> {
                    Assert.assertTrue(journey.getResponseBody().getData().toString().contains("tt"));
                });
    }

    /**
     * try to create a journey with GraphQL but get an error
     */
    @Test
    public void graphqlErrorOnSave() {
        //TODO: Maybe fetch the query from a propertie file of something ?
        // https://stackoverflow.com/questions/878573/java-multiline-string
        String query = " mutation {\n" +
                "            createJourney(input:{ price:\"ll\" destination:\"tt\" }){\n" +
                "                price\n" +
                "                fieldThatDoesNotExist\n" +
                "            }\n" +
                "}\n";
        GraphQLParameter graphQLParameter = new GraphQLParameter();
//        graphQLParameter.setOperationName("{allJourney}");
        graphQLParameter.setQuery(query);
//        graphQLParameter.setVariables(new HashMap<>());
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
//                .expectBody(ValidationError.class)
                .expectBody()
                .consumeWith(journey -> {
                    Assert.assertTrue(new String(journey.getResponseBody()).contains("FieldUndefined"));
                });
    }

    /**
     * find a journey by destination using graphql
     */
    @Test
    public void findAJourneyByDestinationUsingGraphQl() {

        //TODO put this in a setup method
        //TODO maybe use directly the service for this kind of things ?
        webClient.post()
                .uri("/test5")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class)
                .consumeWith(journey -> {
                    Assert.assertEquals(journey.getResponseBody().get(0).getDestination(), "afghanistan");
                });
        
        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery("{allJourney {destination\nprice}}");
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(journey -> {
                    Assert.assertTrue(journey.getResponseBody().getData().toString().contains("afghanistan"));
                });
    }
}
