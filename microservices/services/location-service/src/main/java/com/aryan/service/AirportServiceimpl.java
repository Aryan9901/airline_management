package com.aryan.service;

import com.aryan.mapper.AirportMapper;
import com.aryan.model.Airport;
import com.aryan.model.City;
import com.aryan.payload.request.AirportRequest;
import com.aryan.payload.response.AirportResponse;
import com.aryan.repository.AirportRepository;
import com.aryan.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation responsible for airport-related
 * business operations.
 *
 * Handles:
 * - Airport creation
 * - Airport retrieval
 * - Airport updates
 * - Airport deletion
 * - Airport lookup by city
 *
 * Validates business rules such as:
 * - Unique IATA codes
 * - Existing city associations
 *
 * Coordinates interactions between repositories,
 * mappers, and API response objects.
 */
@Service
@RequiredArgsConstructor
public class AirportServiceimpl implements AirportService{

    /**
     * Repository responsible for airport persistence operations.
     */
    private final AirportRepository airportRepo;

    /**
     * Repository responsible for city persistence operations.
     */
    private final CityRepository cityRepo;

    /**
     * Creates a new airport.
     *
     * Business Rules:
     * - IATA code must be unique
     * - Associated city must exist
     *
     * @param request airport creation payload
     * @return created airport response
     * @throws Exception if airport already exists or city is not found
     */
    @Override
    public AirportResponse createAirport(AirportRequest request)  throws Exception {

        if (airportRepo.existsByIataCode(request.getIataCode())) {
            throw new Exception("Airport with given IATA Code already exists");
        }

        City city = cityRepo.findById(request.getCityId()).orElseThrow(()-> new Exception("City not found!"));

        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepo.save(airport);

        return AirportMapper.toResponse(savedAirport);
    }

    /**
     * Retrieves airport details using its unique identifier.
     *
     * @param id airport identifier
     * @return airport response
     * @throws Exception if airport does not exist
     */
    @Override
    public AirportResponse getAirportById(long id) throws Exception {
        Airport airport = airportRepo.findById(id).orElseThrow(() -> new Exception("Airport not exists with provided id"));
        return AirportMapper.toResponse(airport);
    }

    /**
     * Retrieves all airports available in the system.
     *
     * @return list of airport responses
     */
    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepo.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing airport.
     *
     * Business Rules:
     * - Updated IATA code must remain unique
     * - Only supplied fields are modified
     *
     * @param id airport identifier
     * @param request airport update payload
     * @return updated airport response
     * @throws Exception if airport does not exist or
     *                   updated IATA code already exists
     */
    @Override
    public AirportResponse updateAirport(long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepo.findById(id).orElseThrow(() -> new Exception("Airport not exists with provided id"));

        if (request.getIataCode() != null
                && !existingAirport.getIataCode().equalsIgnoreCase(request.getIataCode())
                && airportRepo.existsByIataCode(request.getIataCode())) {

            throw new Exception("Airport with given IATA Code already exists");
        }

        Airport airportToUpdate = AirportMapper.updateEntity(existingAirport,request);

        Airport updatedAirport = airportRepo.save(airportToUpdate);
        return AirportMapper.toResponse(updatedAirport);
    }

    /**
     * Deletes an airport from the system.
     *
     * @param id airport identifier
     * @throws Exception if airport does not exist
     */
    @Override
    public void deleteAirport(long id)  throws Exception {
        Airport existingAirport = airportRepo.findById(id).orElseThrow(() -> new Exception("Airport not exists with provided id"));

        airportRepo.delete(existingAirport);
    }

    /**
     * Retrieves all airports associated with a specific city.
     *
     * @param cityId city identifier
     * @return list of airport responses belonging to the city
     */
    @Override
    public List<AirportResponse> getAirportByCityId(long cityId) {
        List<Airport> airports = airportRepo.findByCityId(cityId);

        return airports.stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
