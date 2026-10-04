package com.aryan.repository;

import com.aryan.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for managing {@link Flight} entities.
 *
 * Provides custom query methods for flight retrieval
 * and validation.
 */
public interface FlightRepository  extends JpaRepository<Flight,Long> {

    /**
     * Retrieves a paginated list of flights for an airline,
     * with optional filtering by departure and arrival airport.
     *
     * @param airlineId airline identifier
     * @param depId optional departure airport filter
     * @param arrId optional arrival airport filter
     * @param pageable pagination information
     * @return paginated flight list
     */
    @Query("""
            select f from Flight f
            where f.airlineId =:airlineId
            and (:depId is null or f.departureAirportId =:depId)
            and (:arrId is null or f.arrivalAirportId =:arrId)
    """)
    Page<Flight> findByAirlineId(
            @Param("airlineId") Long airlineId,
            @Param("depId") long depId,
            @Param("arrId") long arrId,
            Pageable pageable
    );

    /**
     * Checks whether a flight with the given number exists.
     *
     * @param flightNumber flight number
     * @return true if the flight exists
     */
    boolean existsByFlightNumber(String flightNumber);

    /**
     * Checks whether a flight with the given number exists,
     * excluding the specified flight identifier.
     *
     * @param flightNumber flight number
     * @param id flight identifier to exclude
     * @return true if a conflicting flight exists
     */
    boolean existsByFlightNumberAndIdNot(String flightNumber, Long id);

    /**
     * Retrieves a flight by airline and flight identifier.
     *
     * @param airlineId airline identifier
     * @param id flight identifier
     * @return matching flight, if found
     */
    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);
}
