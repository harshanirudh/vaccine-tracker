package com.harsha.tracker.vaccinetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VaccineTrackerApplication extends SpringBootServletInitializer{

	
	public static void main(String[] args) {
		SpringApplication.run(VaccineTrackerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VaccineTrackerApplication.class);
	}

}
