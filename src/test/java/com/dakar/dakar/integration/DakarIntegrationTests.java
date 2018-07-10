package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.JourneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class DakarIntegrationTests extends AbstractCouchBaseTests {

    @Autowired
    private JourneyService journeyService;

    @Test
    public void insertJourneyCouch() {
        //TODO : use the builder pattern to set destination 
        journeyService.insertNewJourneyInCouchbase(Mono.just(new Journey("afghanistan", "", "afghanistan")));
        assertNotNull(journeyService.findByCountry("afghanistan"));
        //TODO : check if there are best practices to do tests in reactive 
//        assertNotNull(journeyFetched);
    }
}

