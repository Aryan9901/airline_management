package com.aryan.repository;

import com.aryan.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Aircraft} entities.
 *
 * Provides custom query methods for aircraft retrieval
 * and validation.
 */
public interface AircraftRepository extends JpaRepository<Aircraft,Long> {

    /**
     * Retrieves all aircraft belonging to
     * the specified airline.
     *
     * @param airlineId airline identifier
     * @return list of aircraft
     */
    List<Aircraft> findByAirlineId(Long airlineId);

    /**
     * Checks whether an aircraft with the
     * specified code exists.
     *
     * @param code aircraft code
     * @return true if the aircraft exists
     */
    Boolean existsByCode(String code);

    /**
     * Retrieves an aircraft by its identifier
     * and airline identifier.
     *
     * @param id aircraft identifier
     * @param airlineId airline identifier
     * @return matching aircraft, if found
     */
    Optional<Aircraft> findByIdAndAirlineId(Long id, Long airlineId);

}
