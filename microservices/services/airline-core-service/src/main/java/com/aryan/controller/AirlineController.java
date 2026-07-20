package com.aryan.controller;

import com.aryan.enums.AirlineStatus;
import com.aryan.payload.request.AirlineRequest;
import com.aryan.payload.response.AirlineDropdownItem;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.ApiResponse;
import com.aryan.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for airline management operations.
 *
 * Provides APIs for creating, retrieving,
 * updating, deleting, listing, and managing
 * airline status.
 *
 * Base URL: /api/airlines
 */
@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    /**
     * Creates a new airline for the authenticated owner.
     *
     * @param airlineRequest airline details
     * @param userId authenticated owner identifier
     * @return created airline
     */
    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(@Valid @RequestBody AirlineRequest airlineRequest, @RequestHeader("X-User-Id") Long userId){
        return new ResponseEntity<>(airlineService.createAirline(airlineRequest, userId), HttpStatus.CREATED);
    }

    /**
     * Retrieves the airline associated with the authenticated owner.
     *
     * @param userId authenticated owner identifier
     * @return airline details
     * @throws Exception if the airline is not found
     */
    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(@RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineByOwner(userId));
    }

    /**
     * Retrieves an airline by its identifier.
     *
     * @param id airline identifier
     * @return airline details
     * @throws Exception if the airline is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.getAirlineById(id));
    }

    /**
     * Retrieves a paginated list of airlines.
     *
     * @param pageable pagination information
     * @return paginated airline list
     */
    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable){
        return ResponseEntity.ok(airlineService.getAllAirlines(pageable));
    }

    /**
     * Retrieves active airlines for dropdown selections.
     *
     * @return list of airline dropdown items
     */
    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlinesDropdown(){
        return ResponseEntity.ok(airlineService.getAirlineDropdown());
    }

    /**
     * Updates the airline associated with the authenticated owner.
     *
     * @param request updated airline details
     * @param userId authenticated owner identifier
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(@Valid @RequestBody AirlineRequest request, @RequestHeader("X-User-Id") Long userId) throws Exception {
        return ResponseEntity.ok(airlineService.updateAirline(request, userId));
    }

    /**
     * Deletes the airline associated with the authenticated owner.
     *
     * @param id airline identifier
     * @param userId authenticated owner identifier
     * @return deletion response
     * @throws Exception if the airline is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) throws Exception {
        airlineService.deleteAirline(id,userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Airline Deleted Successfully"));
    }

    /**
     * Approves an airline by marking its status as active.
     *
     * @param id airline identifier
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.ACTIVE));
    }

    /**
     * Suspends an airline by marking its status as inactive.
     *
     * @param id airline identifier
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    @PostMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.INACTIVE));
    }

    /**
     * Bans an airline by marking its status as banned.
     *
     * @param id airline identifier
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    @PostMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(airlineService.changeStatusByAdmin(id, AirlineStatus.BANNED));
    }

}
