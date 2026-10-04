package com.aryan.payload.response;

import com.aryan.enums.CabinClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareResponse {

    private Long id;
    private String name;
    private Character rbdCode;
    private Long flightId;
    private Long cabinClassId;
    private CabinClassType cabinClass;

    // Pricing
    private Double baseFare;
    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;
    private Double totalPrice;
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

    // Relationships
    private Long fareRulesId;
    private FareRulesResponse fareRules;
    private BaggagePolicyResponse baggagePolicy;

    // Audit
    private Instant createdAt;
    private Instant updatedAt;

}
