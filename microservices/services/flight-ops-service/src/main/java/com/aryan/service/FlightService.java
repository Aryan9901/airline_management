package com.aryan.service;

import com.aryan.enums.FlightStatus;
import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service contract for flight management operations.
 */
public interface FlightService {

    /**
     * Creates a new flight for the specified airline.
     *
     * @param airlineId airline identifier
     * @param flightRequest flight details
     * @return created flight
     * @throws Exception if the flight number already exists
     */
    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception;

    /**
     * Retrieves a paginated list of flights for the specified airline.
     *
     * @param airlineId airline identifier
     * @param departureAirportId optional departure airport filter
     * @param arrivalAirportId optional arrival airport filter
     * @param pageable pagination information
     * @return paginated flight list
     */
    Page<FlightResponse> getFlightsByAirline(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Pageable pageable
    );

    /**
     * Retrieves a flight by its identifier.
     *
     * @param id flight identifier
     * @return flight details
     * @throws Exception if the flight is not found
     */
    FlightResponse getFlightById(Long id) throws Exception;

    /**
     * Updates an existing flight.
     *
     * @param id flight identifier
     * @param flightRequest updated flight details
     * @return updated flight
     * @throws Exception if the flight is not found
     */
    FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception;

    /**
     * Changes the operational status of a flight.
     *
     * @param id flight identifier
     * @param status new flight status
     * @return updated flight
     * @throws Exception if the flight is not found
     */
    FlightResponse changeStatus(Long id, FlightStatus status) throws Exception;

    /**
     * Deletes a flight belonging to the specified airline.
     *
     * @param airlineId airline identifier
     * @param id flight identifier
     * @throws Exception if the flight is not found
     */
    void deleteFlight(Long airlineId, Long id) throws Exception;
}
