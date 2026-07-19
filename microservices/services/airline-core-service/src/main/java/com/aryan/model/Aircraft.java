package com.aryan.model;

import com.aryan.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;

    @ManyToOne
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Integer getTotalSeats(){
        return economySeats + businessSeats + premiumEconomySeats + firstClassSeats;
    }

    public Boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status) && Boolean.TRUE.equals(isAvailable);
    }

    public Boolean requiresMaintenance(){
        return nextMaintenanceDate != null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }
}
