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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractReviewTabAssertions<C extends AbstractReviewTabComponent<C, A>, A extends AbstractReviewTabAssertions<C, A>> extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractReviewTabAssertions(C component) {
		super(component);
	}

	public A isTabActive() {
		// @formatter:off
		return assertAll("Active tab should be in focus",
				() -> assertThat(getComponent().getTab())
						.hasClass(Pattern.compile(".*ng-star-inserted.*")));
		// @formatter:on
	}
}