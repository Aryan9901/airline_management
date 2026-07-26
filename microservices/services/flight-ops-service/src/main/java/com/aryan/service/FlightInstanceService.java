package com.aryan.service;

import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.FlightInstanceResponse;

public interface FlightInstance {

    FlightInstanceResponse createFlightInstance(
            Long userId,
            FlightInstanceRequest request
    );

}
