package com.aryan.controller;

import com.aryan.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse homeController(){
        ApiResponse apiResponse = new ApiResponse(
                "Flight Operational Services manages Flights," +
                " Flight Schedules, and Flight Instances. " +
                        "It represents the core operational flight lifecycle."
        );
        return apiResponse;
    }
}
