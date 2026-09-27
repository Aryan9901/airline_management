package com.aryan.service;

import com.aryan.payload.request.FareRulesRequest;
import com.aryan.payload.response.FareRulesResponse;

import java.util.List;

public interface FareRulesService {
    FareRulesResponse createFareRules(FareRulesRequest request) throws Exception;
    FareRulesResponse getFareRulesById(Long id) throws Exception;
    FareRulesResponse getFareRulesByFareId(Long fareId) throws Exception;
    List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId);
    FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception;
    void deleteFareRules(Long id) throws Exception;
}
