package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.Journey;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class BookingSteps {
	
	private Journey journey;
	
	@Given("^(.*) wants to go to a journey$")
	public void user_wants_to_go_to_a_journey(String userName) {
		journey = new Journey();
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

}
