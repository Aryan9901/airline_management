package com.aryan.mapper;

import com.aryan.model.Airport;
import com.aryan.model.City;

import com.aryan.payload.request.AirportRequest;
import com.aryan.payload.response.AirportResponse;

/**
 * Mapper utility class responsible for converting
 * airport-related entities and DTOs.
 *
 * Handles transformations between:
 * - AirportRequest → Airport
 * - Airport → AirportResponse
 * - Airport updates
 */
public class AirportMapper {

    /**
     * Converts airport request payload into airport entity.
     *
     * @param request airport request payload
     * @return airport entity
     */
    public static Airport toEntity(
            AirportRequest request
    ) {

        if (request == null) {
            return null;
        }

        return Airport.builder()
                .iataCode(
                        request.getIataCode()
                                .toUpperCase()
                                .trim()
                )
                .name(
                        request.getName()
                                .trim()
                )
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    /**
     * Converts airport entity into airport response payload.
     *
     * @param airport airport entity
     * @return airport response payload
     */
    public static AirportResponse toResponse(
            Airport airport
    ) {

        if (airport == null) {
            return null;
        }

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(
                        airport.getDetailedName()
                )
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .city(
                        CityMapper.toResponse(
                                airport.getCity()
                        )
                )
                .build();
    }

    /**
     * Updates airport entity using request payload.
     *
     * Only non-null fields are updated.
     *
     * @param airport existing airport entity
     * @param request updated airport payload
     * @param city associated city entity
     * @return updated airport entity
     */
    public static Airport updateEntity(
            Airport airport,
            AirportRequest request
    ) {

        if (request.getIataCode() != null) {
            airport.setIataCode(
                    request.getIataCode()
                            .toUpperCase()
                            .trim()
            );
        }

        if (request.getName() != null) {
            airport.setName(
                    request.getName()
                            .trim()
            );
        }

        if (request.getAddress() != null) {
            airport.setAddress(request.getAddress());
        }

        if (request.getGeoCode() != null) {
            airport.setGeoCode(request.getGeoCode());
        }

        return airport;
    }
}