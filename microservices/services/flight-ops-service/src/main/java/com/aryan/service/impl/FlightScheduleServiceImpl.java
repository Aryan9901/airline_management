package com.aryan.service.impl;

import com.aryan.enums.FlightStatus;
import com.aryan.mapper.FlightInstanceMapper;
import com.aryan.mapper.FlightScheduleMapper;
import com.aryan.model.Flight;
import com.aryan.model.FlightInstance;
import com.aryan.model.FlightSchedule;
import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.request.FlightScheduleRequest;
import com.aryan.payload.response.AircraftResponse;
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.AirportResponse;
import com.aryan.payload.response.FlightScheduleResponse;
import com.aryan.repository.FlightRepository;
import com.aryan.repository.FlightScheduleRepository;
import com.aryan.service.FlightInstanceService;
import com.aryan.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;

    private final FlightRepository flightRepository;

    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception {
        // todo: watch foor airlineId
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found with id " + request.getFlightId()));

        if(request.getEndDate().isBefore(request.getEndDate())){
            throw new Exception("End date must be after the start date");
        }

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);

        FlightSchedule scheduledFlight = flightScheduleRepository.save(flightSchedule);
//        create flight instance for saved schedule

        List<DayOfWeek> operatingDays = scheduledFlight.getOperatingDays();
        LocalDate startDate = scheduledFlight.getStartDate();
        LocalDate endDate = scheduledFlight.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest
                .builder()
                .scheduleId(scheduledFlight.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for(LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)){
            if(operatingDays.contains(date.getDayOfWeek())){
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date, scheduledFlight.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date, scheduledFlight.getArrivalTime())
                );
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }
        }

        return convertToFlightScheduleResponse(scheduledFlight);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) {
        // todo: watch airlineId
        List<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(airlineId);
        return schedules.stream().map(schedule -> convertToFlightScheduleResponse(schedule)).toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        FlightScheduleMapper.updateEntity(request, flightSchedule);
        return convertToFlightScheduleResponse(flightScheduleRepository.save(flightSchedule));
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(
                        ()-> new Exception("Flight not found with id " + id)
                );

        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule){
        AirportResponse departureAirport = AirportResponse.builder().id(flightSchedule.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightSchedule.getArrivalAirportId()).build();

        return FlightScheduleMapper.toResponse(flightSchedule, departureAirport, arrivalAirport);
    }
}
