package com.aryan.repository;

import com.aryan.model.Airport;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for airport persistence operations.
 *
 * Provides CRUD functionality and airport-specific
 * database queries.
 */
public interface AirportRepository extends JpaRepository<Airport, Long> {

    /**
     * Retrieves an airport using its unique IATA code.
     *
     * @param iataCode airport IATA code
     * @return matching airport or null if not found
     */
    Optional<Airport> findByIataCode(String iataCode);

    /**
     * Retrieves all airports associated with a specific city.
     *
     * @param cityId unique city identifier
     * @return list of airports belonging to the city
     */
    List<Airport> findByCityId(Long cityId);

    /**
     * Checks whether an airport exists for the provided IATA code.
     *
     * @param iataCode airport IATA code
     * @return true if airport exists, otherwise false
     */
    boolean existsByIataCode(String iataCode);
}