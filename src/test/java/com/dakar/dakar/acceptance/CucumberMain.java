package com.dakar.dakar.acceptance;

import com.dakar.dakar.services.interfaces.IJourneyService;
import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * arborescence inspired from there :
 * https://dzone.com/articles/api-testing-with-cucumber-bdd-configuration-tips
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dakar.dakar.acceptance.steps", "com.dakar.dakar.acceptance.config", "com.dakar.dakar.acceptance.hooks"},
        features = {"classpath:acceptance/features"},
        tags = {"not @ignore"}
)
public class CucumberMain {
    //TODO as soon as we have a delete method, then setup a cleanup method that removes unwanted data from db
}
