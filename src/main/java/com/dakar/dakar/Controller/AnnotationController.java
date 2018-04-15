package com.dakar.dakar.Controller;

import com.coxautodev.graphql.tools.SchemaParser;
import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Resolvers.JourneyResolver;
import com.dakar.dakar.Resolvers.QueryResolver;
import com.dakar.dakar.ResourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.Resources.JourneyResource;
import com.dakar.dakar.Services.JourneyService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class AnnotationController {

    @Autowired
    private JourneyService journeyService;

    @RequestMapping("/test2/{id}")
    Mono<JourneyResource> routeWithAnnotationHateoasWithAssembler(@PathVariable(value = "id") String id){
        JourneyResourceAssembler assembler = new JourneyResourceAssembler();
        return journeyService.findByIdWithJPA(id)
                .map(assembler::toResource);
    }

    @RequestMapping(value = "/test3/{id}", produces = MediaTypes.ALPS_JSON_VALUE)
    Mono<Resource<Journey>> routeWithAnnotationHateoasWithoutAssembler(@PathVariable(value = "id") String id){
        return journeyService.findByIdWithJPA(id)
                .map(this::journeyToResource);
    }

    @RequestMapping(value = "/graphql")
    Mono<String> routeWithAnnotationHateoasAndGraphQL()  {
        GraphQLSchema graphQLSchema = SchemaParser.newParser()
                .file("GraphQLSchemas/journey.graphqls")
                .resolvers(new QueryResolver(journeyService), new JourneyResolver())
                .build()
                .makeExecutableSchema();
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{allJourney {country}}");
        log.debug(executionResult.getData().toString());
        return Mono.just(executionResult.getData().toString());
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
