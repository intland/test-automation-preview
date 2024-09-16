package com.intland.codebeamer.integration.api.service.trackeritem;

import java.util.Objects;

public record TrackerItemId(int id) {

	public TrackerItemId(Integer id) {
		this(Objects.requireNonNull(id).intValue());
	} 
	
	public TrackerItemId(int id) {
		this.id = id;
	}
	
}