package com.aryan.controller;

import com.aryan.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse homeController(){
        ApiResponse apiResponse = new ApiResponse("I am Airline Core Services & I manages airlines, aircraft fleet, aircraft models, and operational inventory for the airline system.");
        return apiResponse;
    }
}
