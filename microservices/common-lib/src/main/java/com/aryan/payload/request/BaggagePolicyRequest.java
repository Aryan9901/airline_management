package com.aryan.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyRequest {

    @NotBlank(message = "Policy name is required")
    private String name;

    @NotNull(message = "Fare id is required")
    private Long fareId;

    private String description;

    @PositiveOrZero(message = "Cabin baggage max weight should be greater than or equal to 0")
    private Double cabinBaggageMaxWeight;

    @PositiveOrZero(message = "Cabin baggage pieces should be greater than or equal to 0")
    private Integer cabinBaggagePieces;

    @PositiveOrZero(message = "Cabin baggage weight per piece should be greater than or equal to 0")
    private Double cabinBaggageWeightPerPiece;

//   todo: either feature drop or included later after completing the project
//    @PositiveOrZero(message = "Cabin baggage max dimensions should be greater than or equal to 0")
//    private Double cabinBaggageMaxDimension;

    @PositiveOrZero(message = "Checkin baggage max weight should be greater than or equal to 0")
    private Double checkInBaggageMaxWeight;

    @PositiveOrZero(message = "Checkin baggage pieces should be greater than or equal to 0")
    private Integer checkInBaggagePieces;

    @PositiveOrZero(message = "Checkin baggage weight per piece should be greater than or equal to 0")
    private Double checkInBaggageWeightPerPiece;

    @PositiveOrZero(message = "Free checked bags allowance should be greater than or equal to 0")
    private Integer freeCheckedBagsAllowance;

    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;

}
