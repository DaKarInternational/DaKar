package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;

@Slf4j
//The spring runner have to be setup only once for cucumber tests
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingSteps {

	private Journey journeyFetched;

	@Autowired
	private IJourneyService journeyService;

	@Autowired
	private WebTestClient webClient;

	@Given("^These journeys have been created$")
	public void user_wants_to_go_to_a_journey() {
		Journey journey = new Journey();
		journey.setDestination("Vietnam");
		journey.setOwner("Dakar");
		journeyService.saveJourney(Mono.just(journey));
	}

	@When("^(.*) search a destination: (.*)")
	public void user_chose_a_destination_Vietnam(String userName, String destination) {
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
				.consumeWith(executionResult -> {
					executionResult.getResponseBody().getData();
					journeyFetched = new Journey();
				});
	}

	@Then("^(.*) find (.*) destinations matching (.*)$")
	public void journey_is_created(String userNAme, String nbrMatches, String journeyExpected) {
		assertEquals(journeyExpected, journeyFetched.getDestination());
	}
}
