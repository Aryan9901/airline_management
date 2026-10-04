package com.aryan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Flight Operations Service.
 *
 * Handles flight registration, scheduling,
 * flight instances, and lifecycle management.
 *
 * Port: 5006
 */
@SpringBootApplication
public class FlightOpsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightOpsServiceApplication.class, args);
	}

}
