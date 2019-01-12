package com.dakar.dakar.controller;

import com.dakar.dakar.models.GraphQLParameter;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.GraphQLException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static graphql.ExecutionInput.newExecutionInput;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromFuture;

@Slf4j
@Controller
public class GraphQlController {

    private MediaType GraphQLMediaType = MediaType.parseMediaType("application/json");

    @Autowired
    private GraphQL graphQL;

    /**
     * The GraphQL POST endpoint
     * <p>
     * translated from there (without the GET method):
     * https://github.com/geowarin/graphql-webflux/blob/master/src/main/kotlin/com/geowarin/graphql/Routes.kt
     */
    @Bean
    RouterFunction<ServerResponse> routesGraphQl() {
        log.info("Graphql route");
        // some working queries :

        // {allJourney {destination}}
        
        /*
        mutation {
          createJourney(input: {price: "tt", country: "tt"}) {
            price
            country
          }
        }
         */
        return route(RequestPredicates.POST("/graphql"), request -> {
            if (request.headers().contentType().filter(mediaType -> mediaType.isCompatibleWith(GraphQLMediaType)).isPresent()) {
                return request.bodyToMono(GraphQLParameter.class)
                        .flatMap((GraphQLParameter graphQLParameter) -> {
                            ExecutionInput.Builder executionInput;
                            executionInput = newExecutionInput()
                                    .query(graphQLParameter.getQuery())
                                    .operationName(graphQLParameter.getOperationName())
                                    .variables(graphQLParameter.getVariables());
                            return fromFuture(graphQL.executeAsync(executionInput));
                        })
                        .flatMap(executionResult -> {
                            if(executionResult.getErrors().size() > 0){
                                String errorMessage = executionResult.getErrors()
                                        .stream()
                                        .map(error -> error.getMessage()).reduce("", (a,b) -> a + b);
                                return ServerResponse.unprocessableEntity().syncBody(errorMessage);
                            }
                            return ok().syncBody(executionResult);
                        })
                        .onErrorResume(error -> badRequest().build())
                        .switchIfEmpty(badRequest().build());
            } else {
                return badRequest().build();
            }
        });
    }
}
