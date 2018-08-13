package com.dakar.dakar.acceptance.steps;

import com.dakar.dakar.acceptance.AbstractCouchBaseTests;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
//@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = "com.dakar.dakar")
//@SpringBootTest(/*webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT*/)
public class DakarIntegrationTests extends AbstractCouchBaseTests{

    @Autowired
    private IJourneyService journeyService;

    @Test
//    @When("^test$")
    public void insertJourneyCouch() {
        journeyService.fillDbWithDumbData();
        //TODO : use the builder pattern to set destination 
        journeyService
                .saveJourney(Mono.just(new Journey("afghanistan", "2000")))
                .subscribe(journey -> {
                    log.error(journey.toString());
                });
        assertNotNull(journeyService.findByCountry("afghanistan").block());
        assertEquals(journeyService.findByCountry("afghanistan").block().getDestination(), "afghanistan");
        //TODO : check if there are best practices to do tests in reactive 
    }


    @Test
//    @When("^test$")
    public void insertJssourneyCouch() {
        journeyService.fillDbWithDumbData();
        //TODO : use the builder pattern to set destination 
        journeyService
                .saveJourney(Mono.just(new Journey("afghanistan", "2000")))
                .subscribe(journey -> {
                    log.error(journey.toString());
                });
        assertNotNull(journeyService.findByCountry("afghanistan").block());
        assertEquals(journeyService.findByCountry("afghanistan").block().getDestination(), "afghanistan");
        //TODO : check if there are best practices to do tests in reactive 
    }
}

