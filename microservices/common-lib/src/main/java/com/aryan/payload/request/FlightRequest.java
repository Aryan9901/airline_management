package com.aryan.payload.request;

import com.aryan.enums.FlightStatus;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequest {

    @NotBlank(message = "Flight Number is required.")
    @Size(max = 10)
    private String flightNumber;

    private Long airlineId;

    @NotBlank(message = "Aircraft Id is required.")
    private Long aircraftId;

    @NotBlank(message = "Departure Airport Id is required.")
    private Long departureAirportId;

    @NotBlank(message = "Arrival Airport Id is required.")
    private Long arrivalAirportId;

    private FlightStatus status;

}
