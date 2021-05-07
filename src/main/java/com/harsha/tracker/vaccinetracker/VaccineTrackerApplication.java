package com.harsha.tracker.vaccinetracker;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha.tracker.vaccinetracker.model.Centers;
import com.harsha.tracker.vaccinetracker.model.Centers.Center;
import com.harsha.tracker.vaccinetracker.model.Districts;
import com.harsha.tracker.vaccinetracker.model.Session;
import com.harsha.tracker.vaccinetracker.model.States;
import com.harsha.tracker.vaccinetracker.services.EmailService;
import com.harsha.tracker.vaccinetracker.services.MetaDataServices;

@SpringBootApplication
public class VaccineTrackerApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(VaccineTrackerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
/*
		Centers body = service.findAppointmentCalendarByDistrict(581, "10-05-2021").block();
		String vaccineFilter="covaxin";
		List<Center> res = body.getCenters().stream().filter(
				center -> center.getSessions().stream().anyMatch(session -> session.getAvailable_capacity()>0 && session.getVaccine().toLowerCase().contains(vaccineFilter)))
				.collect(Collectors.toList());
		if(res.size()>0) {
			ObjectMapper objectMapper = new ObjectMapper();
			emailSender.sendSimpleMessage(new String[] {"harsha.anirudh@gmail.com"}, "Test vaccine tracker", objectMapper.writeValueAsString(res));
			*/
		}
	}


