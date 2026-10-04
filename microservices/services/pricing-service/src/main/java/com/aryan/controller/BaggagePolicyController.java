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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/baggage-policy")
public class BaggagePolicyController {

    private final BaggagePolicyService baggagePolicyService;

    @PostMapping
    public ResponseEntity<BaggagePolicyResponse> createBaggagePolicy(@Valid @RequestBody BaggagePolicyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(baggagePolicyService.createBaggagePolicy(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<BaggagePolicyResponse> getBaggagePolicyByFareId(
            @PathVariable Long fareId
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<BaggagePolicyResponse>> getBaggagePolicyByAirlineId(
            @PathVariable Long airlineId
    ) throws Exception {
        return ResponseEntity.ok(baggagePolicyService.getBaggagePolicyByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> updateBaggagePolicy(
            @Valid @RequestBody BaggagePolicyRequest request,
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(baggagePolicyService.updateBaggagePolicy(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaggagePolicyResponse> deleteBaggagePolicy(
            @PathVariable Long id
    ) throws Exception {
        baggagePolicyService.deleteBaggagePolicy(id);
        return ResponseEntity.noContent().build();
    }

}
