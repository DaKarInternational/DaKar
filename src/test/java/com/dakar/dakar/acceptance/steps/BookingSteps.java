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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
//The spring runner have to be setup only once for cucumber tests
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingSteps {

    // Journey saved on database
    private Journey savedJourney;

    // Result of search
	private String result;

	@Autowired
	private IJourneyService journeyService;

	@Autowired
	private WebTestClient webClient;

	@Given("^These journeys have been created$")
	public void user_wants_to_go_to_a_journey() {
		Journey journeyToSave = new Journey();
		journeyToSave.setId("28356590-332e-43e0-ba7c-50c6a98e41a8");
		journeyToSave.setDestination("Vietnam");
		journeyToSave.setPrice("1000");
		journeyToSave.setOwner("Dakar");
		savedJourney = journeyService.saveJourney(Mono.just(journeyToSave)).collectList().block().get(0);
	}

	@When("^(.*) search a destination: (.*)")
	public void user_chose_a_destination_Vietnam(String userName, String destination) {
		GraphQLParameter graphQLParameter = new GraphQLParameter();
		String queryFindJourneyByCriterias = "{"
				+  "  searchJourney(criteria: {"
				+  "    destination: {"
				+  "      contains: \"" + destination +"\""
				+  "    }"
				+  "  }"
				+  "  ) {"
				+  "    id"
				+  "    destination}}";
		graphQLParameter.setQuery(queryFindJourneyByCriterias);
		this.webClient.post().uri("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(graphQLParameter))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(SimpleExecutionResult.class)
				.consumeWith(executionResult -> {
					result = executionResult.getResponseBody().getData().toString();
				});
	}

	@Then("^(.*) find (.*) destinations matching (.*)$")
	public void journey_is_created(String userName, String nbMatches, String elementToCompare) {
		assertTrue(result.contains(elementToCompare));
	}

	@When("^(.*) show a journey: (.*)$")
	public void usernameShowAJourneyId(String username, String id) {
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
				.expectBody(SimpleExecutionResult.class)
				.consumeWith(executionResult -> {
					result = executionResult.getResponseBody().getData().toString();
				});
	}

	@When("^(.*) search a price (.*)")
	public void usernameSearchAPrice(String userName, String price) {
		GraphQLParameter graphQLParameter = new GraphQLParameter();
		String queryFindJourneyByCriterias = "{"
				+  "  searchJourney(criteria: {"
				+  "    price: {"
				+  "      contains: \"" + price +"\""
				+  "    }"
				+  "  }"
				+  "  ) {"
				+  "    id"
				+  "    price}}";

		graphQLParameter.setQuery(queryFindJourneyByCriterias);
		this.webClient.post().uri("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(graphQLParameter))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(SimpleExecutionResult.class)
				.consumeWith(executionResult -> {
					result = executionResult.getResponseBody().getData().toString();
				});
	}

	@When("^(.*) search a destination (.*) and a price (.*)$")
	public void usernameSearchADestinationAndAPrice(String username, String destination, String price) {
		GraphQLParameter graphQLParameter = new GraphQLParameter();
		System.out.println("ooo : " + destination);
		System.out.println("ooo : " + price);
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
		graphQLParameter.setQuery(queryFindJourneyByCriterias);
		this.webClient.post()
				.uri("/graphql")
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(graphQLParameter))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(SimpleExecutionResult.class)
				.consumeWith(executionResult -> {
					result = executionResult.getResponseBody().getData().toString();
				});
	}

	@Then("^(.*) find (.*) destinations no matching (.*)$")
	public void usernameFindNumbersJourneysFoundDestinationsNoMatchingPrice(String userName, String nbMatches, String elementToCompare) {
		assertTrue(!result.contains(elementToCompare));
	}

}
