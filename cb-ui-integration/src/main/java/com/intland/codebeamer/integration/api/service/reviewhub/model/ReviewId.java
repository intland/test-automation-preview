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

package com.intland.codebeamer.integration.api.service.reviewhub.model;

import java.util.Objects;

public record ReviewId(int id) {

	public ReviewId(Integer id) {
		this(Objects.requireNonNull(id).intValue());
	}

	public ReviewId(int id) {
		this.id = id;
	}

}
