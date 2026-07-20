package com.aryan.controller;

import com.aryan.payload.request.AircraftRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for aircraft management operations.
 *
 * Provides APIs for creating, retrieving,
 * updating, listing, and deleting aircraft.
 *
 * Base URL: /api/aircrafts
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    /**
     * Creates a new aircraft for the authenticated airline owner.
     *
     * @param aircraftRequest aircraft details
     * @param userId authenticated owner identifier
     * @return created aircraft
     * @throws Exception if validation fails or the airline is not found
     */
    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        AircraftResponse aircraftResponse = aircraftService.createAircraft(aircraftRequest, userId);
        return new ResponseEntity<>(aircraftResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves an aircraft by its identifier.
     *
     * @param id aircraft identifier
     * @return aircraft details
     * @throws Exception if the aircraft is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    /**
     * Retrieves all aircraft belonging to the authenticated airline owner.
     *
     * @param userId authenticated owner identifier
     * @return list of aircraft
     * @throws Exception if the airline is not found
     */
    @GetMapping
    public ResponseEntity<List<AircraftResponse>> listAllAircrafts(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAircraftByOwner(userId));
    }

    /**
     * Updates an existing aircraft.
     *
     * @param id aircraft identifier
     * @param aircraftRequest updated aircraft details
     * @param userId authenticated owner identifier
     * @return updated aircraft
     * @throws Exception if the aircraft or airline is not found, or validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @RequestBody AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, aircraftRequest, userId));
    }

    /**
     * Deletes an aircraft belonging to the authenticated airline owner.
     *
     * @param id aircraft identifier
     * @param userId authenticated owner identifier
     * @return response with no content
     * @throws Exception if the aircraft or airline is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        aircraftService.deleteAircraft(id, userId);
        return ResponseEntity.noContent().build();
    }
}
