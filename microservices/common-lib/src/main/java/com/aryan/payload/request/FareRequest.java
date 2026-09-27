package com.aryan.payload.request;

import com.aryan.payload.response.BaggagepolicyResponse;
import com.aryan.payload.response.FareRulesResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class FareRequest {

    @NotBlank(message = "Fare name is required.")
    private String name;

    @NotBlank(message = "RBD Code is required.")
    private Character rbdCode;

    @NotBlank(message = "Flight id is required.")
    private Long flightId;

    @NotBlank(message = "Cabin class id is required.")
    private Long cabinClassId;

    @NotBlank(message = "Base fare is required.")
    @Positive(message = "Base Fare must be positive")
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;

    @Size(max = 100)
    private String fareLabel;

    // Seat Benefits
    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advanceSeatSelection;
    private Boolean guaranteedSeatTogether;

    // Boarding Benefits
    private Boolean priorityBoarding;
    private Boolean priorityCheckin;
    private Boolean fastTrackSecurity;

    // In-flight Benefits
    private Boolean complimentaryMeals;
    private Boolean premiumMealChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    // Flexibility benefits
    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    // Premium Service benefits
    private Boolean loungeAccess;
    private Boolean airportTransfer;

}
