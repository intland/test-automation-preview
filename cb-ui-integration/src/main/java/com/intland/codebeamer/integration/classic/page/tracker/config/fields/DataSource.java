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

package com.intland.codebeamer.integration.classic.page.tracker.config.fields;

public enum DataSource {

	OPTIONS(0),
	USERS(1),
	PROJECTS(2),
	TRACKERS(3),
	WORK_CONFIG_ITEMS(9),
	REPOSITORIES(18);

	private int typeId;

	DataSource(int typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getTypeIdAsString() {
		return String.valueOf(typeId);
	}
}
