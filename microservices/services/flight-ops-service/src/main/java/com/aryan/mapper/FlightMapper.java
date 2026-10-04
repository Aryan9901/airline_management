package com.aryan.mapper;


import com.aryan.model.Flight;
import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.payload.response.FlightResponse;
import com.aryan.util.MapperUtils;

/**
 * Utility class for converting between
 * {@link Flight}, {@link FlightRequest}, and {@link FlightResponse}.
 */
public class FlightMapper {

    /**
     * Converts a flight request into a {@link Flight} entity.
     *
     * @param request flight request payload
     * @return mapped flight entity
     */
    public static Flight toEntity(FlightRequest request){
        if(request == null) return null;

        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .build();
    }

    /**
     * Converts a {@link Flight} entity into a {@link FlightResponse},
     * enriched with cross-service references.
     *
     * @param flight flight entity
     * @param aircraft aircraft details
     * @param airline airline details
     * @param departureAirport departure airport details
     * @param arrivalAirport arrival airport details
     * @return enriched flight response
     */
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

    /**
     * Updates an existing flight entity using non-null values from the request.
     *
     * @param request updated flight details
     * @param flight existing flight entity
     */
    public static void updateEntity(FlightRequest request, Flight flight){
        if(request == null || flight == null) return;

        MapperUtils.updateIfNotNull(request.getFlightNumber(), flight::setFlightNumber);
        MapperUtils.updateIfNotNull(request.getAircraftId(), flight::setAircraftId);
        MapperUtils.updateIfNotNull(request.getDepartureAirportId(), flight::setDepartureAirportId);
        MapperUtils.updateIfNotNull(request.getArrivalAirportId(), flight::setArrivalAirportId);
        MapperUtils.updateIfNotNull(request.getStatus(), flight::setStatus);
    }

}
