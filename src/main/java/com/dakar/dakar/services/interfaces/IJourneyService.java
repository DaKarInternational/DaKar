package com.dakar.dakar.services.interfaces;

import com.dakar.dakar.models.Journey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IJourneyService {
    Mono<Journey> findByDestinationWithJPA(String countryName);

    Mono<Journey> findByDestinationWithMongoRepo(String destination);

    List<Journey> allJourney();

    Journey findById(String id);

    Flux<Journey> allJourneyAsFlux();

    Mono<Journey> saveJourney(Mono<Journey> journey);

    Journey insertJourney(Journey journey);

    Mono<Journey> findByIdWithMongoRepo(String destination);
}
