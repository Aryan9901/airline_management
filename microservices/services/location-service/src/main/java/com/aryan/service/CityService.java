package com.aryan.service;

import com.aryan.payload.request.CityRequest;
import com.aryan.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service contract for city management operations.
 */
public interface CityService {

    /**
     * Creates a new city.
     *
     * @param request city details
     * @return created city
     * @throws Exception if a city with the same code already exists
     */
    CityResponse createCity(CityRequest request) throws Exception;

    /**
     * Retrieves a city by its identifier.
     *
     * @param id city identifier
     * @return city details
     * @throws Exception if the city is not found
     */
    CityResponse getCityByid(Long id) throws Exception;

    /**
     * Updates an existing city.
     *
     * @param id city identifier
     * @param request updated city details
     * @return updated city
     * @throws Exception if the city is not found or the code is already taken
     */
    CityResponse updateCity(Long id, CityRequest request) throws Exception;

    /**
     * Deletes a city.
     *
     * @param id city identifier
     * @throws Exception if the city is not found
     */
    void deleteCity(Long id) throws Exception;

    /**
     * Retrieves all cities with pagination.
     *
     * @param pageable pagination information
     * @return paginated city list
     */
    Page<CityResponse> getAllCities(Pageable pageable);

    /**
     * Searches cities by keyword across name, code, and country fields.
     *
     * @param keyword search term
     * @param pageable pagination information
     * @return paginated matching cities
     */
    Page<CityResponse> searchCities(String keyword, Pageable pageable);

    /**
     * Retrieves cities belonging to a specific country.
     *
     * @param countryCode ISO country code
     * @param pageable pagination information
     * @return paginated city list
     */
    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);

    /**
     * Checks whether a city with the given code exists.
     *
     * @param cityCode city code
     * @return true if the city exists
     */
    Boolean cityExists(String cityCode);
}
