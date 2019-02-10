package com.dakar.dakar.acceptance.hooks;

import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  Hooks are blocks of code that can run at various points in the Cucumber execution cycle
 */
public class Hooks {
    private static final String JOURNEY_ID = "28356590-332e-43e0-ba7c-50c6a98e41a8";

    @Autowired
    private IJourneyService journeyService;

    /**
     * Step after each test
     */
    @After
    public void AfterEach(){
        journeyService.deleteJourney(JOURNEY_ID);
    }
}
