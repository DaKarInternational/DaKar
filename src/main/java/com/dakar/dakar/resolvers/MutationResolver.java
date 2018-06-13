package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyInput;
import com.dakar.dakar.services.JourneyService;

public class MutationResolver implements GraphQLMutationResolver {

    // no autowired here
    private JourneyService journeyService;

    //instead we use the old fashion
    public MutationResolver(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public Journey createJourney(JourneyInput journeyInput) {
        Journey journeyCreated = new Journey();
        journeyCreated.setPrice(journeyInput.getPrice());
        journeyCreated.setCountry(journeyInput.getCountry());
        return journeyService.insertNewJourney(journeyCreated);
    }

}
