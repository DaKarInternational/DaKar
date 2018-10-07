package com.dakar.dakar.controller;

import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.validator.GraphQLValidator;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Autowired
    private GraphQLValidator graphQLValidator;

    /**
     * The GraphQL POST endpoint 
     * 
     * translated from there (without the GET method): 
     * https://github.com/geowarin/graphql-webflux/blob/master/src/main/kotlin/com/geowarin/graphql/Routes.kt
     */
    @Bean
    RouterFunction<ServerResponse> routesGraphQl() throws Exception {
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
                        .flatMap(graphQLParameter -> {
                            List<ValidationError> errors = graphQLValidator.validateGraphQL(graphQLParameter);
                            ExecutionInput.Builder executionInput = newExecutionInput();
                            if(errors.size() > 0){
                                System.out.println("Error");
                            }
                            else{
                                executionInput = newExecutionInput()
                                        .query(graphQLParameter.getQuery())
                                        .operationName(graphQLParameter.getOperationName())
                                        .variables(graphQLParameter.getVariables());
                            }
                            return fromFuture(graphQL.executeAsync(executionInput));
                        })
                        .flatMap(executionResult -> ok().syncBody(executionResult))
                        .switchIfEmpty(badRequest().build());
            } else {
                return badRequest().build();
            }
        });
    }
}
