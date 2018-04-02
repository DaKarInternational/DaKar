package com.dakar.dakar.Controller;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.ResourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.Resources.JourneyResource;
import com.dakar.dakar.Services.JourneyService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

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

    @RequestMapping(value = "/test4/{id}", produces = MediaTypes.ALPS_JSON_VALUE)
    Mono<String> routeWithAnnotationHateoasAndGraphQL(@PathVariable(value = "id") String id) throws FileNotFoundException {

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(ResourceUtils.getFile("classpath:GraphQLSchemas/Journey.graphqls"));

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        ExecutionResult executionResult = build.execute("{hello}");

        System.out.println(executionResult.getData().toString());
        // Prints: {hello=world}
        return Mono.just(executionResult.getData().toString());
    }

    /**
     * https://exampledriven.wordpress.com/2015/11/13/spring-hateoas-example/
     * @param journey
     * @return
     */
    private Resource<Journey> journeyToResource(Journey journey) {
//        Link invoiceLink = linkTo(methodOn(AnnotationController.class).routeWithAnnotationHateoas(journey.getId()+"")).withRel("invoice");

//        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoice");
//        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        Resource<Journey> journeyResource = new Resource<>(journey);
        return journeyResource;

    }
}
