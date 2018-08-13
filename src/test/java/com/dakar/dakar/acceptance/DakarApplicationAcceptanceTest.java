package com.dakar.dakar.acceptance;

import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.testcontainers.couchbase.CouchbaseContainer;

/**
 * arborescence inspired from there :
 * https://dzone.com/articles/api-testing-with-cucumber-bdd-configuration-tips
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.dakar.dakar.acceptance.steps"},
        features = {"classpath:acceptance/features"}
)
//@ContextConfiguration(classes = {ConfigTest.class})
//@ComponentScan(basePackages = "com.dakar.dakar")
public class DakarApplicationAcceptanceTest {
}
