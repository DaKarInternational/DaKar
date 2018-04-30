package com.dakar.dakar.integration;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.services.JourneyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class DakarApplicationIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    private JourneyService journeyService;

    @Test
    public void gotAllJourney() {
        //TODO: Use the controller and mockmvc to test that
        List<Journey> journeyList = journeyService.allJourney();
        assertNotNull(journeyList);
    }
}
