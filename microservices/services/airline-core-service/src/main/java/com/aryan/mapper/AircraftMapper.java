package com.aryan.mapper;

import com.aryan.model.Aircraft;
import com.aryan.model.Airline;
import com.aryan.payload.request.AircraftRequest;
import com.aryan.payload.response.AircraftResponse;

/**
 * Utility class for converting between
 * {@link Aircraft}, {@link AircraftRequest},
 * and {@link AircraftResponse}.
 */
public class AircraftMapper {

    /**
     * Converts an aircraft request into an
     * {@link Aircraft} entity.
     *
     * @param request aircraft request payload
     * @param airline airline associated with the aircraft
     * @return mapped aircraft entity
     */
    public static Aircraft toEntity(AircraftRequest request, Airline airline){

        if(request == null) return null;

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus())
                .isAvailable(request.getIsAvailable())
                .airline(airline)
                .currentAirportId(request.getCurrentAirportId())
                .build();
    }

    /**
     * Converts an {@link Aircraft} entity into
     * an {@link AircraftResponse}.
     *
     * Includes computed fields and airline details.
     *
     * @param aircraft aircraft entity
     * @return aircraft response
     */
    public static AircraftResponse toResponse(Aircraft aircraft){
        if(aircraft == null) return null;

        Airline airline = aircraft.getAirline();

        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .airlineId(airline != null ? airline.getId() : null)
                .airlineName(airline != null ? airline.getName() : null)
                .airlineIataCode(airline != null ? airline.getIataCode() : null)
             // Airport is cross-service - only id is available here
                .currentAirportId(aircraft.getCurrentAirportId())

             // Computed fields
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.requiresMaintenance())
                .isOperational(aircraft.isOperational())
             // audit
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }

    /**
     * Updates an existing aircraft entity using
     * the provided request data.
     *
     * @param aircraft existing aircraft entity
     * @param request updated aircraft details
     */

    public static void updateEntity(Aircraft aircraft, AircraftRequest request){
        if(aircraft == null || request == null) return;

        aircraft.setCode(request.getCode());
        aircraft.setModel(request.getModel());
        aircraft.setManufacturer(request.getManufacturer());
        aircraft.setSeatingCapacity(request.getSeatingCapacity());
        aircraft.setEconomySeats(request.getEconomySeats());
        aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        aircraft.setBusinessSeats(request.getBusinessSeats());
        aircraft.setFirstClassSeats(request.getFirstClassSeats());
        aircraft.setRangeKm(request.getRangeKm());
        aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        aircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
        aircraft.setYearOfManufacture(request.getYearOfManufacture());
        aircraft.setRegistrationDate(request.getRegistrationDate());
        aircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        aircraft.setStatus(request.getStatus());
        aircraft.setIsAvailable(request.getIsAvailable());
        aircraft.setCurrentAirportId(request.getCurrentAirportId());

    }

}
