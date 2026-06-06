package com.aryan.mapper;

import com.aryan.model.City;
import com.aryan.payload.request.CityRequest;
import com.aryan.payload.response.CityResponse;

/**
 * Mapper utility class responsible for transforming
 * city-related entities and DTOs.
 *
 * Handles conversions between:
 * - CityRequest → City
 * - City → CityResponse
 * - Partial entity updates
 *
 * This layer helps maintain separation between:
 * - Persistence models
 * - API contracts
 * - Business logic
 */
public class CityMapper {

    /**
     * Converts city request payload into city entity.
     *
     * Performs normalization:
     * - Trims unnecessary spaces
     * - Converts codes to uppercase
     *
     * @param request city request payload
     * @return mapped city entity
     */
    public static City toEntity(CityRequest request) {

        if (request == null) {
            return null;
        }

        return City.builder()
                .name(
                        request.getName()
                                .trim()
                )
                .cityCode(
                        request.getCityCode()
                                .toUpperCase()
                                .trim()
                )
                .countryCode(
                        request.getCountryCode()
                                .toUpperCase()
                                .trim()
                )
                .countryName(
                        request.getCountryName()
                                .trim()
                )
                .regionCode(
                        request.getRegionCode()
                                .trim()
                )
                .timeZoneId(
                        request.getTimeZoneOffset()
                )
                .build();
    }

    /**
     * Converts city entity into response payload.
     *
     * @param city city entity
     * @return city response payload
     */
    public static CityResponse toResponse(City city) {

        if (city == null) {
            return null;
        }

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .build();
    }

    /**
     * Updates an existing city entity using request payload.
     *
     * Only non-null fields are updated.
     *
     * Performs normalization:
     * - Trims whitespace
     * - Converts codes to uppercase
     *
     * @param city existing city entity
     * @param request updated city payload
     * @return updated city entity
     */
    public static City updateEntity(
            City city,
            CityRequest request
    ) {

        if (request.getName() != null) {
            city.setName(
                    request.getName()
                            .trim()
            );
        }

        if (request.getCityCode() != null) {
            city.setCityCode(
                    request.getCityCode()
                            .toUpperCase()
                            .trim()
            );
        }

        if (request.getCountryCode() != null) {
            city.setCountryCode(
                    request.getCountryCode()
                            .toUpperCase()
                            .trim()
            );
        }

        if (request.getCountryName() != null) {
            city.setCountryName(
                    request.getCountryName()
                            .trim()
            );
        }

        if (request.getRegionCode() != null) {
            city.setRegionCode(
                    request.getRegionCode()
                            .trim()
            );
        }

        return city;
    }
}