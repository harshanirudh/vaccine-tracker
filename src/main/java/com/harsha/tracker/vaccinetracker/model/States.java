package com.harsha.tracker.vaccinetracker.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class States {
	@Getter
	@Setter
	public static class State {
		private int state_id;
		private String state_name;
	}
	private List<State> states;
	private int ttl;
}
