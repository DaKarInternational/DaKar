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

@Slf4j
public class DeleteAJourneySteps {

    private Journey journey;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private IJourneyService journeyService;

    @Given("^journey with the following details$")
    public void journeyWithTheFollowingDetails(DataTable dataTable){
//        journey = new Journey();
//        journey.setId("toto");
//        journey.setDestination("Vietnam");
//        journey.setOwner("toto");
//        journeyService.saveJourney(Mono.just(journey));
        List<Journey> list = dataTable.transpose().asList(Journey.class);
        list.stream().forEach(System.out::println);
    }

//    @When("^(.*) delete this journey (.*)$")
//    public void usernameDeleteThisJourneyId(String username, String id) throws Throwable {
//        // Then delete
//        this.webClient.delete().uri("/deleteJourney/"+ id)
//                .exchange()
//                .expectStatus()
//                .isEqualTo(204);
//    }
//
//    @Then("^The journey (.*) is deleted with (.*) as owner$")
//    public void journey_is_created(String journeyExpected, String owner) {
//        assertEquals(journeyExpected, journey.getDestination());
//        assertEquals(owner, journey.getOwner());
//    }

}

