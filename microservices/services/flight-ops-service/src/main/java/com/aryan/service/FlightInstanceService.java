package com.aryan.service;

import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Service contract for flight instance management operations.
 */
public interface FlightInstanceService {

    /**
     * Creates a new flight instance for the specified airline.
     *
     * @param airlineId airline identifier
     * @param request flight instance details
     * @return created flight instance
     * @throws Exception if the flight is not found
     */
    FlightInstanceResponse createFlightInstance(
            Long airlineId,
            FlightInstanceRequest request
    ) throws Exception;

    /**
     * Retrieves a flight instance by its identifier.
     *
     * @param id flight instance identifier
     * @return flight instance details
     * @throws Exception if the flight instance is not found
     */
    FlightInstanceResponse getFlightInstanceById(Long id) throws Exception;

    /**
     * Retrieves a paginated list of flight instances for the specified airline.
     *
     * Supports optional filtering by departure airport, arrival airport,
     * flight, and departure date.
     *
     * @param airlineId airline identifier
     * @param departureAirportid optional departure airport filter
     * @param arrivalAirportId optional arrival airport filter
     * @param flightId optional flight filter
     * @param onDate optional departure date filter
     * @param pageable pagination information
     * @return paginated flight instance list
     */
    Page<FlightInstanceResponse> getFlightInstancesByAirlineId(
            Long airlineId,
            Long departureAirportid,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable
    );

    /**
     * Updates an existing flight instance.
     *
     * @param id flight instance identifier
     * @param request updated flight instance details
     * @return updated flight instance
     * @throws Exception if the flight instance is not found
     */
    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception;

    /**
     * Deletes a flight instance.
     *
     * @param id flight instance identifier
     * @throws Exception if the flight instance is not found
     */
    void deleteFlightInstance(Long id) throws Exception;

}
