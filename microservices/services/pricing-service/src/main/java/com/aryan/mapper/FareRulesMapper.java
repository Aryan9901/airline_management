package com.aryan.mapper;


import com.aryan.model.Fare;
import com.aryan.model.FareRules;
import com.aryan.payload.request.FareRulesRequest;
import com.aryan.payload.response.FareRulesResponse;
import com.aryan.util.MapperUtils;

public class FareRulesMapper {

    /**
     * Converts FareRulesRequest to FareRules entity.
     *
     * @param request the fare rules request
     * @param fare the Fare entity associated with these rules
     * @return mapped FareRules entity, or null if request is null
     */
    public static FareRules toEntity(
            FareRulesRequest request,
            Fare fare
    ) {
        if (request == null) {
            return null;
        }

        return FareRules.builder()
                .ruleName(request.getRuleName())
                .fare(fare)
                .airlineId(request.getAirlineId())
                .isRefundable(request.getIsRefundable())
                .changeFee(request.getChangeFee())
                .cancellationFee(request.getCancellationFee())
                .refundDeadlineDays(request.getRefundDeadlineDays())
                .changeDeadlineHours(request.getChangeDeadlineHours())
                .isChangeable(request.getIsChangeable())
                .build();
    }

    /**
     * Converts FareRules entity to FareRulesResponse.
     *
     * @param fareRules the FareRulesRequest entity
     * @return mapped FareRulesResponse, or null if fareRules is null
     */
    public static FareRulesResponse toResponse(
            FareRules fareRules
    ) {
        if (fareRules == null) return null;

        return FareRulesResponse.builder()
                .id(fareRules.getId())
                .ruleName(fareRules.getRuleName())
                .fareId(fareRules.getFare() != null ? fareRules.getFare().getId() : null)
                .airlineId(fareRules.getAirlineId())
                .isRefundable(fareRules.getIsRefundable())
                .changeFee(fareRules.getChangeFee())
                .cancellationFee(fareRules.getCancellationFee())
                .refundDeadlineDays(fareRules.getRefundDeadlineDays())
                .changeDeadlineHours(fareRules.getChangeDeadlineHours())
                .isChangeable(fareRules.getIsChangeable())
                .createdAt(fareRules.getCreatedAt())
                .updatedAt(fareRules.getUpdatedAt())
                .build();
    }

    /**
     * Updates an existing FareRules entity using non-null
     * values from the request.
     *
     * @param request the update request
     * @param fareRules existing FareRules entity
     */
    public static void updateEntity(
            FareRulesRequest request,
            FareRules fareRules
    ) {
        if (request == null || fareRules == null) {
            return;
        }

        MapperUtils.updateIfNotNull(
                request.getRuleName(),
                fareRules::setRuleName
        );

        MapperUtils.updateIfNotNull(
                request.getAirlineId(),
                fareRules::setAirlineId
        );

        MapperUtils.updateIfNotNull(
                request.getIsRefundable(),
                fareRules::setIsRefundable
        );

        MapperUtils.updateIfNotNull(
                request.getChangeFee(),
                fareRules::setChangeFee
        );

        MapperUtils.updateIfNotNull(
                request.getCancellationFee(),
                fareRules::setCancellationFee
        );

        MapperUtils.updateIfNotNull(
                request.getRefundDeadlineDays(),
                fareRules::setRefundDeadlineDays
        );

        MapperUtils.updateIfNotNull(
                request.getChangeDeadlineHours(),
                fareRules::setChangeDeadlineHours
        );

        MapperUtils.updateIfNotNull(
                request.getIsChangeable(),
                fareRules::setIsChangeable
        );
    }
}
