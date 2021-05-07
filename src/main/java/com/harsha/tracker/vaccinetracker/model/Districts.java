package com.harsha.tracker.vaccinetracker.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Districts {
	@Getter
	@Setter
	public static class District{
		private int district_id;
		private String district_name;
	}
	private List<District> districts;
	private int ttl;
}
