package com.dakar.dakar.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * arborescence inspired from there :
 * https://dzone.com/articles/api-testing-with-cucumber-bdd-configuration-tips
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dakar.dakar.cucumber.steps"},
        features = {"classpath:cucumber/features"}
)
public class CucumberMain {
}
