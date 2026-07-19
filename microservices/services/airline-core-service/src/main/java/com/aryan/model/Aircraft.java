package com.aryan.model;

import com.aryan.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entity representing an aircraft operated by an airline.
 *
 * Stores aircraft specifications, seating configuration,
 * operational status, maintenance information,
 * and airline association.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacturer;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "economy_seats")
    private Integer economySeats = 0;

    @Column(name = "premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Column(name = "first_class_seats")
    private Integer firstClassSeats = 0;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    @Column(name = "max_altitude_ft")
    private Integer maxAltitudeFt;

    @Column(name = "range_km")
    private Integer rangeKm;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;

    private LocalDate nextMaintenanceDate;

    /**
     * Operational status of the aircraft.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    /**
     * Indicates whether the aircraft is currently
     * available for flight scheduling.
     */
    private Boolean isAvailable = true;

    /**
     * Airline that owns or operates this aircraft.
     */
    @ManyToOne
    private Airline airline;

    /**
     * Identifier of the airport where the aircraft
     * is currently located.
     */
    private Long currentAirportId;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Calculates the total seating capacity
     * across all cabin classes.
     *
     * @return total number of seats
     */
    public Integer getTotalSeats(){
        return economySeats + businessSeats + premiumEconomySeats + firstClassSeats;
    }

    /**
     * Determines whether the aircraft is
     * operational and available for scheduling.
     *
     * @return true if the aircraft is operational
     */
    public Boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status) && Boolean.TRUE.equals(isAvailable);
    }

    /**
     * Determines whether the aircraft requires
     * maintenance within the next two weeks.
     *
     * @return true if maintenance is due soon
     */
    public Boolean requiresMaintenance(){
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}
