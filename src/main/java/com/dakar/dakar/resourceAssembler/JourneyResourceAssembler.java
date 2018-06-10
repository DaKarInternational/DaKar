package com.dakar.dakar.resourceAssembler;

import com.dakar.dakar.controller.FunctionnalController;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.resources.JourneyResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class JourneyResourceAssembler  extends ResourceAssemblerSupport<Journey, JourneyResource> {

    public JourneyResourceAssembler() {
        super(FunctionnalController.class, JourneyResource.class);
    }

    @Override
    public JourneyResource toResource(Journey journey) {
        JourneyResource resource = createResourceWithId(journey.getDestination(), journey);
        resource.setJourney(journey);
        return resource;
    }
}