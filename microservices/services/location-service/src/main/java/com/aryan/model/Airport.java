package com.aryan.model;

import com.aryan.embeddable.Address;
import com.aryan.embeddable.GeoCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

/**
 * Entity representing an airport within the system.
 *
 * Stores airport-related information including:
 * - Airport identification codes
 * - Address information
 * - Geographical coordinates
 * - Time zone details
 * - Associated city reference
 *
 * This entity is commonly used for:
 * - Flight search systems
 * - Route management
 * - Airport lookup operations
 * - Travel and booking services
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Airport {

    /**
     * Unique identifier for the airport.
     *
     * Auto-generated using JPA generation strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * IATA airport code.
     *
     * Must be unique and exactly 3 characters.
     *
     * Examples:
     * - DEL
     * - BOM
     * - JFK
     */
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    /**
     * Official name of the airport.
     *
     * Examples:
     * - Indira Gandhi International Airport
     * - Chhatrapati Shivaji Maharaj International Airport
     */
    @Column(nullable = false)
    private String name;

    /**
     * Embedded address information associated with the airport.
     *
     * Stored within the same airport table.
     */
    @Embedded
    private Address address;

    /**
     * Embedded geographical coordinate information.
     *
     * Typically, contains:
     * - Latitude
     * - Longitude
     */
    @Embedded
    private GeoCode geoCode;

    /**
     * Associated city to which the airport belongs.
     *
     * Many airports can belong to a single city.
     */
    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    @Transient
    public String getDetailedName(){
        if(city != null && city.getCountryCode() != null) return name.toUpperCase() + "/" + city.getCityCode();

        return name.toUpperCase();
    }
}