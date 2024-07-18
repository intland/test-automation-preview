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

package com.intland.codebeamer.integration.api.builder.reference;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReferenceBuilder {

	protected List<ReferenceFilter> filters = new ArrayList<>();

	public List<ReferenceFilter> build() {
		return filters;
	}
}
