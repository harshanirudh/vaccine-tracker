package com.harsha.tracker.vaccinetracker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Subscription {

	private int SUBSCRIPTION_ID;
	private String EMAIL;
	private int JOB_ID;
	private boolean ISACTIVE;
}
