package com.dakar.dakar.repositories;

import com.dakar.dakar.models.Journey;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
//@N1qlPrimaryIndexed
//@ViewIndexed(designDoc = "journey", viewName = "all")
public interface JourneyRepository extends ReactiveCouchbaseRepository<Journey, Long> {

    Mono<Journey> findFirstByCountry(String countryName);

    Flux<Journey> findAll();

    Mono<Journey> findFirstByDestination(String destination);

    /**
     * Additional custom finder method, backed by a View that indexes
     * the names.
     */
//    @View(designDocument = "user", viewName = "byName")
//    List<Journey> findByLastname(String lastName);

    /**
     * Additional custom finder method, backed by a geospatial view and
     * allowing multi-dimensional queries.
     * You can also query within a Circle or a Polygon.
     */
//    @Dimensional(designDocument = "userGeo", spatialViewName = "byLocation")
//    List<Journey> findByLocationWithin();
}
