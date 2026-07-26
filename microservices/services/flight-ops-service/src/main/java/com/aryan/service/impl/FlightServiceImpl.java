package com.aryan.service.impl;

import com.aryan.mapper.FlightMapper;
import com.aryan.model.Flight;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.repository.FlightRepository;
import com.aryan.enums.FlightStatus;
import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.response.FlightResponse;
import com.aryan.service.FlightService;
import com.aryan.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {

        //        todo: watch airlineId
        if(flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())){
            throw new Exception("Flight with number already exists.");
        }

        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);

        return convertToFlightResponse(flightRepository.save(flight));
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Pageable pageable
    ) {
        //        todo: watch airlineId
        return flightRepository.findByAirlineId
                (
                    airlineId,
                    departureAirportId,
                    arrivalAirportId,
                    pageable
                ).map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new Exception("Flight not found with id " + id));
        return convertToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new Exception("Flight not found with id " + id));

        if(flightRequest.getFlightNumber() != null && flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)){
            throw new Exception("Flight already exists");
        }

        FlightMapper.updateEntity(flightRequest, flight);
        return convertToFlightResponse(flightRepository.save(flight));
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new Exception("Flight not found with id " + id));
        MapperUtils.updateIfNotNull(status, flight::setStatus);
        return convertToFlightResponse(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long airlineId ,Long id) throws Exception {
        //        todo: watch airlineId
        Flight flight = flightRepository.findByAirlineIdAndId(airlineId,id).orElseThrow(() -> new Exception("Flight not found with id " + id));
        flightRepository.delete(flight);
    }

    public FlightResponse convertToFlightResponse(Flight flight){
        //        todo: service to service comunication
        AircraftResponse aircraftResponse = AircraftResponse.builder().id(flight.getAircraftId()).build();
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flight.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flight.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flight.getArrivalAirportId()).build();

        return FlightMapper.toResponse(flight, aircraftResponse, airlineResponse, departureAirport, arrivalAirport);
    }
}
