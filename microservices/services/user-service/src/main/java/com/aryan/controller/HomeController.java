package com.aryan.controller;

import com.aryan.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health check controller for the User Service.
 */
@RestController
public class HomeController {

    @GetMapping("/help")
    public ApiResponse getHelp(){
        return new ApiResponse("hay everyone! I'm user service of airline management system.");
    }
}

