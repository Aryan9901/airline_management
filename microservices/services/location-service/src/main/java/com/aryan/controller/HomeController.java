package com.aryan.controller;

import com.aryan.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/help")
    public ApiResponse homeController(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Hello Aryan gupta");
        return apiResponse;
    }

}
