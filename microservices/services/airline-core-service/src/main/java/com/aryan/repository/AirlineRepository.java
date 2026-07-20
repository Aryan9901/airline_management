package com.aryan.repository;

import com.aryan.enums.AirlineStatus;
import com.aryan.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Airline} entities.
 *
 * Provides custom query methods for airline retrieval.
 */
public interface AirlineRepository extends JpaRepository<Airline,Long> {

    /**
     * Retrieves the airline owned by
     * the specified user.
     *
     * @param ownerId owner identifier
     * @return matching airline, if found
     */
    Optional<Airline> findByOwnerId(Long ownerId);

    /**
     * Retrieves all airlines with the
     * specified operational status.
     *
     * @param status airline status
     * @return list of matching airlines
     */
    List<Airline> findByStatus(AirlineStatus status);
}
