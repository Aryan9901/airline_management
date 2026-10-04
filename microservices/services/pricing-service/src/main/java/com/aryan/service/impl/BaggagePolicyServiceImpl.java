package com.aryan.service.impl;

import com.aryan.mapper.BaggagePolicyMapper;
import com.aryan.model.BaggagePolicy;
import com.aryan.model.Fare;
import com.aryan.payload.request.BaggagePolicyRequest;
import com.aryan.payload.response.BaggagepolicyResponse;
import com.aryan.repository.BaggagePolicyRepository;
import com.aryan.repository.FareRepository;
import com.aryan.service.BaggagePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final BaggagePolicyRepository baggagePolicyRepository;
    private final FareRepository fareRepository;


    @Override
    public BaggagepolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(
                        () -> new Exception("Fare not found with the given id")
                );

        if(baggagePolicyRepository.existsByFareId(fare.getId())){
            throw new Exception("Bagge policy already exists for the given fare.");
        }

        return BaggagePolicyMapper.toResponse(baggagePolicyRepository.save(BaggagePolicyMapper.toEntity(request, fare)));
    }

    @Override
    public BaggagepolicyResponse getBaggagePolicyById(Long id) throws Exception {
        return BaggagePolicyMapper.toResponse(
                baggagePolicyRepository.findById(id)
                        .orElseThrow(
                                () -> new Exception("Baggage Policy not found")
                        )
        );
    }

    @Override
    public BaggagepolicyResponse getBaggagePolicyByFareId(Long fareId) throws Exception {
        return BaggagePolicyMapper.toResponse(
                baggagePolicyRepository.findByFareId(fareId)
                        .orElseThrow(
                                () -> new Exception("Baggage Policy not found for the given fare")
                        )
        );
    }

    @Override
    public List<BaggagepolicyResponse> getBaggagePolicyByAirlineId(Long airlineId) {
        return baggagePolicyRepository.findByAirlineId(airlineId)
                .stream()
                .map(BaggagePolicyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BaggagepolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception {
        BaggagePolicy policyToUpdate = baggagePolicyRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Baggage Policy not found")
                );

        BaggagePolicyMapper.updateEntity(request, policyToUpdate);
        return BaggagePolicyMapper.toResponse(baggagePolicyRepository.save(policyToUpdate));
    }

    @Override
    public void deleteBaggagePolicy(Long id) throws Exception {
        BaggagePolicy policyToDelete = baggagePolicyRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Baggage Policy not found")
                );

        baggagePolicyRepository.delete(policyToDelete);
    }
}
