package com.aryan.mapper;


import com.aryan.enums.FlightStatus;
import com.aryan.model.Flight;
import com.aryan.model.FlightInstance;
import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.payload.response.FlightInstanceResponse;
import com.aryan.util.MapperUtils;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request, Flight flight){
        if(request == null || flight == null) return null;

        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .scheduleId(request.getScheduleId())
                .departureAirportId(request.getDepartureAirportId() != null ? request.getDepartureAirportId() : null)
                .arrivalAirportId(request.getArrivalAirportId() != null ? request.getArrivalAirportId() : null)
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightInstanceResponse toResponse(
            FlightInstance flightInstance,
            AircraftResponse aircraft,
            AirlineResponse airline,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport
    ){
        if(flightInstance == null) return null;

        return FlightInstanceResponse.builder()
                .id(flightInstance.getId())
                .flightId(flightInstance.getFlight() != null ? flightInstance.getFlight().getId() : null)
                .flightNumber(flightInstance.getFlight() != null ? flightInstance.getFlight().getFlightNumber() : null)
                .aircraftId(flightInstance.getFlight().getAircraftId())
                .aircraftModal(aircraft.getModel())
                .aircraftCode(aircraft.getCode())
                .airlineId(flightInstance.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .formattedDuration(flightInstance.getFormatedDuration())
                .totalSeats(flightInstance.getTotalSeats())
                .availableSeats(flightInstance.getAvailableSeats())
                .status(flightInstance.getStatus())
                .minAdvanceBookingDays(flightInstance.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(flightInstance.getMaxAdvanceBookingDays())
                .isActive(flightInstance.isActive())
                .build();
    }

    public static void updateEntity(FlightInstanceRequest request, FlightInstance flightInstance){
        if(request == null || flightInstance == null) return;

        MapperUtils.updateIfNotNull(request.getDepartureAirportId(), flightInstance::setDepartureAirportId);
        MapperUtils.updateIfNotNull(request.getArrivalAirportId(), flightInstance::setDepartureAirportId);
        MapperUtils.updateIfNotNull(request.getDepartureDateTime(), flightInstance::setDepartureDateTime);
        MapperUtils.updateIfNotNull(request.getArrivalDateTime(), flightInstance::setArrivalDateTime);
        MapperUtils.updateIfNotNull(request.getAvailableSeats(), flightInstance::setAvailableSeats);
        MapperUtils.updateIfNotNull(request.getStatus(), flightInstance::setStatus);
        MapperUtils.updateIfNotNull(request.getMinAdvanceBookingDays(), flightInstance::setMinAdvanceBookingDays);
        MapperUtils.updateIfNotNull(request.getMaxAdvanceBookingDays(), flightInstance::setMaxAdvanceBookingDays);
        MapperUtils.updateIfNotNull(request.getIsActive(), flightInstance::setActive);
    }

}
