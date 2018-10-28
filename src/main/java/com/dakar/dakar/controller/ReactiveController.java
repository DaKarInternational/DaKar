package com.dakar.dakar.controller;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.resourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.resources.JourneyResource;
import com.dakar.dakar.services.interfaces.IJourneyService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Controller
public class ReactiveController {

    private MediaType GraphQLMediaType = MediaType.parseMediaType("application/json");

    @Autowired
    private IJourneyService journeyService;

    @Autowired
    private GraphQL graphQL;

    @Autowired
    private MessageSource messageSource;

    /**
     * DEMO
     * classic endpoint returning a Mono<Journey>
     */
    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(RequestPredicates.GET("/test1/{destination}"), request ->
                ok().body(journeyService.findByDestination(request.pathVariable("destination")), Journey.class));
    }

    /**
     * Endpoint pour tester l'i18n
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> routeWelcome() {
        return route(RequestPredicates.GET("/welcome/{locale}/{name}"), request -> {
                    Locale locale;
                    if("fr".equals(request.pathVariable("locale"))){
                        locale = Locale.FRANCE;
                    } else {
                        locale = null;
                    }
                    return ok().body(BodyInserters.fromObject(messageSource.getMessage("message.welcome", new Object [] {request.pathVariable("name")}, locale)));
        });
    }

/*    @Bean
    RouterFunction<?> routesBis() {
        RouterFunction<?> routerFunction = route(RequestPredicates.GET("/test6/{destination}"), request ->
                ok().body(journeyService.findByDestination(request.pathVariable("destination")), Journey.class));
       routerFunction.a  route(RequestPredicates.GET("/test1/{destination}"), request ->
                ok().body(journeyService.findByDestination(request.pathVariable("destination")), Journey.class));
        return route
    }*/


    /**
     * DEMO
     * first way to map a Journey into a Hatoas Resource
     */
    @Bean
    RouterFunction<ServerResponse> routeWithAnnotationHateoasWithAssembler() {
        JourneyResourceAssembler assembler = new JourneyResourceAssembler();
        return route(RequestPredicates.GET("/test2/{destination}"), request ->
                ok().body(journeyService.findByDestination(request.pathVariable("destination"))
                        .map(assembler::toResource), JourneyResource.class));
    }

    /**
     * DEMO
     * second way to map a Journey into a Hatoas Resource
     */
    @Bean
    RouterFunction<ServerResponse> routeWithAnnotationHateoasWithoutAssembler() {
        return route(RequestPredicates.GET("/test3/{destination}"), request ->
                ok().contentType(MediaType.APPLICATION_JSON).body(journeyService.findByDestination(request.pathVariable("destination"))
                        .map(this::journeyToResource), Resource.class));
    }

    /**
     * DEMO
     * classic save endpoint returning the new Journey in a Flux
     */
    @Bean
    RouterFunction<ServerResponse> routeForCouch() {
        String id = UUID.randomUUID().toString();
        return route(RequestPredicates.POST("/test5"), request ->
                ok().body(journeyService.saveJourney(Mono.just(new Journey(id, "afghanistan", "afghanistan", ""))), Journey.class));
    }

    /**
     * DEMO
     * This way you can transform a Graphql request into a classic endpoint
     */
    @Bean
    RouterFunction<ServerResponse> routeWithAnnotationAndGraphQL() {
        ExecutionResult executionResult = this.graphQL.execute("{allJourney {destination}}");
        log.debug(executionResult.getData().toString());
        return route(RequestPredicates.GET("/graphqlEndpointTransformed"), request ->
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
//                Link invoiceLink = linkTo(methodOn(ReactiveController.class).routeWithAnnotationHateoas(journey.getId()+"")).withRel("invoice");

//        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoice");
//        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        Resource<Journey> journeyResource = new Resource<>(journey);
        return journeyResource;

    }
}
