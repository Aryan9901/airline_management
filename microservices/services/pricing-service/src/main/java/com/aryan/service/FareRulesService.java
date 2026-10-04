package com.aryan.service;

import com.aryan.payload.request.FareRulesRequest;
import com.aryan.payload.response.FareRulesResponse;

import java.util.List;

/**
 * Service contract for fare rules management operations.
 */
public interface FareRulesService {

    /**
     * Creates fare rules for a fare.
     *
     * @param request fare rules details
     * @return created fare rules
     * @throws Exception if the fare is not found or rules already exist
     */
    FareRulesResponse createFareRules(FareRulesRequest request) throws Exception;

    /**
     * Retrieves fare rules by their identifier.
     *
     * @param id fare rules identifier
     * @return fare rules details
     * @throws Exception if the fare rules are not found
     */
    FareRulesResponse getFareRulesById(Long id) throws Exception;

    /**
     * Retrieves fare rules associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return fare rules details
     * @throws Exception if the fare rules are not found
     */
    FareRulesResponse getFareRulesByFareId(Long fareId) throws Exception;

    /**
     * Retrieves all fare rules for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of fare rules
     */
    List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId);

    /**
     * Updates existing fare rules.
     *
     * @param id fare rules identifier
     * @param request updated fare rules details
     * @return updated fare rules
     * @throws Exception if the fare rules are not found
     */
    FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception;

    /**
     * Deletes fare rules.
     *
     * @param id fare rules identifier
     * @throws Exception if the fare rules are not found
     */
    void deleteFareRules(Long id) throws Exception;
}
