package com.aryan.controller;

import com.aryan.payload.request.FlightScheduleRequest;
import com.aryan.payload.response.FlightScheduleResponse;
import com.aryan.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for flight schedule management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * deleting, and listing flight schedules by airline.
 *
 * Base URL: /api/schedules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class FlightScheduleController {

    private FlightScheduleService flightScheduleService;

    /**
     * Creates a new flight schedule and generates flight instances
     * for each operating day within the schedule date range.
     *
     * @param airlineId authenticated airline identifier
     * @param request flight schedule details
     * @return created flight schedule
     * @throws Exception if the flight is not found
     */
    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request
    ) throws Exception{
        // todo: watch for airlineId
        return ResponseEntity.status(HttpStatus.CREATED).body(flightScheduleService.createFlightSchedule(airlineId,request));
    }

    /**
     * Retrieves a flight schedule by its identifier.
     *
     * @param id flight schedule identifier
     * @return flight schedule details
     * @throws Exception if the schedule is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));
    }

    /**
     * Retrieves all flight schedules for the specified airline.
     *
     * @param airlineId authenticated airline identifier
     * @return list of flight schedules
     */
    @GetMapping
    public ResponseEntity<List<FlightScheduleResponse>> getFlightSchedules(
            @RequestHeader("X-Airline-Id") Long airlineId
    ){
        // todo: watch for airlineId
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleByAirline(airlineId));
    }

    /**
     * Updates an existing flight schedule.
     *
     * @param id flight schedule identifier
     * @param request updated schedule details
     * @return updated flight schedule
     * @throws Exception if the schedule is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest request
    ) throws Exception{
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id,request));
    }


    /**
     * Deletes a flight schedule.
     *
     * @param id flight schedule identifier
     * @return no content
     * @throws Exception if the schedule is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> deleteFlightSchedule(
            @PathVariable Long id
    ) throws Exception{
        flightScheduleService.deleteFlightSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
