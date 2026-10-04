package com.aryan.mapper;


import com.aryan.embeddable.*;
import com.aryan.model.Fare;
import com.aryan.payload.request.FareRequest;
import com.aryan.payload.response.*;
import com.aryan.util.MapperUtils;

/**
 * Utility class for converting between
 * {@link Fare}, {@link FareRequest}, and {@link FareResponse}.
 */
public class FareMapper {

    /**
     * Converts a fare request into a {@link Fare} entity.
     *
     * Calculates current price from base fare, taxes, and fees
     * if not explicitly provided.
     *
     * @param request fare request payload
     * @return mapped fare entity
     */
    public static Fare toEntity(FareRequest request){
        if(request == null) return null;

        Double calculatedPrice = request.getCurrentPrice();
        if(calculatedPrice == null){
            calculatedPrice = request.getBaseFare()
                    + request.getTaxesAndFees()
                    + request.getAirlineFees();
        }

        SeatBenefits seatBenefits = SeatBenefits.builder()
                .extraSeatSpace(bool(request.getExtraSeatSpace()))
                .preferredSeatChoice(bool(request.getPreferredSeatChoice()))
                .advanceSeatSelection(bool(request.getAdvanceSeatSelection()))
                .guaranteedSeatTogether(bool(request.getGuaranteedSeatTogether()))
                .build();

        BoardingBenefits boardingBenefits = BoardingBenefits.builder()
                .priorityBoarding(bool(request.getPriorityBoarding()))
                .priorityCheckin(bool(request.getPriorityCheckin()))
                .fastTrackSecurity(bool(request.getFastTrackSecurity()))
                .build();

        InFlightBenefits inFligtBenefits = InFlightBenefits.builder()
                .complimentaryMeals(bool(request.getComplimentaryMeals()))
                .complimentaryBeverages(bool(request.getComplimentaryBeverages()))
                .premiumMealChoice(bool(request.getPremiumMealChoice()))
                .inFlightEntertainment(bool(request.getInFlightEntertainment()))
                .inFlightInternet(bool(request.getInFlightInternet()))
                .build();

        FlexibilityBenefits flexibilityBenefits = FlexibilityBenefits.builder()
                .freeDateChange(bool(request.getFreeDateChange()))
                .partialRefund(bool(request.getPartialRefund()))
                .fullRefund(bool(request.getFullRefund()))
                .build();

        PremiumServiceBenefits premiumServiceBenefits = PremiumServiceBenefits.builder()
                .loungeAccess(bool(request.getLoungeAccess()))
                .airportTransfer(bool(request.getAirportTransfer()))
                .build();

        return Fare.builder()
                .name(request.getName())
                .rbdCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxesAndFees(request.getTaxesAndFees())
                .airlineFees(request.getAirlineFees())
                .currentPrice(calculatedPrice)
                .fareLable(request.getFareLabel())
                .seatBenefits(seatBenefits)
                .boardingBenefits(boardingBenefits)
                .inFlightBenefits(inFligtBenefits)
                .flexibilityBenefits(flexibilityBenefits)
                .premiumServiceBenefits(premiumServiceBenefits)
                .build();
    }

    /**
     * Converts a {@link Fare} entity into a {@link FareResponse}.
     *
     * @param fare fare entity
     * @return fare response
     */
    public static FareResponse toResponse(
            Fare fare
    ){
        if(fare == null) return null;

        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRbdCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .baseFare(fare.getBaseFare())
                .taxesAndFees(fare.getTaxesAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .totalPrice(fare.getTotalPrice())
                .fareLabel(fare.getFareLable())
                .fareRulesId(fare.getFareRules() != null ? fare.getFareRules().getId() : null)
                //seat benefits
                .extraSeatSpace(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getExtraSeatSpace() : null )
                .preferredSeatChoice(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getPreferredSeatChoice() : null )
                .advanceSeatSelection(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getAdvanceSeatSelection() : null )
                .guaranteedSeatTogether(fare.getSeatBenefits() != null ? fare.getSeatBenefits().isGuaranteedSeatTogether() : null )

                //boarding benefits
                .priorityBoarding(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityBoarding() : null )
                .priorityCheckin(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityCheckin() : null )
                .fastTrackSecurity(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getFastTrackSecurity() : null )

                //in flight benefits
                .complimentaryMeals(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryMeals() : null )
                .complimentaryBeverages(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryBeverages() : null )
                .premiumMealChoice(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getPremiumMealChoice() : null )
                .inFlightEntertainment(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightEntertainment() : null )
                .inFlightInternet(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightInternet() : null )

                //flexibility benefits
                .freeDateChange(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFreeDateChange() : null )
                .partialRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFullRefund() : null )
                .fullRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getPartialRefund() : null )

