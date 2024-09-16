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

package com.intland.codebeamer.integration.api.service.artifact;

import java.util.Arrays;

public enum ArtifactType {
	USER_ACCOUNT (1),
	PROJECT (2),
	TRACKER (3),
	FORUM (4),
	ARTIFACT (5),
	BUILD (6),
	SOURCE (7),
	ARTIFACT_COMMENT (8),
	TRACKER_ITEM (9),
	RACKER_ITEM_COMMENT (10),
	TRACKER_ITEM_ATTACHMENT (12),
	FORUM_POST (11),
	ROLE (13),
	SCM_CHANGE_SET (14),
	VIEW (15),
	BUILD_LOG (16),
	ENTITY_TAG (17),
	SCM_REPOSITORY (18),
	SCM_PUSH (19),
	PROJECT_ROLE_ARTIFACT (20),
	GLOBAL_TYPE_ARTIFACT (22),
	FILE_LINK_ARTIFACT (21);

	private final int value;

	public int getValue() {
		return value;
	}

	private ArtifactType(int value) {
		this.value = value;
	}
	
	public static ArtifactType findById(int value) {
		return Arrays.stream(ArtifactType.values())
				.filter(type -> type.getValue() == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("ArtifactType cannot be found by id: %s".formatted(Integer.valueOf(value))));
	} 
}
