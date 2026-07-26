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

@RestController
@RequestMapping("/api/flight-instances")
@RequiredArgsConstructor
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightInstanceService.createFlightInstance(airlineId,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            FlightInstanceRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(
            @PathVariable Long id
    ) throws Exception {
        flightInstanceService.deleteFlightInstance(id);
        ApiResponse response = new ApiResponse("Flight Instance deleted successfully.");
        return ResponseEntity.ok(response);
    }

}
