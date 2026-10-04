package com.aryan.service;


import com.aryan.payload.request.BaggagePolicyRequest;
import com.aryan.payload.response.BaggagepolicyResponse;

import java.util.List;

public interface BaggagePolicyService {

    BaggagepolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception;
    BaggagepolicyResponse getBaggagePolicyById(Long id) throws Exception;
    BaggagepolicyResponse getBaggagePolicyByFareId(Long fareId) throws Exception;
    List<BaggagepolicyResponse> getBaggagePolicyByAirlineId(Long airlineId);
    BaggagepolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception;
    void deleteBaggagePolicy(Long id) throws Exception;

}
