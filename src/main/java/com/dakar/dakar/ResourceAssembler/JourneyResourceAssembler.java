package com.dakar.dakar.ResourceAssembler;

import com.dakar.dakar.Controller.AnnotationController;
import com.dakar.dakar.Models.Journey;
import com.dakar.dakar.Resources.JourneyResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class JourneyResourceAssembler  extends ResourceAssemblerSupport<Journey, JourneyResource> {

    public JourneyResourceAssembler() {
        super(AnnotationController.class, JourneyResource.class);
    }

    @Override
    public JourneyResource toResource(Journey journey) {
        JourneyResource resource = createResourceWithId(journey.getId(), journey);
        resource.setJourney(journey);
        return resource;
    }
}