package com.harsha.tracker.vaccinetracker.services;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.harsha.tracker.vaccinetracker.model.Centers;
import com.harsha.tracker.vaccinetracker.model.Districts;
import com.harsha.tracker.vaccinetracker.model.States;

import reactor.core.publisher.Mono;
@Component
public class MetaDataServices {
@Autowired
private WebClient client;
	/** 
	 * To Get All States Details**/
	public Mono<States> getAllStates() {
		return client.get()
		.uri("/v2/admin/location/states")
		.retrieve()
		.bodyToMono(new ParameterizedTypeReference<States>() {});
	}
	
	/** 
	 * To Get All Distirct Details by state id
	 * @param stateId : ID of the state**/
	public Mono<Districts> getDistricts(int stateId) {
		return client.get()
		.uri("/v2/admin/location/districts/"+stateId)
		.retrieve()
		.bodyToMono(new ParameterizedTypeReference<Districts>() {});
	}
	/** 
	 * To Get All Available appointments in a district from a start date
	 * @param district_id : ID of the district
	 * @param date : Start date, to look for appointments starting 7 days from the date
	 * @throws URISyntaxException **/
	public Mono<Centers> findAppointmentCalendarByDistrict(int district_id,String date) throws URISyntaxException {
		UriComponentsBuilder builder=UriComponentsBuilder.newInstance();
		UriComponents uri = builder.path("/v2/appointment/sessions/public/calendarByDistrict")
		.queryParam("district_id", district_id)
		.queryParam("date", date).build();
		return client.get()
				.uri(uri.toUriString())
//		.uri("v2/appointment/sessions/public/calendarByDistrict?district_id=512&date=31-03-2021")
		.retrieve()
		.bodyToMono(Centers.class);
//		.bodyToMono(new ParameterizedTypeReference<Districts>() {});
	}
	
}
