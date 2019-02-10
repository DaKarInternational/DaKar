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

    /**
     * Get all journeys
     * @return
     */
    Flux<Journey> findAll();

    /**
     * Get first journey by destination
     * @return
     */
    Flux<Journey> findFirstByDestination(String destination);

    /**
     * Get journeys by destination
     * @return
     */
    Flux<Journey> findByDestination(String destination);

    /**
     * Get first journey by price
     * @return
     */
    Flux<Journey> findFirstByPrice(String price);

    /**
     * Get journeys by price
     * @return
     */
    Flux<Journey> findByPrice(String price);

    /**
     * Get journeys by destination and price
     * @return
     */
    Flux<Journey> findByDestinationAndPrice(String destination, String Price);

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
