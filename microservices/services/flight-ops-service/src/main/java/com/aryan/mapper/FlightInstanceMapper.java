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

/**
 * Utility class for converting between
 * {@link FlightInstance}, {@link FlightInstanceRequest},
 * and {@link FlightInstanceResponse}.
 */
public class FlightInstanceMapper {

    /**
     * Converts a flight instance request into a {@link FlightInstance} entity.
     *
     * @param request flight instance request payload
     * @param flight associated flight entity
     * @return mapped flight instance entity
     */
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

    /**
     * Converts a {@link FlightInstance} entity into a {@link FlightInstanceResponse},
     * enriched with cross-service references.
     *
     * @param flightInstance flight instance entity
     * @param aircraft aircraft details
     * @param airline airline details
     * @param departureAirport departure airport details
     * @param arrivalAirport arrival airport details
     * @return enriched flight instance response
     */
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

    /**
     * Updates an existing flight instance entity using non-null values from the request.
     *
     * @param request updated flight instance details
     * @param flightInstance existing flight instance entity
     */
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
