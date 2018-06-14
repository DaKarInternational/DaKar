package com.dakar.dakar.controller;

import com.coxautodev.graphql.tools.SchemaParser;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.resolvers.JourneyResolver;
import com.dakar.dakar.resolvers.QueryResolver;
import com.dakar.dakar.resourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.resources.JourneyResource;
import com.dakar.dakar.services.JourneyService;
import com.dakar.dakar.services.interfaces.IJourneyService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
public class AnnotationController {

    @Autowired
    private IJourneyService journeyService;

    @RequestMapping("/test2/{destination}")
    Mono<JourneyResource> routeWithAnnotationHateoasWithAssembler(@PathVariable(value = "destination") String destination){
        JourneyResourceAssembler assembler = new JourneyResourceAssembler();
        return journeyService.findByDestinationWithMongoRepo(destination)
                .map(assembler::toResource);
    }

    @RequestMapping(value = "/test3/{destination}", produces = MediaTypes.ALPS_JSON_VALUE)
    Mono<Resource<Journey>> routeWithAnnotationHateoasWithoutAssembler(@PathVariable(value = "destination") String destination){
        return journeyService.findByDestinationWithMongoRepo(destination)
                .map(this::journeyToResource);
    }

    @RequestMapping(value = "/allJourney")
    List<Journey> getAllJourney(){
        return journeyService.allJourney();
    }

    @RequestMapping(value = "/allJourneyFlux")
    Flux<Journey> getAllJourneyAsFlux(){
        return journeyService.allJourneyAsFlux();
    }

    @RequestMapping(value = "/journey", method = RequestMethod.POST)
    Mono<Journey> saveJourney(@RequestBody Mono<Journey> journey){
        return journeyService.saveJourney(journey);
    }

    @RequestMapping(value = "/graphql")
    String routeWithAnnotationHateoasAndGraphQL()  {
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("graphQLSchemas/journey.graphqls")
                .resolvers(new QueryResolver(journeyService), new JourneyResolver())
                .build()
                .makeExecutableSchema();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{allJourney {country}}");
        log.debug(executionResult.getData().toString());
        return executionResult.getData().toString();
    }

    /**
     * https://exampledriven.wordpress.com/2015/11/13/spring-hateoas-example/
     * @param journey: The Journey that we want to transform into a HATEOAS resource
     * @return A Journey with the properties of a Resource HATEAOS such as the links
     */
    private Resource<Journey> journeyToResource(Journey journey) {

        //TODO: code the links in order to have a proper HATEOAS Restful API
        //        Link invoiceLink = linkTo(methodOn(AnnotationController.class).routeWithAnnotationHateoas(journey.getId()+"")).withRel("invoice");

//        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoice");
//        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        Resource<Journey> journeyResource = new Resource<>(journey);
        return journeyResource;

    }
}
