package com.dakar.dakar.Repositories;

import com.dakar.dakar.Models.Journey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends CrudRepository<Journey, Long> {

    Journey findFirstByCountry(String countryName);

    Journey findFirstById(int id);

}
