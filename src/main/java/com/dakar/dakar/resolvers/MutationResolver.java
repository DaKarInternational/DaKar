package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyInput;
import com.dakar.dakar.services.interfaces.IJourneyService;

public class MutationResolver implements GraphQLMutationResolver {

    // no autowired here
    private IJourneyService journeyService;

    //instead we use the old fashion
    public MutationResolver(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public Journey createJourney(JourneyInput journey) {
        Journey journeyCreated = new Journey();
        journeyCreated.setDestination(journey.getDestination());
        journeyCreated.setPrice(journey.getPrice());
        return journeyService.insertJourney(journeyCreated);
    }

}
