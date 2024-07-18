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

package com.intland.codebeamer.integration.api.service;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;

public abstract class Member {
	public abstract int getId();
	public abstract String getName();
	public abstract ArtifactType getArtifactType();
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Member)) {
			return false;
		}
		return this.getArtifactType() == ((Member) o).getArtifactType() && this.getId() == ((Member) o).getId();
	}
}
