package com.aryan.service;

import com.aryan.payload.request.FlightScheduleRequest;
import com.aryan.payload.response.FlightScheduleResponse;

import java.util.List;

/**
 * Service contract for flight schedule management operations.
 */
public interface FlightScheduleService {

    /**
     * Creates a new flight schedule and generates flight instances
     * for each operating day within the schedule date range.
     *
     * @param airlineId airline identifier
     * @param request flight schedule details
     * @return created flight schedule
     * @throws Exception if the flight is not found
     */
    FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception;

    /**
     * Retrieves a flight schedule by its identifier.
     *
     * @param id flight schedule identifier
     * @return flight schedule details
     * @throws Exception if the schedule is not found
     */
    FlightScheduleResponse getFlightScheduleById(Long id) throws Exception;

    /**
     * Retrieves all flight schedules for the specified airline.
     *
     * @param airlineId airline identifier
     * @return list of flight schedules
     */
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId);

    /**
     * Updates an existing flight schedule.
     *
     * @param id flight schedule identifier
     * @param request updated schedule details
     * @return updated flight schedule
     * @throws Exception if the schedule is not found
     */
    FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception;

    /**
     * Deletes a flight schedule.
     *
     * @param id flight schedule identifier
     * @throws Exception if the schedule is not found
     */
    void deleteFlightSchedule(Long id) throws Exception;

}
