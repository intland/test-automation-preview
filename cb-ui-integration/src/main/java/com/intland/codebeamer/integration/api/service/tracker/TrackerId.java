package com.intland.codebeamer.integration.api.service.tracker;

import java.util.Objects;

public record TrackerId(int id) {

	public TrackerId(Integer id) {
		this(Objects.requireNonNull(id).intValue());
	} 
	
	public TrackerId(int id) {
		this.id = id;
	}
	
}
