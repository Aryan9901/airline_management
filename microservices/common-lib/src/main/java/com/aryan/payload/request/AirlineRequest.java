package com.aryan.payload.request;

import com.aryan.dto.UserDTO;
import com.aryan.embeddable.Support;
import com.aryan.enums.AirlineStatus;
import com.aryan.payload.response.CityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {
    @NotBlank(message = "IATA Code is mandatory")
    @Size(min = 2, max = 2, message = "IATA Code must be exactly 2 characters")
    private String iataCode;

    @NotBlank(message = "ICAO Code is mandatory")
    @Size(min = 3, max = 3, message = "ICAO Code must be exactly 3 characters")
    private String icaoCode;

    @NotBlank(message = "Airline Name is mandatory")
    private String name;

    private String alias;
    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;
    private Long headquartersCityId;

    private String supportEmail;
    private String supportPhone;
    private String supportHours;
}
