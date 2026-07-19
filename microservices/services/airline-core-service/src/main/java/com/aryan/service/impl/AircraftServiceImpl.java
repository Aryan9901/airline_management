package com.aryan.service.impl;

import com.aryan.mapper.AircraftMapper;
import com.aryan.model.Aircraft;
import com.aryan.model.Airline;
import com.aryan.payload.request.AircraftRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.repository.AircraftRepository;
import com.aryan.repository.AirlineRepository;
import com.aryan.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));
        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if(aircraftRepository.existsByCode(aircraft.getCode())){
            throw new Exception("Code already exist with anather aircraft");
        }

//        if(aircraft.getSeatingCapacity() < aircraft.getTotalSeats()){
//            throw new Exception("Seating capacity can't exceed to Total Seats");
//        }

        if (!aircraft.getSeatingCapacity().equals(aircraft.getTotalSeats())) {
            throw new Exception("Total seats across all classes must equal the seating capacity.");
        }

        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        return AircraftMapper.toResponse(
                aircraftRepository.findById(id).orElseThrow(() -> new Exception("Aircraft not exists with Id " + id))
        );
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));
        return aircraftRepository.findByAirlineId(airline.getId()).stream().map(AircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId()).orElseThrow(()-> new Exception("Aircraft not exists"));
        if(request.getCode() != null && !aircraft.getCode().equals(request.getCode()) && aircraftRepository.existsByCode(request.getCode())){
            throw new Exception("Code already exist with anather aircraft.");
        }
        AircraftMapper.updateEntity(aircraft,request);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(() -> new Exception("Airline not found with Owner Id " + ownerId));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId()).orElseThrow(()-> new Exception("Aircraft not exists"));
        aircraftRepository.delete(aircraft);
    }
}
