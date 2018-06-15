package com.dakar.dakar.services.interfaces;

import com.dakar.dakar.models.Journey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IJourneyService {
    Mono<Journey> findByCountryNameWithJPA(String countryName);

    Mono<Journey> findByDestinationWithMongoRepo(String destination);

    List<Journey> allJourney();

    Flux<Journey> allJourneyAsFlux();

    Mono<Journey> saveJourney(Mono<Journey> journey);

    Journey insertNewJourney(Journey journey);

    void fillDbWithDumbData();
}
