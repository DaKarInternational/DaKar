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

    /**
     * Create a Journey
     * @param journeyInput
     * @return The newly created Journey, with an ID affected
     */
    public Journey createJourney(JourneyInput journeyInput) {
        Journey journeyCreated = new Journey(null, journeyInput.getPrice(), journeyInput.getDestination(), "");
        Mono<Journey> journeyMono = Mono.just(journeyCreated);
        return journeyService.saveJourney(journeyMono).blockFirst();
    }

    /**
     * Update a Journey
     *
     * @param journey
     * @return The newly updated Journey
     */
    public Journey updateJourney(JourneyInput journey) {
        Journey journeyCreated = new Journey(journey.getId(), journey.getPrice(), journey.getDestination(), "");
        Mono<Journey> journeyFound = journeyService.findById(journey.getId())
                .map((Journey it) -> {
                    it.setDestination(journey.getDestination());
                    it.setOwner(journey.getOwner());
                    it.setPrice(journey.getPrice());
                    return it;
                });
        return journeyService.saveJourney(journeyFound).blockFirst();
    }

    /**
     * Delete a journey
     * @param id
     */
    public void deleteJourney(String id){
        this.journeyService.deleteJourney(id).block();
    }

}
