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

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid  @RequestBody FlightRequest flightRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(airlineId,flightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFLightById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @RequestBody FlightRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(
            @PathVariable Long id,
            @RequestHeader("X-Airline-Id") Long airlineId
    ) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.noContent().build();
    }
}
