package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.SimpleExecutionResult;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@Slf4j
//The spring runner have to be setup only once for cucumber tests
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingSteps {

	private Journey journeyFetched;

	private Journey journeyCreated;

	@Autowired
	private IJourneyService journeyService;

	@Autowired
	private WebTestClient webClient;

	@Given("^These journeys have been created$")
	public void user_wants_to_go_to_a_journey() {
		journeyCreated = new Journey();
		journeyCreated.setId("28356590-332e-43e0-ba7c-50c6a98e41a8");
		journeyCreated.setDestination("Vietnam");
		journeyCreated.setOwner("Dakar");
		journeyService.saveJourney(Mono.just(journeyCreated));
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
		assertEquals(journeyExpected, journeyCreated.getDestination());
	}

	@When("^(.*) show a journey: (.*)$")
	public void usernameShowAJourneyId(String username, String id) throws Throwable {
		// Find it by id
		String queryFindJourneyById = " query findJourney{\n" +
				"            findJourneyById(id:\"" + id +"\"){\n" +
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
				.expectBody(SimpleExecutionResult.class);
	}
}
