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

/**
 * REST controller for fare management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * deleting, and searching fares.
 *
 * Base URL: /api/fares
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    /**
     * Creates a new fare.
     *
     * @param request fare details
     * @return created fare
     * @throws Exception if a duplicate fare exists
     */
    @PostMapping
    public ResponseEntity<FareResponse> createfare(@Valid @RequestBody FareRequest request) throws Exception {
        return new ResponseEntity(fareService.createFare(request),HttpStatus.CREATED);
    }


    /**
     * Retrieves all fares.
     *
     * @return list of fares
     */
    @GetMapping
    public ResponseEntity<List<Fare>> getFares(){
        return ResponseEntity.ok(fareService.getFares());
    }

    /**
     * Retrieves a fare by its identifier.
     *
     * @param id fare identifier
     * @return fare details
     * @throws Exception if the fare is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFareById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    /**
     * Retrieves fares for a specific flight and cabin class.
     *
     * @param flightId flight identifier
     * @param cabinClassId cabin class identifier
     * @return matching fares
     * @throws Exception if no fares are found
     */
    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFaresByFlightIdAndCabinClassId(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId
    ) throws Exception{
        return ResponseEntity.ok(fareService.getFaresByFlightIdAndCabinClassId(flightId, cabinClassId));
    }

    /**
     * Retrieves fares by a batch of identifiers.
     *
     * @param ids list of fare identifiers
     * @return fares mapped by their identifiers
     * @throws Exception if any fare is not found
     */
    @PostMapping("batch-by-ids")
    public ResponseEntity<Map<Long,FareResponse>> getFaresByIds(
            @RequestBody List<Long> ids
    ) throws Exception{
        return ResponseEntity.ok(fareService.getFaresByIds(ids));
    }

    /**
     * Retrieves the lowest fare per flight for a given cabin class.
     *
     * @param flightIds list of flight identifiers
     * @param cabinClassId cabin class identifier
     * @return lowest fare mapped by flight identifier
     * @throws Exception if no fares are found
     */
    @PostMapping("search")
    public ResponseEntity<Map<Long,FareResponse>> getLowestFarePerFlight(
            @RequestBody List<Long> flightIds,
            @RequestParam Long cabinClassId
    ) throws Exception{
        Map<Long,FareResponse> res = fareService.getLowestFarePerFlight(flightIds,cabinClassId);
        System.out.println("search fare response -------- " + res.toString());
        return ResponseEntity.ok(res);
    }

    /**
     * Updates an existing fare.
     *
     * @param id fare identifier
     * @param request updated fare details
     * @return updated fare
     * @throws Exception if the fare is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable Long id,
            @Valid @RequestBody FareRequest request
    ) throws Exception {
        return new ResponseEntity(fareService.updateFare(id,request),HttpStatus.OK);
    }

    /**
     * Deletes a fare.
     *
     * @param id fare identifier
     * @return no content
     * @throws Exception if the fare is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFare(
            @PathVariable Long id
    ) throws Exception {
        fareService.deleteFare(id);
        return ResponseEntity.noContent().build();
    }

}
