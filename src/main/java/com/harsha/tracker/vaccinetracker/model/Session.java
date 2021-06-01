package com.harsha.tracker.vaccinetracker.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Session {

	private String session_id;
	private String date;
	private int available_capacity;
	private int available_capacity_dose1;
	private int available_capacity_dose2;
	private int min_age_limit;
	private String vaccine;
	private List<String> slots;
}
