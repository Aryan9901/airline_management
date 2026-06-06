package com.aryan.payload.response;

import com.aryan.embeddable.Address;
import com.aryan.embeddable.GeoCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response payload representing airport details.
 *
 * Returned during:
 * - Airport creation operations
 * - Airport update operations
 * - Airport fetch operations
 * - Airport search operations
 *
 * Contains airport metadata, geographical details,
 * address information, and associated city details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {

    /**
     * Unique identifier for the airport.
     */
    private Long id;

    /**
     * IATA airport code.
     *
     * Examples:
     * - DEL
     * - BOM
     * - JFK
     */
    private String iataCode;

    /**
     * Official airport name.
     *
     * Examples:
     * - Indira Gandhi International Airport
     * - John F. Kennedy International Airport
     */
    private String name;

    /**
     * Detailed formatted airport display name.
     *
     * Commonly used in:
     * - Dropdown selections
     * - Search results
     * - Booking interfaces
     *
     * Example:
     * DEL - Indira Gandhi International Airport
     */
    private String detailedName;

    /**
     * Address information associated with the airport.
     */
    private Address address;

    /**
     * Associated city details.
     */
    private CityResponse city;

    /**
     * Geographical coordinate information.
     *
     * Typically contains:
     * - Latitude
     * - Longitude
     */
    private GeoCode geoCode;

}