package com.aryan.service.impl;

import com.aryan.mapper.FareRulesMapper;
import com.aryan.model.Fare;
import com.aryan.model.FareRules;
import com.aryan.payload.request.FareRulesRequest;
import com.aryan.payload.response.FareRulesResponse;
import com.aryan.repository.FareRepository;
import com.aryan.repository.FareRulesRepository;
import com.aryan.service.FareRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareRulesServiceImpl implements FareRulesService {

    private final FareRulesRepository fareRulesRepository;
    private final FareRepository fareRepository;

    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId()).orElseThrow(() -> new Exception("Fare not found"));

        if(fareRulesRepository.existsByFareId(request.getFareId())){
            throw new Exception("Fare already exists");
        }

        FareRules fareRules = FareRulesMapper.toEntity(request, fare);
        return FareRulesMapper.toResponse(fareRulesRepository.save(fareRules));
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) throws Exception {
        return FareRulesMapper.toResponse(
                fareRulesRepository.findById(id).orElseThrow(
                        () -> new Exception("fare rule not found")
                )
        );
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) throws Exception {
        return FareRulesMapper.toResponse(
                fareRulesRepository.findByFareId(fareId).orElseThrow(
                        () -> new Exception("fare rule not found with the given fare id")
                )
        );
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        return fareRulesRepository.findByAirlineId(airlineId).stream()
                .map(FareRulesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("fare rule not found")
        );

        FareRulesMapper.updateEntity(request, fareRules);
        return FareRulesMapper.toResponse(fareRulesRepository.save(fareRules));
    }

    @Override
    public void deleteFareRules(Long id) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("fare rule not found")
        );
        fareRulesRepository.delete(fareRules);
    }
}
