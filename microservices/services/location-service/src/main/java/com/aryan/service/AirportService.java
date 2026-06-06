package com.aryan.service;

import com.aryan.payload.request.AirportRequest;
import com.aryan.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest reequest) throws Exception;
    AirportResponse getAirportById(long id) throws Exception;

    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(long id, AirportRequest request) throws Exception;

    void deleteAirport(long id) throws Exception;
    List<AirportResponse> getAirportByCityId(long cityId) ;
}
