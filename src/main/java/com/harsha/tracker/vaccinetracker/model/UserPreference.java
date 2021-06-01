package com.harsha.tracker.vaccinetracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPreference {

	private int jobId;
	private int districtId;
	private String vaccine;
	private String recipients;
}
