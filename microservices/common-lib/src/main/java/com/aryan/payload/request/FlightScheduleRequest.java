package com.aryan.payload.request;

import com.aryan.payload.response.AirportResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {

    @NotNull(message = "Flight Id is required")
    private Long flightId;


    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure Time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival Time is required")
    private LocalTime arrivalTime;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;

    private Boolean isActive;

}
