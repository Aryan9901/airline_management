package com.aryan.controller;

import com.aryan.payload.request.AirportRequest;
import com.aryan.payload.response.ApiResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.service.AirportService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for airport-related operations.
 *
 * Provides APIs for:
 * - Creating airports
 * - Fetching airport details
 * - Updating airports
 * - Deleting airports
 * - Retrieving airports by city
 *
 * Base URL: /api/airports
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    /**
     * Service responsible for airport business operations.
     */
    private final AirportService airportService;

    /**
     * Creates a new airport.
     *
     * @param request airport creation payload
     * @return created airport response
     * @throws Exception if validation fails or airport already exists
     */
    @PostMapping
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest request
    ) throws Exception {

        AirportResponse response =
                airportService.createAirport(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves airport details using airport ID.
     *
     * @param id airport identifier
     * @return airport response
     * @throws Exception if airport does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(
            @PathVariable Long id
    ) throws Exception {

        AirportResponse response =
                airportService.getAirportById(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all airports.
     *
     * @return list of airport responses
     */
    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports() {

        return ResponseEntity.ok(
                airportService.getAllAirports()
        );
    }

    /**
     * Updates an existing airport.
     *
     * @param id airport identifier
     * @param request airport update payload
     * @return updated airport response
     * @throws Exception if airport does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(
            @PathVariable Long id,
            @Valid @RequestBody AirportRequest request
    ) throws Exception {

        AirportResponse response =
                airportService.updateAirport(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an airport.
     *
     * @param id airport identifier
     * @return success response
     * @throws Exception if airport does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(
            @PathVariable Long id
    ) throws Exception {

        airportService.deleteAirport(id);

        return ResponseEntity.ok(
                new ApiResponse(
                        "Airport deleted successfully"
                )
        );
    }

    /**
     * Retrieves all airports associated with a city.
     *
     * @param cityId city identifier
     * @return list of airport responses
     */
    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportsByCity(
            @PathVariable Long cityId
    ) {

        return ResponseEntity.ok(
                airportService.getAirportByCityId(cityId)
        );
    }
}