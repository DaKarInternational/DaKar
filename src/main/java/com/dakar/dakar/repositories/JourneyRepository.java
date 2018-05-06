package com.dakar.dakar.repositories;

import com.dakar.dakar.models.Journey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends MongoRepository<Journey, Long> {

    Journey findFirstByCountry(String countryName);

    List<Journey> findAll();

    Journey findFirstByDestination(String destination);
}
