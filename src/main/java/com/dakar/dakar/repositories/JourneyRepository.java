package com.dakar.dakar.repositories;

import com.dakar.dakar.models.Journey;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface JourneyRepository extends ReactiveMongoRepository<Journey, String> {

    Flux<Journey> findAll();

    Mono<Journey> findFirstByDestination(String destination);


}
