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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class FlightScheduleController {

    private FlightScheduleService flightScheduleService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request
    ) throws Exception{
        // todo: watch for airlineId
        return ResponseEntity.status(HttpStatus.CREATED).body(flightScheduleService.createFlightSchedule(airlineId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<List<FlightScheduleResponse>> getFlightSchedules(
            @RequestHeader("X-Airline-Id") Long airlineId
    ){
        // todo: watch for airlineId
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleByAirline(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest request
    ) throws Exception{
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id,request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> deleteFlightSchedule(
            @PathVariable Long id
    ) throws Exception{
        flightScheduleService.deleteFlightSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
