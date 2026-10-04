package com.aryan.controller;

import com.aryan.payload.request.BaggagePolicyRequest;
import com.aryan.payload.response.BaggagePolicyResponse;
import com.aryan.service.BaggagePolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for baggage policy management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * and deleting baggage policies.
 *
 * Base URL: /api/baggage-policy
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baggage-policy")
public class BaggagePolicyController {

    private final BaggagePolicyService baggagePolicyService;

    /**
     * Creates a new baggage policy for a fare.
     *
     * @param request baggage policy details
     * @return created baggage policy
     * @throws Exception if the fare is not found or a policy already exists
     */
    @PostMapping
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(@Valid @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(baggagePolicyService.createBaggagePolicy(request));
    }

    /**
     * Retrieves a baggage policy by its identifier.
     *
     * @param id baggage policy identifier
     * @return baggage policy details
     * @throws Exception if the policy is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyById(id));
    }

    /**
     * Retrieves the baggage policy associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return baggage policy details
     * @throws Exception if the policy is not found
     */
    @GetMapping("/fare/{fareId}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyByFareId(
            @PathVariable Long fareId
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByFareId(fareId));
    }

    /**
     * Retrieves all baggage policies for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of baggage policies
     * @throws Exception if the airline is not found
     */
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePolicyByAirlineId(
            @PathVariable Long airlineId
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByAirlineId(airlineId));
    }

    /**
     * Updates an existing baggage policy.
     *
     * @param request updated baggage policy details
     * @param id baggage policy identifier
     * @return updated baggage policy
     * @throws Exception if the policy is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(
            @Valid @RequestBody BaggagePolicyRequest request,
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(baggagePolicyService.updateBaggagePolicy(id, request));
    }

    /**
     * Deletes a baggage policy.
     *
     * @param id baggage policy identifier
     * @return no content
     * @throws Exception if the policy is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> deleteBaggagePolicy(
            @PathVariable Long id
    ) throws Exception {
        baggagePolicyService.deleteBaggagePolicy(id);
        return ResponseEntity.noContent().build();
    }

}
