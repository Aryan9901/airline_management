package com.aryan.mapper;


import com.aryan.model.Flight;
import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.payload.response.FlightResponse;
import com.aryan.util.MapperUtils;

public class FlightMapper {

    public static Flight toEntity(FlightRequest request){
        if(request == null) return null;

        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .build();
    }

    public static FlightResponse toResponse(
            Flight flight,
            AircraftResponse aircraft,
            AirlineResponse airline,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport
    ){
        if(flight == null) return null;

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airline.builder().id(flight.getAirlineId()).build())
                .aircraft(aircraft)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FlightRequest request, Flight flight){
        if(request == null || flight == null) return;

        MapperUtils.updateIfNotNull(request.getFlightNumber(), flight::setFlightNumber);
        MapperUtils.updateIfNotNull(request.getAircraftId(), flight::setAircraftId);
        MapperUtils.updateIfNotNull(request.getDepartureAirportId(), flight::setDepartureAirportId);
        MapperUtils.updateIfNotNull(request.getArrivalAirportId(), flight::setArrivalAirportId);
        MapperUtils.updateIfNotNull(request.getStatus(), flight::setStatus);
    }

}
