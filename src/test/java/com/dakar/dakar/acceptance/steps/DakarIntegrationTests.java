package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.implementation.JourneyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class DakarIntegrationTests extends AbstractCouchBaseTests{

    @Autowired
    private JourneyServiceImpl journeyService;

    @Test
    public void insertJourneyCouch() {
        journeyService.fillDbWithDumbData();
        //TODO : use the builder pattern to set destination 
        journeyService
                .saveJourney(Mono.just(new Journey("afghanistan","2000")))
                .subscribe(journey -> {log.error(journey.toString());});
        assertNotNull(journeyService.findByCountry("afghanistan").block());
        assertEquals(journeyService.findByCountry("afghanistan").block().getDestination(),"afghanistan");
        //TODO : check if there are best practices to do tests in reactive 
    }
}

