package com.aryan.payload.response;

import com.aryan.dto.UserDTO;
import com.aryan.embeddable.Support;
import com.aryan.enums.AirlineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlineResponse {
    private Long id;

    private String iataCode;
    private String icaoCode;

    private String name;
    private String alias;
    private String alliance;
    private String country;

    private String logoUrl;
    private String website;

    private AirlineStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDTO owner;
    private Long updatedById;

    private CityResponse headquartersCity;
    private Support support;
}
