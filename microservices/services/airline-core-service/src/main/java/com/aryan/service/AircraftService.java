package com.aryan.service;

import com.aryan.payload.request.AircraftRequest;
import com.aryan.payload.response.AircraftResponse;

import java.util.List;

/**
 * Service contract for aircraft management operations.
 */
public interface AircraftService {

    /**
     * Creates a new aircraft for the specified airline owner.
     *
     * @param request aircraft details
     * @param ownerId airline owner identifier
     * @return created aircraft
     * @throws Exception if the owner or aircraft data is invalid
     */
    AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception;

    /**
     * Retrieves an aircraft by its identifier.
     *
     * @param id aircraft identifier
     * @return aircraft details
     * @throws Exception if the aircraft is not found
     */
    AircraftResponse getAircraftById(Long id) throws Exception;

    /**
     * Retrieves all aircraft belonging to the specified airline owner.
     *
     * @param ownerId airline owner identifier
     * @return list of aircraft
     * @throws Exception if the airline is not found
     */
    List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception;

    /**
     * Updates an existing aircraft.
     *
     * @param id aircraft identifier
     * @param request updated aircraft details
     * @param ownerId airline owner identifier
     * @return updated aircraft
     * @throws Exception if the aircraft or airline is not found
     */
    AircraftResponse updateAircraft(Long id,AircraftRequest request, Long ownerId) throws  Exception;

    /**
     * Deletes an aircraft owned by the specified airline.
     *
     * @param id aircraft identifier
     * @param ownerId airline owner identifier
     * @throws Exception if the aircraft or airline is not found
     */
    void deleteAircraft(Long id, Long ownerId) throws Exception;

}
