package com.aryan.service;

import com.aryan.payload.request.AirportRequest;
import com.aryan.payload.response.AirportResponse;

import java.util.List;

/**
 * Service contract for airport management operations.
 */
public interface AirportService {

    /**
     * Creates a new airport.
     *
     * @param request airport details
     * @return created airport
     * @throws Exception if the IATA code already exists or the city is not found
     */
    AirportResponse createAirport(AirportRequest request) throws Exception;

    /**
     * Retrieves an airport by its identifier.
     *
     * @param id airport identifier
     * @return airport details
     * @throws Exception if the airport is not found
     */
    AirportResponse getAirportById(long id) throws Exception;

    /**
     * Retrieves all airports.
     *
     * @return list of airports
     */
    List<AirportResponse> getAllAirports();

    /**
     * Updates an existing airport.
     *
     * @param id airport identifier
     * @param request updated airport details
     * @return updated airport
     * @throws Exception if the airport is not found or the IATA code is already taken
     */
    AirportResponse updateAirport(long id, AirportRequest request) throws Exception;

    /**
     * Deletes an airport.
     *
     * @param id airport identifier
     * @throws Exception if the airport is not found
     */
    void deleteAirport(long id) throws Exception;

    /**
     * Retrieves all airports associated with a specific city.
     *
     * @param cityId city identifier
     * @return list of airports in the city
     */
    List<AirportResponse> getAirportByCityId(long cityId);
}
