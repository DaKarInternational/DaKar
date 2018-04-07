package com.dakar.dakar.Repositories;

import com.dakar.dakar.Models.Journey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends CrudRepository<Journey, Long> {

    Journey findFirstByCountry(String countryName);

    Journey findFirstById(int id);

    List<Journey> findAll();

}
