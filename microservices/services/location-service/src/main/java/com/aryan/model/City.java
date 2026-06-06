package com.aryan.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a city record in the system.
 *
 * Stores geographical and regional information
 * associated with a city including:
 * - City details
 * - Country information
 * - Region information
 * - Time zone metadata
 *
 * This entity is primarily used for:
 * - Address management
 * - Location mapping
 * - Regional filtering
 * - Country-based searches
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class City {

    /**
     * Unique identifier for the city.
     *
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Name of the city.
     *
     * Example:
     * - Mumbai
     * - London
     * - New York
     */
    @Column(nullable = false)
    private String name;

    /**
     * Unique city code used for internal
     * identification and lookups.
     *
     * Example:
     * - MUM
     * - DEL
     * - NYC
     */
    @Column(nullable = false, unique = true)
    private String cityCode;

    /**
     * ISO country code associated with the city.
     *
     * Example:
     * - IN
     * - US
     * - UK
     */
    @Column(nullable = false, unique = false)
    private String countryCode;

    /**
     * Full country name associated with the city.
     *
     * Example:
     * - India
     * - United States
     */
    @Column(nullable = false)
    private String countryName;

    /**
     * Regional or state code associated with the city.
     *
     * Example:
     * - MH
     * - CA
     * - DL
     */
    @Column(nullable = false)
    private String regionCode;

    /**
     * Time zone identifier associated with the city.
     *
     * Example:
     * - Asia/Kolkata
     * - America/New_York
     *
     * Maximum length: 50 characters.
     */
    @Column(name = "time_zone_id", length = 50)
    private String timeZoneId;
}