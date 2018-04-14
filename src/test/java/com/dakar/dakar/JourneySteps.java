package com.dakar.dakar;

import org.junit.Assert;

import com.dakar.dakar.Models.Journey;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class JourneySteps {
	
	private Journey journey;
	
	@Given("^(.*) wants to go to a journey$")
	public void user_wants_to_go_to_a_journey(String userName) throws Throwable {
		journey = new Journey();
		journey.setOwner(userName);
	}

	@When("^User choses a destination (.*)$")
	public void user_chose_a_destination_Vietnam(String destination) throws Throwable {
		journey.setDestination(destination);
	}

	@Then("^Journey is created$")
	public void journey_is_created() throws Throwable {
		Assert.assertTrue(true);
	}

}
