package com.dakar.dakar.Controller;

import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.ResourceAssembler.JourneyResourceAssembler;
import com.dakar.dakar.Resources.JourneyResource;
import com.dakar.dakar.Services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

    private Resource<Journey> journeyToResource(Journey journey) {
//        Link invoiceLink = linkTo(methodOn(AnnotationController.class).routeWithAnnotationHateoas(journey.getId()+"")).withRel("invoice");

//        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoice");
//        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        Resource<Journey> journeyResource = new Resource<>(journey);
        return journeyResource;

    }
}
