package com.aryan.service;


import com.aryan.payload.request.BaggagePolicyRequest;
import com.aryan.payload.response.BaggagePolicyResponse;

import java.util.List;

/**
 * Service contract for baggage policy management operations.
 */
public interface BaggagePolicyService {

    /**
     * Creates a new baggage policy for a fare.
     *
     * @param request baggage policy details
     * @return created baggage policy
     * @throws Exception if the fare is not found or a policy already exists
     */
    BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception;

    /**
     * Retrieves a baggage policy by its identifier.
     *
     * @param id baggage policy identifier
     * @return baggage policy details
     * @throws Exception if the policy is not found
     */
    BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception;

    /**
     * Retrieves the baggage policy associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return baggage policy details
     * @throws Exception if the policy is not found
     */
    BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) throws Exception;

    /**
     * Retrieves all baggage policies for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of baggage policies
     */
    List<BaggagePolicyResponse> getBaggagePolicyByAirlineId(Long airlineId);

    /**
     * Updates an existing baggage policy.
     *
     * @param id baggage policy identifier
     * @param request updated baggage policy details
     * @return updated baggage policy
     * @throws Exception if the policy is not found
     */
    BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception;

    /**
     * Deletes a baggage policy.
     *
     * @param id baggage policy identifier
     * @throws Exception if the policy is not found
     */
    void deleteBaggagePolicy(Long id) throws Exception;

}
