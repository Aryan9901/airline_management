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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fare-rules")
public class FareRulesController {

    private final FareRulesService fareRulesService;

    @PostMapping
    public ResponseEntity<FareRulesResponse> createFareRules(
            @Valid @RequestBody FareRulesRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareRulesService.createFareRules(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(
            @PathVariable Long fareId
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(
            @PathVariable Long airlineId
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @Valid @RequestBody FareRulesRequest request,
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(fareRulesService.updateFareRules(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<FareRulesResponse>> deleteFareRules(
            @PathVariable Long id
    ) throws Exception {
        fareRulesService.deleteFareRules(id);
        return ResponseEntity.noContent().build();
    }

}
