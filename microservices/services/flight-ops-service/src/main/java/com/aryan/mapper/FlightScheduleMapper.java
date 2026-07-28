package com.aryan.mapper;

import com.aryan.enums.FlightStatus;
import com.aryan.model.Flight;
import com.aryan.model.FlightInstance;
import com.aryan.model.FlightSchedule;
import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.request.FlightScheduleRequest;
import com.aryan.payload.response.*;
import com.aryan.util.MapperUtils;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request, Flight flight){
        if(request == null || flight == null) return null;

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getDepartureAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightScheduleResponse toResponse(
            FlightSchedule flightSchedule,
            AirportResponse arrival,
            AirportResponse departure
    ){
        if(flightSchedule == null) return null;

        return FlightScheduleResponse.builder()
                .id(flightSchedule.getId())
                .flightId(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getId() : null)
                .flightNumber(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getFlightNumber() : null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(flightSchedule.getDepartureTime())
                .arrivalTime(flightSchedule.getArrivalTime())
                .startDate(flightSchedule.getStartDate())
                .endDate(flightSchedule.getEndDate())
                .operatingDays(flightSchedule.getOperatingDays())
                .isActive(flightSchedule.getIsActive())
                .build();
    }

    public static void updateEntity(FlightScheduleRequest request, FlightSchedule flightSchedule){
        if(request == null || flightSchedule == null) return;

        MapperUtils.updateIfNotNull(request.getDepartureTime(), flightSchedule::setDepartureTime);
        MapperUtils.updateIfNotNull(request.getArrivalTime(), flightSchedule::setArrivalTime);
        MapperUtils.updateIfNotNull(request.getStartDate(), flightSchedule::setStartDate);
        MapperUtils.updateIfNotNull(request.getEndDate(), flightSchedule::setEndDate);
        MapperUtils.updateIfNotNull(request.getOperatingDays(), flightSchedule::setOperatingDays);
        MapperUtils.updateIfNotNull(request.getIsActive(), flightSchedule::setIsActive);
    }

}
