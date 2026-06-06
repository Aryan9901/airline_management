package com.aryan.controller;

import com.aryan.payload.request.CityRequest;
import com.aryan.payload.response.ApiResponse;
import com.aryan.payload.response.CityResponse;
import com.aryan.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for managing city-related operations.
 *
 * Provides APIs for:
 * - Creating cities
 * - Fetching city details
 * - Updating city information
 * - Deleting cities
 * - Searching cities
 * - Fetching cities by country code
 * -
 * Base URL: /api/cities
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

    /**
     * Service layer responsible for city business operations.
     * */
    private final CityService cityService;

    /**
     * Creates a new city resource.
     *
     * @param cityRequest request payload containing city details
     * @return created city response
     * @throws Exception if city creation fails
     */
    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) throws Exception {
        CityResponse  response = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Fetches city details using city ID.
     *
     * @param id unique city identifier
     * @return city response
     * @throws Exception if city is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) throws Exception {
        CityResponse response = cityService.getCityByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetches all cities with pagination and sorting support.
     *
     * @param page current page number
     * @param size number of records per page
     * @param sortBy field used for sorting
     * @param sortDirection sorting direction (asc/desc)
     * @return paginated list of cities
     */
    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.ok(cityService.getAllCities(pageable));
    }

    /**
     * Updates an existing city resource.
     *
     * @param id unique city identifier
     * @param request updated city payload
     * @return updated city response
     * @throws Exception if update operation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCity(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest request) throws Exception {
        CityResponse response = cityService.updateCity(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Deletes a city resource using city ID.
     *
     * @param id unique city identifier
     * @return success response message
     * @throws Exception if city deletion fails
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCity(
            @PathVariable Long id) throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("City deleted successfully"));
    }

    /**
     * Searches cities using keyword matching.
     *
     * Performs case-insensitive partial matching
     * against city names.
     *
     * @param keyword search keyword
     * @param page current page number
     * @param size number of records per page
     * @return paginated matching city results
     */
    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.searchCities(keyword,pageable));
    }

    /**
     * Fetches cities belonging to a specific country.
     *
     * Country code is normalized to uppercase
     * before processing.
     *
     * @param countryCode ISO country code
     * @param page current page number
     * @param size number of records per page
     * @return paginated list of cities
     */
    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @PathVariable String countryCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode.toUpperCase(),pageable));
    }
}
