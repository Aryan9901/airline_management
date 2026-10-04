package com.aryan.controller;

import com.aryan.payload.request.FareRulesRequest;
import com.aryan.payload.response.FareRulesResponse;
import com.aryan.service.FareRulesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for fare rules management operations.
 *
 * Provides APIs for creating, retrieving, updating,
 * and deleting fare rules.
 *
 * Base URL: /api/fare-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fare-rules")
public class FareRulesController {

    private final FareRulesService fareRulesService;

    /**
     * Creates fare rules for a fare.
     *
     * @param request fare rules details
     * @return created fare rules
     * @throws Exception if the fare is not found or rules already exist
     */
    @PostMapping
    public ResponseEntity<FareRulesResponse> createFareRules(
            @Valid @RequestBody FareRulesRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareRulesService.createFareRules(request));
    }

    /**
     * Retrieves fare rules by their identifier.
     *
     * @param id fare rules identifier
     * @return fare rules details
     * @throws Exception if the fare rules are not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesById(id));
    }

    /**
     * Retrieves fare rules associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return fare rules details
     * @throws Exception if the fare rules are not found
     */
    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(
            @PathVariable Long fareId
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByFareId(fareId));
    }

    /**
     * Retrieves all fare rules for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of fare rules
     * @throws Exception if the airline is not found
     */
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(
            @PathVariable Long airlineId
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    /**
     * Updates existing fare rules.
     *
     * @param request updated fare rules details
     * @param id fare rules identifier
     * @return updated fare rules
     * @throws Exception if the fare rules are not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @Valid @RequestBody FareRulesRequest request,
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.updateFareRules(id, request));
    }

    /**
     * Deletes fare rules.
     *
     * @param id fare rules identifier
     * @return no content
     * @throws Exception if the fare rules are not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<FareRulesResponse>> deleteFareRules(
            @PathVariable Long id
    ) throws Exception {
        fareRulesService.deleteFareRules(id);
        return ResponseEntity.noContent().build();
    }

}
