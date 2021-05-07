package com.harsha.tracker.vaccinetracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Centers {
	@Getter
	@Setter
	@ToString
	@JsonIgnoreProperties(ignoreUnknown = true,value = "long")
	public static class Center{
		private int center_id;
		private String name;
		private String address;
		private String state_name;
		private String district_name;
		private String block_name;
		private int pincode;
		@JsonIgnore
		private int lat;
		@JsonIgnore
		private int LONG;
		private String from;
		private String to;
		private String fee_type;
		private List<Session> sessions;
	}
	private List<Center> centers;
}
