package com.aryan.controller;

import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.response.FlightResponse;
import com.aryan.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for flight management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * deleting, and filtering flights by airline.
 *
 * Base URL: /api/flights
 */
@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    /**
     * Creates a new flight for the specified airline.
     *
     * @param flightRequest flight details
     * @param airlineId authenticated airline identifier
     * @return created flight
     * @throws Exception if the flight number already exists
     */
    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid  @RequestBody FlightRequest flightRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(airlineId,flightRequest));
    }

    /**
     * Retrieves a flight by its identifier.
     *
     * @param id flight identifier
     * @return flight details
     * @throws Exception if the flight is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFLightById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    /**
     * Retrieves a paginated list of flights for the specified airline.
     *
     * Supports optional filtering by departure and arrival airport.
     *
     * @param airlineId authenticated airline identifier
     * @param departureAirportId optional departure airport filter
     * @param arrivalAirportId optional arrival airport filter
     * @param pageable pagination information
     * @return paginated flight list
     * @throws Exception if the airline is not found
     */
    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(
            @RequestHeader("X-Airline-Id") long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable
    ) throws Exception{
        return ResponseEntity.ok(flightService.getFlightsByAirline(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                pageable
        ));
    }

    /**
     * Updates an existing flight.
     *
     * @param id flight identifier
     * @param request updated flight details
     * @return updated flight
     * @throws Exception if the flight is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody FlightRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    /**
     * Deletes a flight belonging to the specified airline.
     *
     * @param id flight identifier
     * @param airlineId authenticated airline identifier
     * @return no content
     * @throws Exception if the flight is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @PathVariable Long id,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.noContent().build();
    }
}
