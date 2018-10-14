package com.dakar.dakar.services.interfaces;

import com.dakar.dakar.models.Journey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IJourneyService {
    Mono<Journey> findByDestination(String countryName);

    Flux<Journey> allJourney();

    Flux<Journey> saveJourney(Mono<Journey> journey);

    Mono<Journey> findById(String id);

    Mono<Void> deleteJourney(String id);
        
//    void fillDbWithDumbData();
}
