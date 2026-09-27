package com.aryan.controller;

import com.aryan.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse HomeController(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("hello everyone i am pricing service of airline microservices, " +
            "Pricing service manages fares, fare rules, " +
            "and baggage policies. It is responsible for price calculation and pricing rules."
        );
        return apiResponse;
    }

}
