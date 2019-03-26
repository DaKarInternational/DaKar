package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

@Slf4j
public class SaveAJourneySteps extends CommonSteps {

    private Journey journey;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private IJourneyService journeyService;

    // Journey requests
    protected static final String REQUEST_ALL_JOURNEY = "allJourney";

    @Given("^(.*) participated to the creation of one journey$")
    public void user_participated_to_the_creation_of_one_journey(String userName) {
        Journey journey = new Journey();
        journey.setDestination(userName);
        journey.setOwner(userName);
        //TODO, you should not be able to save a journey with a field set to null and annotated @NonNull
        // price has the annotation @NonNull but we still can save it in couchbase
        journeyService.saveJourney(Mono.just(journey));
    }

    @When("^(.*) search his journeys$")
    public void user_search_his_journey(String userName) {
        GraphQLParameter graphQLParameter = new GraphQLParameter();
//		TODO put the correct query here 
        graphQLParameter.setQuery("{allJourney {destination\nprice}}");
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(result -> {
                    this.journey = new Journey();
                    this.journey.setDestination("Dakar");//TODO fetch from the returned data (parse the JSON)
                });
    }

    @When("^(.*) create the journey for destination: (.*)$")
    public void user_wants_to_go_to_a_journey(String userName, String destination) {
        String query = " mutation {\n" +
                "            createJourney(input:{ owner:\"" + userName + "\", destination:\"" + destination + "\" }){\n" +
                "                owner\n" +
                "                destination\n" +
                "            }\n" +
                "}\n";
        GraphQLParameter graphQLParameter = new GraphQLParameter();
        graphQLParameter.setQuery(query);
        this.webClient.post().uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(graphQLParameter))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SimpleExecutionResult.class)
                .consumeWith(simpleExecutionResult -> {
                    Assert.assertTrue(simpleExecutionResult.getResponseBody().getData().toString().contains(destination));
                    simpleExecutionResult.getResponseBody().getData();
                    this.journey = new Journey();
                    this.journey.setDestination(destination);//TODO fetch from the returned data
                    this.journey.setPrice(destination);
                    this.journey.setOwner(userName);
                });
    }

    @Then("^The journey (.*) is created with (.*) as owner$")
    public void journey_is_created(String journeyExpected, String owner) {
        assertEquals(journeyExpected, journey.getDestination());
        assertEquals(owner, journey.getOwner());
    }

    @Then("^(.*) gets all his journeys$")
    public void user_gets_his_journey(String journeyExpected) {
        assertEquals(journeyExpected, journey.getDestination());
    }
}
