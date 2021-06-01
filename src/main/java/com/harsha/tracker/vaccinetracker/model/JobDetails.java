package com.harsha.tracker.vaccinetracker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobDetails {

	private int JOB_ID;
	private int STATE_ID;
	private int DISTRICT_ID;
	private String VACCINE_TYPE;
	private boolean ISACTIVE;
}
