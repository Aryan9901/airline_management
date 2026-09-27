package com.aryan.service.impl;

import com.aryan.mapper.FareMapper;
import com.aryan.model.Fare;
import com.aryan.payload.request.FareRequest;
import com.aryan.payload.response.FareResponse;
import com.aryan.repository.FareRepository;
import com.aryan.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for fare management operations.
 */
@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    /**
     * Repository responsible for fare persistence operations.
     */
    private final FareRepository fareRepository;

    @Override
    public FareResponse createFare(FareRequest request) throws Exception {
        if(fareRepository.existsByFlightIdAndCabinClassIdAndName(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName()
        )){
            throw new Exception("Fare not found with given Id");
        }

        return FareMapper.toResponse(fareRepository.save(FareMapper.toEntity(request)));
    }

    @Override
    public FareResponse getFareById(Long id) throws Exception {
        return FareMapper.toResponse(
                fareRepository.findById(id).orElseThrow(() -> new Exception("Fare not found with given Id"))
        );
    }

    @Override
    public List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) {
        return fareRepository.findByFlightIdAndCabinClassId(
                flightId,
                cabinClassId
        ).stream().map(
                FareMapper::toResponse
        ).toList();
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) throws Exception {
        Fare fare = fareRepository.findById(id).orElseThrow(() -> new Exception("Fare not found with given Id"));

        if(fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
            request.getFlightId(),
            request.getCabinClassId(),
            request.getName(),
            fare.getId()
        )) {
            throw new Exception("Fare already exists with given flight, cabin class and name");
        }

        FareMapper.updateEntity(request, fare);
        return FareMapper.toResponse(fareRepository.save(fare));
    }

    @Override
    public void deleteFare(Long id) throws Exception {
        Fare fare = fareRepository.findById(id).orElseThrow(() -> new Exception("Fare not found with given Id"));
        fareRepository.delete(fare);
    }

    @Override
    public List<Fare> getFares() {
        return fareRepository.findAll();
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds,
            Long cabinClassId
    ) {
        if (flightIds == null || flightIds.isEmpty()) {
            return Map.of();
        }

        List<Fare> fares =
                fareRepository.findByFlightIdInAndCabinClassId(
                        flightIds,
                        cabinClassId
                );

        return fares.stream()
                .collect(Collectors.toMap(
                        Fare::getFlightId,
                        fare -> fare,
                        (existing, candidate) ->
                                candidate.getTotalPrice() < existing.getTotalPrice()
                                        ? candidate
                                        : existing
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> FareMapper.toResponse(e.getValue())
                ));
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        List<Fare> fares = fareRepository.findAllById(ids);
        return fares.stream().collect(Collectors.toMap(Fare::getId, FareMapper::toResponse));
    }

}
