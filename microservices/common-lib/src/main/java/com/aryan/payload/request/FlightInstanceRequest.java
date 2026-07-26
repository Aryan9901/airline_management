package com.aryan.payload.request;

import com.aryan.enums.FlightStatus;
import com.aryan.payload.response.AirportResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstanceRequest {
    @NotNull(message = "Flight ID is required.")
    private Long flightId;

    private Long airlineId;

    private Long scheduleId;

    private Long departureAirportId;

    private Long arrivalAirportId;

    @NotNull(message = "Departure Date Time is required")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival Date Time is required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total Seats is required")
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;

    private Boolean isActive;
}
