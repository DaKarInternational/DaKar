package com.dakar.dakar.services.interfaces;

import com.dakar.dakar.models.Journey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IJourneyService {
    Mono<Journey> findByDestinationWithJPA(String countryName);

    Mono<Journey> findByDestinationWithMongoRepo(String destination);

    Flux<Journey> allJourney();

    Flux<Journey> saveJourney(Mono<Journey> journey);

    void fillDbWithDumbData();

    public Mono<Journey> findByCountry(String country);

}