                //premium service benefits
                .airportTransfer(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getAirportTransfer() : null )
                .loungeAccess(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getLoungeAccess() : null )
                // Nested response
                .fareRules(fare.getFareRules() != null ? FareRulesMapper.toResponse(fare.getFareRules()) : null)
                .baggagePolicy(fare.getBaggagePolicy() != null ? BaggagePolicyMapper.toResponse(fare.getBaggagePolicy()) : null)

                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }

    /**
     * Updates an existing fare entity using non-null values from the request.
     *
     * @param request updated fare details
     * @param fare existing fare entity
     */
    public static void updateEntity(FareRequest request, Fare fare){
        if(request == null || fare == null) return;

        MapperUtils.updateIfNotNull(request.getName(), fare::setName);
        MapperUtils.updateIfNotNull(request.getRbdCode(), fare::setRbdCode);
        MapperUtils.updateIfNotNull(request.getFlightId(), fare::setFlightId);
        MapperUtils.updateIfNotNull(request.getCabinClassId(), fare::setCabinClassId);

        MapperUtils.updateIfNotNull(request.getBaseFare(), fare::setBaseFare);
        MapperUtils.updateIfNotNull(request.getTaxesAndFees(), fare::setTaxesAndFees);
        MapperUtils.updateIfNotNull(request.getAirlineFees(), fare::setAirlineFees);
        MapperUtils.updateIfNotNull(request.getCurrentPrice(), fare::setCurrentPrice);
        MapperUtils.updateIfNotNull(request.getFareLabel(), fare::setFareLable);

        // update embedded benefits
        SeatBenefits sb = new SeatBenefits();
        MapperUtils.updateIfNotNull(request.getExtraSeatSpace(), sb::setExtraSeatSpace);
        MapperUtils.updateIfNotNull(request.getPreferredSeatChoice(), sb::setPreferredSeatChoice);
        MapperUtils.updateIfNotNull(request.getAdvanceSeatSelection(), sb::setAdvanceSeatSelection);
        MapperUtils.updateIfNotNull(request.getGuaranteedSeatTogether(), sb::setGuaranteedSeatTogether);

        BoardingBenefits bb = new BoardingBenefits();
        MapperUtils.updateIfNotNull(request.getPriorityBoarding(), bb::setPriorityBoarding);
        MapperUtils.updateIfNotNull(request.getPriorityCheckin(), bb::setPriorityCheckin);
        MapperUtils.updateIfNotNull(request.getFastTrackSecurity(), bb::setFastTrackSecurity);

        InFlightBenefits ifb = new InFlightBenefits();
        MapperUtils.updateIfNotNull(request.getComplimentaryMeals(), ifb::setComplimentaryMeals);
        MapperUtils.updateIfNotNull(request.getComplimentaryBeverages(), ifb::setComplimentaryBeverages);
        MapperUtils.updateIfNotNull(request.getPremiumMealChoice(), ifb::setPremiumMealChoice);
        MapperUtils.updateIfNotNull(request.getInFlightInternet(), ifb::setInFlightInternet);
        MapperUtils.updateIfNotNull(request.getInFlightEntertainment(), ifb::setInFlightEntertainment);

        FlexibilityBenefits fb = new FlexibilityBenefits();
        MapperUtils.updateIfNotNull(request.getPartialRefund(), fb::setPartialRefund);
        MapperUtils.updateIfNotNull(request.getFullRefund(), fb::setFullRefund);
        MapperUtils.updateIfNotNull(request.getFreeDateChange(), fb::setFreeDateChange);

        PremiumServiceBenefits psb = new PremiumServiceBenefits();
        MapperUtils.updateIfNotNull(request.getLoungeAccess(), psb::setLoungeAccess);
        MapperUtils.updateIfNotNull(request.getAirportTransfer(), psb::setAirportTransfer);
    }

    /**
     * Safely converts a nullable Boolean to a primitive boolean.
     *
     * @param value nullable boolean value
     * @return false if null, otherwise the boolean value
     */
    private static boolean bool(Boolean value){
        return value != null ? value : false;
    }

}
