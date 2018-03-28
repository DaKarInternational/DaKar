package com.dakar.dakar.Repositories;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class JourneyRepository {

    public Mono<Integer> findById(String id) {
        return Mono
                .just(2)
                .as(mono -> Mono.just(Integer.parseInt(id)));
    }
}
