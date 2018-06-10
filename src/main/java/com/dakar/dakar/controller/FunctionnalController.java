package com.dakar.dakar.controller;

import com.coxautodev.graphql.tools.SchemaParser;
import com.dakar.dakar.models.GraphQLParameter;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.resolvers.JourneyResolver;
import com.dakar.dakar.resolvers.QueryResolver;
import com.dakar.dakar.resourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.resources.JourneyResource;
import com.dakar.dakar.services.JourneyService;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import static graphql.ExecutionInput.newExecutionInput;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.fromFuture;

@Slf4j
@Controller
public class FunctionnalController {

    @Autowired
    private JourneyService journeyService;

    private MediaType GraphQLMediaType = MediaType.parseMediaType("application/json");

    @Autowired
    private GraphQL graphQL;

    @Bean
    RouterFunction<?> routes() {
        return route(RequestPredicates.GET("/test1/{countryName}"), request ->
                ok().body(journeyService.findByCountryNameWithJPA(request.pathVariable("countryName")), Journey.class));
    }

    /*
    translation from there (without the GET method): 
    https://github.com/geowarin/graphql-webflux/blob/master/src/main/kotlin/com/geowarin/graphql/Routes.kt
     */
    @Bean
    RouterFunction<?> routesGraphQl() {
        //a working query :
        //{allJourney {country}}
        return route(RequestPredicates.POST("/graphql"), request -> {
            if (request.headers().contentType().filter(mediaType -> mediaType.isCompatibleWith(GraphQLMediaType)).isPresent()) {
                return request.bodyToMono(GraphQLParameter.class)
                        .flatMap(graphQLParameter -> {
                            ExecutionInput.Builder executionInput = newExecutionInput()
                                    .query(graphQLParameter.getQuery())
                                    .operationName(graphQLParameter.getOperationName())
                                    .variables(graphQLParameter.getVariables());
                            return fromFuture(graphQL.executeAsync(executionInput));
                        })
                        .flatMap(executionResult -> ok().syncBody(executionResult))
                        .switchIfEmpty(badRequest().build());
            } else {
                return badRequest().build();
            }
        });
    }

    @Bean
    RouterFunction<?> routeWithAnnotationHateoasWithAssembler() {
        JourneyResourceAssembler assembler = new JourneyResourceAssembler();
        return route(RequestPredicates.GET("/test2/{destination}"), request ->
                ok().body(journeyService.findByDestinationWithMongoRepo(request.pathVariable("destination"))
                        .map(assembler::toResource), JourneyResource.class));
    }

    @Bean
    RouterFunction<?> routeWithAnnotationHateoasWithoutAssembler() {
        return route(RequestPredicates.GET("/test3/{destination}"), request ->
                ok().contentType(MediaType.APPLICATION_JSON).body(journeyService.findByDestinationWithMongoRepo(request.pathVariable("destination"))
                        .map(this::journeyToResource), Resource.class));
    }


    @Bean
    RouterFunction<?> routeWithAnnotationHateoasAndGraphQL() {
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("graphQLSchemas/journey.graphqls")
                .resolvers(new QueryResolver(journeyService), new JourneyResolver())
                .build()
                .makeExecutableSchema();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{allJourney {country}}");
        log.debug(executionResult.getData().toString());
        return route(RequestPredicates.GET("/graphql"), request ->
                ok().body(Mono.just(executionResult.getData().toString()), String.class));
    }

    /**
     * https://exampledriven.wordpress.com/2015/11/13/spring-hateoas-example/
     *
     * @param journey: The Journey that we want to transform into a HATEOAS resource
     * @return A Journey with the properties of a Resource HATEAOS such as the links
     */
    private Resource<Journey> journeyToResource(Journey journey) {

        //TODO: code the links in order to have a proper HATEOAS Restful API
//                Link invoiceLink = linkTo(methodOn(FunctionnalController.class).routeWithAnnotationHateoas(journey.getId()+"")).withRel("invoice");

//        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoice");
//        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        Resource<Journey> journeyResource = new Resource<>(journey);
        return journeyResource;

    }
}
