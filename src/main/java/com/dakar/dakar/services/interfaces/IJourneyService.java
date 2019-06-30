package com.dakar.dakar.services.interfaces;

import com.dakar.dakar.models.Journey;
import com.dakar.dakar.models.JourneyCriteriaInput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IJourneyService {
    Flux<Journey> findByDestination(String countryName);

    Flux<Journey> allJourney();

    Flux<Journey> saveJourney(Mono<Journey> journey);

    Mono<Journey> findById(String id);

    Mono<Void> deleteJourney(String id);

    Flux<Journey> findByCriterias(JourneyCriteriaInput criterias);

//    void fillDbWithDumbData();
}
