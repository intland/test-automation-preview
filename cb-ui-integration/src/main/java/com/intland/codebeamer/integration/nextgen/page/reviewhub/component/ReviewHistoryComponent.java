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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

public class ReviewHistoryComponent extends AbstractReviewTabComponent<ReviewHistoryComponent, ReviewHistoryAssertions> {

	public ReviewHistoryComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-review-history");
	}

	@Override
	public ReviewHistoryAssertions assertThat() {
		return new ReviewHistoryAssertions(this);
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	protected String getTabId() {
		return "[data-cy='historyTabLabel']";
	}
}