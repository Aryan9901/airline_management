package com.aryan.payload.request;

import com.aryan.embeddable.Address;
import com.aryan.embeddable.GeoCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

/**
 * Request payload used for airport creation and update operations.
 *
 * Contains airport-related information including:
 * - Airport identification details
 * - Address information
 * - Geographical coordinates
 * - Time zone metadata
 * - Associated city reference
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportRequest {

    /**
     * IATA airport code.
     *
     * Must contain exactly 3 characters.
     *
     * Examples:
     * - DEL
     * - BOM
     * - JFK
     */
    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters")
    private String iataCode;

    /**
     * Official airport name.
     */
    @NotBlank(message = "Airport name is required")
    private String name;

    /**
     * Address information associated with the airport.
     */
    @NotNull(message = "Address information is required")
    private Address address;

    /**
     * Identifier of the associated city.
     */
    @NotNull(message = "City ID is mandatory")
    private Long cityId;

    /**
     * Geographical coordinate information.
     */
    @NotNull(message = "GeoCode information is required")
    private GeoCode geoCode;
}