package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class BookingSteps {
	
	private Journey journey;
	
	@Given("^(.*) wants to go to a journey$")
	public void user_wants_to_go_to_a_journey(String userName) {
//		journey = new Journey("Jack", "afghanistan");
		journey.setOwner(userName);
	}

	@When("^User choses a destination : (.*)$")
	public void user_chose_a_destination_Vietnam(String destination) {
		journey.setDestination(destination);
	}

	@Then("^The journey (.*) is created$")
	public void journey_is_created(String journeyExpected) {
		assertEquals(journeyExpected, journey.getDestination());
	}



	@Autowired
	private IJourneyService journeyService;

//	@Test
    @When("^testoo$")
	public void insertJoddurneyCouch() {
//		journeyService.fillDbWithDumbData();
		//TODO : use the builder pattern to set destination 
//		journeyService
//				.saveJourney(Mono.just(new Journey("afghanistan", "2000")))
//				.subscribe(journey -> {
//					log.error(journey.toString());
//				});
		assertNotNull(journeyService.findByDestination("afghanistan").block());
		assertEquals(journeyService.findByDestination("afghanistan").block().getDestination(), "afghanistan");
		//TODO : check if there are best practices to do tests in reactive 
	}
	
}
