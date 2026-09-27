package com.aryan.service;

import com.aryan.model.Fare;
import com.aryan.payload.request.FareRequest;
import com.aryan.payload.response.FareResponse;

import java.util.List;
import java.util.Map;

/**
 * Service contract for fare management operations.
 */
public interface FareService {

    /**
     * Creates a new fare.
     *
     * @param request fare details
     * @return created fare
     * @throws Exception if the fare cannot be created
     */
    FareResponse createFare(FareRequest request) throws Exception;

    /**
     * Retrieves a fare by its identifier.
     *
     * @param id fare identifier
     * @return fare details
     * @throws Exception if the fare is not found
     */
    FareResponse getFareById(Long id) throws Exception;

    /**
     * Retrieves fares for a flight and cabin class.
     *
     * @param flightId flight identifier
     * @param cabinClassId cabin class identifier
     * @return matching fares
     */
    List<FareResponse> getFaresByFlightIdAndCabinClassId(
            Long flightId,
            Long cabinClassId
    );

    /**
     * Updates an existing fare.
     *
     * @param id fare identifier
     * @param request updated fare details
     * @return updated fare
     * @throws Exception if the fare cannot be updated
     */
    FareResponse updateFare(Long id, FareRequest request) throws Exception;

    /**
     * Deletes a fare.
     *
     * @param id fare identifier
     * @throws Exception if the fare is not found
     */
    void deleteFare(Long id) throws Exception;

    /**
     * Retrieves all fares.
     *
     * @return list of fares
     */
    List<Fare> getFares();

    /**
     * Retrieves the lowest fare for each flight.
     *
     * @param flightIds flight identifiers
     * @param cabinClassId cabin class identifier
     * @return lowest fare mapped by flight identifier
     */
    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds,
            Long cabinClassId
    );

    /**
     * Retrieves fares by their identifiers.
     *
     * @param ids fare identifiers
     * @return fares mapped by their identifiers
     */
    Map<Long, FareResponse> getFaresByIds(List<Long> ids);

}
