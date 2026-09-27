package com.aryan.controller;

import com.aryan.model.Fare;
import com.aryan.payload.request.FareRequest;
import com.aryan.payload.response.FareResponse;
import com.aryan.service.FareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    @PostMapping
    public ResponseEntity<FareResponse> createfare(@Valid @RequestBody FareRequest request) throws Exception {
        return new ResponseEntity(fareService.createFare(request),HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Fare>> getFares(){
        return ResponseEntity.ok(fareService.getFares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFareById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFaresByFlightIdAndCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId
    ) throws Exception{
        return ResponseEntity.ok(fareService.getFaresByFlightIdAndCabinClassId(flightId, cabinClassId));
    }

    @PostMapping("batch-by-ids")
    public ResponseEntity<Map<Long,FareResponse>> getFaresByIds(
            @RequestBody List<Long> ids
    ) throws Exception{
        return ResponseEntity.ok(fareService.getFaresByIds(ids));
    }

    @PostMapping("search")
    public ResponseEntity<Map<Long,FareResponse>> getLowestFarePerFlight(
            @RequestBody List<Long> flightIds,
            @RequestParam Long cabinClassId
    ) throws Exception{
        Map<Long,FareResponse> res = fareService.getLowestFarePerFlight(flightIds,cabinClassId);
        System.out.println("search fare response -------- " + res.toString());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable Long id,
            @Valid @RequestBody FareRequest request
    ) throws Exception {
        return new ResponseEntity(fareService.updateFare(id,request),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFare(
            @PathVariable Long id
    ) throws Exception {
        fareService.deleteFare(id);
        return ResponseEntity.noContent().build();
    }

}
