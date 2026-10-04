package com.aryan.controller;

import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.ApiResponse;
import com.aryan.payload.response.FlightInstanceResponse;
import com.aryan.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller for flight instance management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * deleting, and filtering flight instances.
 *
 * Base URL: /api/flight-instances
 */
@RestController
@RequestMapping("/api/flight-instances")
@RequiredArgsConstructor
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;

    /**
     * Creates a new flight instance for the specified airline.
     *
     * @param airlineId authenticated airline identifier
     * @param request flight instance details
     * @return created flight instance
     * @throws Exception if the flight is not found
     */
    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightInstanceService.createFlightInstance(airlineId,request));
    }

    /**
     * Retrieves a flight instance by its identifier.
     *
     * @param id flight instance identifier
     * @return flight instance details
     * @throws Exception if the flight instance is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));
    }

    /**
     * Retrieves a paginated list of flight instances for the specified airline.
     *
     * Supports optional filtering by departure airport, arrival airport,
     * flight, and departure date.
     *
     * @param airlineId authenticated airline identifier
     * @param departureAirportId optional departure airport filter
     * @param arrivalAirportId optional arrival airport filter
     * @param flightId optional flight filter
     * @param onDate optional departure date filter
     * @param pageable pagination information
     * @return paginated flight instance list
     * @throws Exception if the airline is not found
     */
    @GetMapping
    public ResponseEntity<Page<FlightInstanceResponse>> getFlightInstances(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            Pageable pageable
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstancesByAirlineId(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                onDate,
                pageable
        ));
    }

    /**
     * Updates an existing flight instance.
     *
     * @param id flight instance identifier
     * @param request updated flight instance details
     * @return updated flight instance
     * @throws Exception if the flight instance is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            FlightInstanceRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id,request));
    }

    /**
     * Deletes a flight instance.
     *
     * @param id flight instance identifier
     * @return success response
     * @throws Exception if the flight instance is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(
            @PathVariable Long id
    ) throws Exception {
        flightInstanceService.deleteFlightInstance(id);
        ApiResponse response = new ApiResponse("Flight Instance deleted successfully.");
        return ResponseEntity.ok(response);
    }

}
