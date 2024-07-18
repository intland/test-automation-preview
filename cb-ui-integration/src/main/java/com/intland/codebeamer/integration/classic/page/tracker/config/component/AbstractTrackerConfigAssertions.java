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

package com.intland.codebeamer.integration.classic.page.tracker.config.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractTrackerConfigAssertions<C extends AbstractTrackerConfigTab<C, A>, A extends AbstractTrackerConfigAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractTrackerConfigAssertions(C component) {
		super(component);
	}

	public A isTabActive() {
		return assertAll("Active tab should be in focus",
				() -> assertThat(getComponent().getTab()).hasClass(Pattern.compile(".*ditch-focused.*")));
	}
}
