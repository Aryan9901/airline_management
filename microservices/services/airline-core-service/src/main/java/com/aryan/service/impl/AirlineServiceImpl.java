package com.aryan.service.impl;

import com.aryan.enums.AirlineStatus;
import com.aryan.mapper.AirlineMapper;
import com.aryan.model.Airline;
import com.aryan.payload.request.AirlineRequest;
import com.aryan.payload.response.AirlineDropdownItem;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.repository.AirlineRepository;
import com.aryan.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(request,ownerId);
        Airline savedAirline = airlineRepository.save(airline);

        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findById(id).orElseThrow(() -> new Exception("Airline not found with Id " + id));

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(
                AirlineMapper::toResponse
        );
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));

        AirlineMapper.updateEntity(airline, request);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));
        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new Exception("Airline not found with Id " + airlineId));
        airline.setStatus(status);
        Airline updatedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a->AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logourl(a.getLogoUrl())
                        .build()).toList();
    }
}
