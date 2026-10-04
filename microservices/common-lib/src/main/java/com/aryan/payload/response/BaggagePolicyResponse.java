package com.aryan.payload.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyResponse {

    private Long id;

    private String name;
    private String description;

    // Cabin Baggage
    private Double cabinBaggageMaxWeight;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPiece;
//   todo: either feature drop or included later after completing the project
//   private Double cabinBaggageMaxDimension;

    // Check-in Baggage
    private Double checkInBaggageMaxWeight;
    private Integer checkInBaggagePieces;
    private Double checkInBaggageWeightPerPiece;
    private Integer freeCheckedBagsAllowance;

    // Benefits
    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;

    // Relationships
    private Long airlineId;
    private Long fareId;

    // Audit
    private Instant createdAt;
    private Instant updatedAt;

}
