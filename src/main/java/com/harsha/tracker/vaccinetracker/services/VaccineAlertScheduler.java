package com.harsha.tracker.vaccinetracker.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class VaccineAlertScheduler {

	@Autowired
	private AvailabiltyService service;
	private static  boolean isEmailSent = false;
	@Value(value = "${email.to}")
	private String recipients;
	@Value(value = "${district.id}")
	private int districtId;
	@Value(value = "${vaccine.name}")
	private String vaccine;
	
	private Logger log = LogManager.getLogger(VaccineAlertScheduler.class);
	
//	@Scheduled(fixedRate = 30000)
	public void alert() {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
		log.info("Inside scheduler");
		if (!isEmailSent) {
			if (service.sendAlertAvailableAppointmentsByDistrictByWeek(districtId, date, vaccine,recipients))
				isEmailSent = true;
		}

	}
}
