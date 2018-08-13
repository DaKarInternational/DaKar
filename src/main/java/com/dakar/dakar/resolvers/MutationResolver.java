package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyInput;
import com.dakar.dakar.services.JourneyService;
import reactor.core.publisher.Mono;
import com.dakar.dakar.services.interfaces.IJourneyService;

public class MutationResolver implements GraphQLMutationResolver {

    // no autowired here
    private IJourneyService journeyService;

    //instead we use the old fashion
    public MutationResolver(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public Journey createJourney(JourneyInput journeyInput) {
        Journey journeyCreated = new Journey();
        Mono<Journey> journeyMono = Mono.just(journeyCreated);
        journeyCreated.setPrice(journeyInput.getPrice());
        journeyCreated.setDestination(journeyInput.getCountry());
        return journeyService.insertNewJourneyMongo(journeyMono).blockFirst();
    }

}
