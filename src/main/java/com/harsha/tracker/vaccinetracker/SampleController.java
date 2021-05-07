package com.harsha.tracker.vaccinetracker;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.tracker.vaccinetracker.services.MetaDataServices;

import reactor.core.publisher.Mono;

@RestController
public class SampleController {

	@Autowired
	private MetaDataServices service;

	@GetMapping("/appointments/calendarByDistrict")
	public Mono<Object> getAvailableAppointmentsByDistrictByWeek(@RequestParam int district_id, @RequestParam String date,
			@RequestParam String vaccine) {
		Mono<Object> filter = null;
		try{
			filter=service.findAppointmentCalendarByDistrict(district_id,date)
					.map(centers->centers.getCenters().stream()
							.filter(center -> center.getSessions().stream()
									.anyMatch(session-> (session.getAvailable_capacity()>=0 && session.getVaccine().contains(vaccine.toUpperCase())))).collect(Collectors.toList()));

		}catch(URISyntaxException e){

		}return filter;
	}
}
