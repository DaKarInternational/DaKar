package com.dakar.dakar.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * arborescence inspired from there :
 * https://dzone.com/articles/api-testing-with-cucumber-bdd-configuration-tips
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dakar.dakar.acceptance.steps", "com.dakar.dakar.acceptance.config"},
        features = {"classpath:acceptance/features"},
        tags = {"not @ignore"}
)
public class CucumberMain {
    //TODO as soon as we have a delete method, then setup a cleanup method that removes unwanted data from db
}
