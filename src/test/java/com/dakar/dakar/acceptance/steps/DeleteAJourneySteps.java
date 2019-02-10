package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
public class DeleteAJourneySteps {

    private Journey savedJourney;

    @Autowired
    private IJourneyService journeyService;

    @Given("^journey with the following details:$")
    public void journeyWithTheFollowingDetails(DataTable dataTable) {
        List<Journey> list = dataTable.asList(Journey.class);
        savedJourney = journeyService.saveJourney(Mono.just(list.get(0))).collectList().block().get(0);
    }

    @When("^Delete this journey$")
    public void deleteThisJourney(){
        // Then delete
        journeyService.deleteJourney(savedJourney.getId()).block();
    }

    @Then("^The journey is deleted$")
    public void theJourneyIsDeleted(){
        assertNull(journeyService.findById(savedJourney.getId()).block());
    }
}

