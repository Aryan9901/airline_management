package com.aryan.mapper;

import com.aryan.embeddable.Support;
import com.aryan.model.Airline;
import com.aryan.payload.request.AirlineRequest;
import com.aryan.payload.response.AirlineResponse;

/**
 * Utility class for converting between
 * {@link Airline}, {@link AirlineRequest},
 * and {@link AirlineResponse}.
 */
public class AirlineMapper {

    /**
     * Converts an airline request into an
     * {@link Airline} entity.
     *
     * @param request airline request payload
     * @param ownerId identifier of the airline owner
     * @return mapped airline entity
     */
    public static Airline toEntity(AirlineRequest request, Long ownerId){
        if(request == null) return null;

        Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .status(request.getStatus())
                .alliance(request.getAlliance())
                .headquartersCityId(request.getHeadquartersCityId())
                .ownerId(ownerId)
                .build();

        boolean hasSupportDetails =
                request.getSupportEmail() != null
                        || request.getSupportPhone() != null
                        || request.getSupportHours() != null;

        if(hasSupportDetails){
            airline.setSupport(
                    Support.builder()
                            .email(request.getSupportEmail())
                            .phone(request.getSupportPhone())
                            .hours(request.getSupportHours())
                            .build()
            );
        }

        return airline;
    }

    /**
     * Converts an {@link Airline} entity into
     * an {@link AirlineResponse}.
     *
     * @param airline airline entity
     * @return airline response
     */
    public static AirlineResponse toResponse(Airline airline){
        if(airline == null) return null;

        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .support(airline.getSupport())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .ownerId(airline.getOwnerId())
                .updatedById(airline.getUpdatedById())
                .build();
    }

    /**
     * Updates an existing airline entity using
     * the provided request data.
     *
     * @param airline existing airline entity
     * @param request updated airline details
     */
    public static void updateEntity(Airline airline, AirlineRequest request){
        if(airline == null || request == null) return;

        airline.setIataCode(request.getIataCode());
        airline.setIcaoCode(request.getIcaoCode());
        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        airline.setStatus(request.getStatus());
        airline.setAlliance(request.getAlliance());
        airline.setHeadquartersCityId(request.getHeadquartersCityId());

        if(airline.getSupport() == null){
            airline.setSupport(new Support());
        }

        airline.getSupport().setEmail(request.getSupportEmail());
        airline.getSupport().setPhone(request.getSupportPhone());
        airline.getSupport().setHours(request.getSupportHours());
    }
}
