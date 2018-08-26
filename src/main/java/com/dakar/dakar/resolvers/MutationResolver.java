package com.dakar.dakar.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyInput;
import com.dakar.dakar.services.interfaces.IJourneyService;
import reactor.core.publisher.Mono;

public class MutationResolver implements GraphQLMutationResolver {

    // no autowired here
    private IJourneyService journeyService;

    //instead we use the old fashion
    public MutationResolver(IJourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public Journey createJourney(JourneyInput journeyInput) {
        Journey journeyCreated = new Journey(journeyInput.getPrice(),journeyInput.getDestination());
        Mono<Journey> journeyMono = Mono.just(journeyCreated);
        return journeyService.saveJourney(journeyMono).blockFirst();
    }

}
