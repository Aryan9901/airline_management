package com.aryan.repository;

import com.aryan.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing {@link Fare} persistence operations.
 *
 * Provides fare-specific queries in addition to the
 * standard CRUD operations inherited from {@link JpaRepository}.
 */
public interface FareRepository extends JpaRepository<Fare, Long> {

    /**
     * Checks whether a fare already exists for the given
     * flight, cabin class, and fare name.
     *
     * @param flightId flight identifier
     * @param cabinClassId cabin class identifier
     * @param name fare name
     * @return {@code true} if a matching fare exists
     */
    boolean existsByFlightIdAndCabinClassIdAndName(
            Long flightId,
            Long cabinClassId,
            String name
    );

    /**
     * Retrieves all fares for the specified flight and cabin class.
     *
     * @param flightId flight identifier
     * @param cabinClassId cabin class identifier
     * @return list of matching fares
     */
    List<Fare> findByFlightIdAndCabinClassId(
            Long flightId,
            Long cabinClassId
    );

    /**
     * Checks whether another fare with the same flight,
     * cabin class, and name exists, excluding the specified fare.
     *
     * Used when validating fare updates.
     *
     * @param flightId flight identifier
     * @param cabinClassId cabin class identifier
     * @param name fare name
     * @param id fare identifier to exclude
     * @return {@code true} if another matching fare exists
     */
    boolean existsByFlightIdAndCabinClassIdAndNameAndIdNot(
            Long flightId,
            Long cabinClassId,
            String name,
            Long id
    );

    /**
     * Retrieves fares for the specified flights and cabin class.
     *
     * @param flightIds list of flight identifiers
     * @param cabinClassId cabin class identifier
     * @return list of matching fares
     */
    List<Fare> findByFlightIdInAndCabinClassId(
            List<Long> flightIds,
            Long cabinClassId
    );

}
