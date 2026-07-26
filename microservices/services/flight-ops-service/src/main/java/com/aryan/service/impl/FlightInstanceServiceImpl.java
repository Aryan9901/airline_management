package com.aryan.service.impl;

import com.aryan.mapper.FlightInstanceMapper;
import com.aryan.mapper.FlightMapper;
import com.aryan.model.Flight;
import com.aryan.model.FlightInstance;
import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.payload.response.FlightInstanceResponse;
import com.aryan.repository.FlightInstanceRepository;
import com.aryan.repository.FlightRepository;
import com.aryan.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {


    private final FlightInstanceRepository flightInstanceRepository;

    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {

        //        todo: watch airlineId

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + airlineId)
                );

        //        todo: service to serice communication

        AircraftResponse aircraft = AircraftResponse
                .builder()
                .id(1L)
                .totalSeats(90)
                .build();

        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request,flight);

        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance createdFlightInstance = flightInstanceRepository.save(flightInstance);

//      todo: create seat instance

        return convertToFlightInstanceResponse(createdFlightInstance);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {

        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getFlightInstancesByAirlineId(
            Long airlineId,
            Long departureAirportid,
            Long arrivalAirportId,
            Long flightId,
            LocalDate onDate,
            Pageable pageable
    ) {

//        todo: watch airlineId

        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;

        return flightInstanceRepository.findByAirlineId(
                airlineId,
                departureAirportid,
                arrivalAirportId,
                flightId,
                start,
                end,
                pageable
        ).map( fi -> convertToFlightInstanceResponse(fi));
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {

        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        FlightInstanceMapper.updateEntity(request, flightInstance);

        return convertToFlightInstanceResponse(flightInstanceRepository.save(flightInstance));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance){

        //        todo: service to service comunication
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flightInstance.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flightInstance.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightInstance.getArrivalAirportId()).build();
        AircraftResponse aircraftResponse = AircraftResponse.builder().id(flightInstance.getFlight().getAircraftId()).build();

        return FlightInstanceMapper.toResponse(flightInstance, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
