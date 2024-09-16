/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.api.service.tracker;

import java.util.Objects;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.swagger.client.model.FieldReference;

public class TrackerField extends Member {

	private TrackerFieldId id;

	private String name;

	public TrackerField(String name) {
		this.name = name;
	}

	public TrackerField(FieldReference fieldReference) {
		this.id = new TrackerFieldId(Objects.requireNonNull(fieldReference.getId()).intValue());
		this.name = fieldReference.getName();
	}

	@Override
	public int getId() {
		return id.id();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ArtifactType getArtifactType() {
		return ArtifactType.TRACKER;
	}

	public TrackerFieldId getTrackerFieldId() {
		return id;
	}

}
