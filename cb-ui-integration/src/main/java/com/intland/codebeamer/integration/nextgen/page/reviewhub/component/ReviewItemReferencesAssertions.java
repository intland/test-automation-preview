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

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReviewItemReferencesAssertions
		extends AbstractCodebeamerComponentAssert<ReviewItemReferencesComponent, ReviewItemReferencesAssertions> {

	protected ReviewItemReferencesAssertions(ReviewItemReferencesComponent component) {
		super(component);
	}

	public ReviewItemReferencesAssertions assertNewVersionAvailableBadgeForReferences() {
		return assertAll("New version badge available references",
				() -> assertThat(getComponent().getNewVersionAvailableBadgeForReferencesButton()).isVisible());
	}
}
