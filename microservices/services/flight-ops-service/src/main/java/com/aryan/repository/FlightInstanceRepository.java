package com.aryan.repository;

import com.aryan.model.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing {@link FlightInstance} entities.
 *
 * Provides custom query methods for flight instance
 * retrieval with multi-criteria filtering.
 */
public interface FlightInstanceRepository extends JpaRepository<FlightInstance,Long> {

    /**
     * Retrieves a paginated list of flight instances for an airline,
     * with optional filtering by airports, flight, and date range.
     *
     * @param airlineId airline identifier
     * @param departureAirportId optional departure airport filter
     * @param arrivalAirportId optional arrival airport filter
     * @param flightId optional flight filter
     * @param dayStart optional start of departure date range
     * @param dayEnd optional end of departure date range
     * @param pageable pagination information
     * @return paginated flight instance list
     */
    @Query("""
        select fi from FlightInstance fi
        where fi.airlineId=:airlineId
        and (:departureAirportId is null or fi.departureAirportId = :departureAirportId)
        and (:arrivalAirportId is null or fi.arrivalAirportId = :arrivalAirportId)
        and (:flightId is null or fi.flight.id = :flightId)
        and (:dayStart is null or fi.departureDatetime >= :dayStart)
    """)
    Page<FlightInstance> findByAirlineId(
            @Param("airlineId") Long airlineId,
            @Param("departureAirportId") Long departureAirportId,
            @Param("arrivalAirportId") Long arrivalAirportId,
            @Param("flightId") Long flightId,
            @Param("dayStart") LocalDateTime dayStart,
            @Param("dayEnd") LocalDateTime dayEnd,
            Pageable pageable
    );

}
