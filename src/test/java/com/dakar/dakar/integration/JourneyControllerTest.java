package com.dakar.dakar.integration;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import com.dakar.dakar.utils.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import graphql.GraphQLError;
import graphql.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyControllerTest extends AbstractControllerTest{

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
        String expectedResponse = "Validation error of type FieldUndefined: Field 'fieldThatDoesNotExist' in type 'Journey' is undefined";
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
                .is4xxClientError()
                .expectBody()
                .consumeWith(response -> {
                    Assert.assertTrue(new String(response.getResponseBody()).equals(expectedResponse));
                });
    }

    /**
     * find a journey by destination using graphql
     */
    @Test
    public void findAJourneyByDestinationUsingGraphQl() {
        // Create a journey
        Journey journey = new Journey(JOURNEY_ID, JOURNEY_PRICE, JOURNEY_DESTINATION, JOURNEY_OWNER);

        //TODO put this in a setup method
        //TODO maybe use directly the service for this kind of things ?
        webClient.post()
                .uri("/test5")
                .body(Mono.just(journey), Journey.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Journey.class);
        
        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery("{" + REQUEST_ALL_JOURNEY + " {destination\nprice}}");
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    // We check the result
                    String jsonResult = GSON.toJson(result.getResponseBody());
                    JsonArray journeys = JsonParser.getJsonArray(jsonResult, REQUEST_ALL_JOURNEY);
                    Assert.assertTrue(journeys.size() > 0);
                    JsonObject jsonJourney = journeys.get(0).getAsJsonObject();
                    Assert.assertTrue("afghanistan".equals(jsonJourney.get("destination").getAsString()));
                    Assert.assertTrue("1000".equals(jsonJourney.get("price").getAsString()));
                });
    }

    /**
     * find a journey by id using graphql
     */
    @Test
    public void findJourneyByIdUsingGraphQl() {

        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Find it by id
        String queryFindJourneyById = " query findJourney{\n" +
                "            findJourneyById(id:\"" + JOURNEY_ID +"\"){\n" +
                "                id\n" +
                "                destination\n" +
                "            }\n" +
                "}\n";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyById);
        this.webClient.post().uri("/graphql")
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

    /**
     * delete a journey with GraphQL
     */
    @Test
    public void graphqlDelete() {
        // Create a journey to delete
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Delete a journey
        String queryDelete = " mutation deleteJourney {\n" +
                "            deleteJourney(id:\"" + JOURNEY_ID +"\")\n" +
                "}\n";
        GraphQLParameter graphQLParameterDelete = new GraphQLParameter();
        graphQLParameterDelete.setQuery(queryDelete);
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameterDelete))
                .exchange()
                .expectStatus()
                .isEqualTo(200);

        // Find it by id to check that it has been removed
        String queryFindJourneyById = " query findJourney{\n" +
                "            findJourneyById(id:\"" + JOURNEY_ID +"\"){\n" +
                "                id\n" +
                "                destination\n" +
                "            }\n" +
                "}\n";

        GraphQLParameter graphQLParameterFind = new GraphQLParameter();
        graphQLParameterFind.setQuery(queryFindJourneyById);
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameterFind))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(journey -> {
                    // Assert.assertFalse(journey.getResponseBody().isDataPresent());
                    Assert.assertTrue(journey.getResponseBody().getData().toString().contains("findJourneyById=null"));
                });
    }

    /**
     * Basic flow : Find journeys by destination using graphql
     */
    @Test
    public void findJourneysByCriteriaDestinationUsingGraphQl() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Query to search by criterias
        String destination = "afghanistan";
        String queryFindJourneyByCriterias = "{"
                +  "  searchJourney(criteria: {"
                +  "    destination: {"
                +  "      contains: \"" + destination +"\""
                +  "    }"
                +  "  }"
                +  "  ) {"
                +  "    id"
                +  "    destination}}";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyByCriterias);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    Assert.assertTrue(result.getResponseBody().getData().toString().contains("afghanistan"));
                });
    }

    /**
     * Basic flow : Find journeys by price using graphql
     */
    @Test
    public void findJourneysByCriteriaPriceUsingGraphQl() {
        // Create a journey
        createDefaultJourney(JOURNEY_ID, "afghanistan", "1000", "DaKar");

        // Query to search by criterias
        String price = "1000";
        String queryFindJourneyByCriterias = "{"
                +  "  searchJourney(criteria: {"
                +  "    price: {"
                +  "      contains: \"" + price +"\""
                +  "    }"
                +  "  }"
                +  "  ) {"
                +  "    id"
                +  "    price}}";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyByCriterias);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    Assert.assertTrue(result.getResponseBody().getData().toString().contains("1000"));
                });
    }

    /**
     * Basic flow : Find journeys by destination and price using graphql
     */
    @Test
    public void findJourneysByCriteriaDestinationAndPriceUsingGraphQl() {
        // Create a journey
        String price = "1000";
        String destination = "afghanistan";
        createDefaultJourney(JOURNEY_ID, destination, price, "DaKar");

        // Query to search by criterias
        String queryFindJourneyByCriterias = "{"
                +  "  searchJourney(criteria: {"
                +  "    destination: {"
                +  "      contains: \"" + destination +"\""
                +  "    },"
                +  "    price: {"
                +  "      contains: \"" + price +"\""
                +  "    }"
                +  "  }"
                +  "  ) {"
                +  "    destination"
                +  "    price}}";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyByCriterias);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    Assert.assertTrue(result.getResponseBody().getData().toString().contains("1000"));
                });
    }

    /**
     * Exception flow : Find journeys by destination and price using graphql wrong data
     * because there is no match
     */
    @Test
    public void findJourneysByCriteriaDestinationAndPriceUsingGraphQlWrongData() {
        // Create a journey
        String price = "456";
        String destination = "afghanistan";
        createDefaultJourney(JOURNEY_ID, destination, "1000", "DaKar");

        // Query to search by criterias
        String queryFindJourneyByCriterias = "{"
                +  "  searchJourney(criteria: {"
                +  "    destination: {"
                +  "      contains: \"" + destination +"\""
                +  "    },"
                +  "    price: {"
                +  "      contains: \"" + price +"\""
                +  "    }"
                +  "  }"
                +  "  ) {"
                +  "    destination"
                +  "    price}}";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyByCriterias);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    Assert.assertTrue(!result.getResponseBody().getData().toString().contains("afghanistan"));
                });
    }

    /**
     * Basic flow : Find all journeys because no criterias
     */
    @Test
    public void findJourneysWithNoCriteriasUsingGraphQl() {
        // Create a journey
        String price = "1000";
        String destination = "afghanistan";
        createDefaultJourney(JOURNEY_ID, destination, price, "DaKar");

        // Query to search by criterias
        String queryFindJourneyByCriterias = "{"
                +  "  searchJourney(criteria: {"
                +  "  }"
                +  "  ) {"
                +  "    destination"
                +  "    price}}";

        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(queryFindJourneyByCriterias);
        this.webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    Assert.assertTrue(result.getResponseBody().getData().toString().contains("afghanistan"));
                });
    }

}
