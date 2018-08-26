package com.dakar.dakar.integration;

import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private GraphQL graphQL;

    /**
     * TODO: We should use the controller instead of calling directly graphql
     * <p>
     * Fetch all the journeys
     * We should not do that at all, never...
     */
    @Test
    public void allJourney() {
        ExecutionResult executionResult = this.graphQL.execute("{allJourney {destination}}");
        log.debug(executionResult.getData().toString());
    }
}
