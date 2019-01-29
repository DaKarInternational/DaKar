package com.dakar.dakar.repositories;

import com.dakar.dakar.models.Journey;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "journey")
public interface JourneyRepository extends ReactiveCouchbaseRepository<Journey, String> {

    Flux<Journey> findAll();

    Flux<Journey> findFirstByDestination(String destination);

    Flux<Journey> findFirstByPrice(String price);

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
