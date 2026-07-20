package com.aryan.service;

import com.aryan.enums.AirlineStatus;
import com.aryan.model.Airline;
import com.aryan.payload.request.AirlineRequest;
import com.aryan.payload.response.AirlineDropdownItem;
import com.aryan.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service contract for airline management operations.
 */
public interface AirlineService {

    /**
     * Creates a new airline.
     *
     * @param request airline details
     * @param ownerId owner identifier
     * @return created airline
     */
    AirlineResponse createAirline(AirlineRequest request, Long ownerId);

    /**
     * Retrieves the airline associated with the specified owner.
     *
     * @param ownerId owner identifier
     * @return airline details
     * @throws Exception if the airline is not found
     */
    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;

    /**
     * Retrieves an airline by its identifier.
     *
     * @param id airline identifier
     * @return airline details
     * @throws Exception if the airline is not found
     */
    AirlineResponse getAirlineById(Long id) throws Exception;

    /**
     * Retrieves a paginated list of airlines.
     *
     * @param pageable pagination information
     * @return paginated airline list
     */
    Page<AirlineResponse> getAllAirlines(Pageable pageable);

    /**
     * Updates an existing airline.
     *
     * @param request updated airline details
     * @param ownerId owner identifier
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception;

    /**
     * Deletes an airline.
     *
     * @param id airline identifier
     * @param ownerId owner identifier
     * @throws Exception if the airline is not found
     */
    void deleteAirline(Long id, Long ownerId) throws Exception;

    /**
     * Updates the operational status of an airline.
     *
     * @param airlineId airline identifier
     * @param status new airline status
     * @return updated airline
     * @throws Exception if the airline is not found
     */
    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception;

    /**
     * Retrieves airlines for dropdown selections.
     *
     * @return list of airline dropdown items
     */
    List<AirlineDropdownItem> getAirlineDropdown();
}
