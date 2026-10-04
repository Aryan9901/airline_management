package com.aryan.repository;

import com.aryan.model.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing {@link FlightSchedule} entities.
 *
 * Provides custom query methods for schedule retrieval
 * by airline.
 */
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Long> {

    /**
     * Retrieves all flight schedules for the specified airline.
     *
     * @param airlineId airline identifier
     * @return list of flight schedules
     */
    List<FlightSchedule> findByFlightAirlineId(Long airlineId);

}
