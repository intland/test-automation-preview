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

public class ReviewCommentsComponent extends AbstractReviewTabComponent<ReviewCommentsComponent, ReviewCommentsAssertions> {

	public ReviewCommentsComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".cb-review-feedback");
	}

	@Override
	public ReviewCommentsAssertions assertThat() {
		return new ReviewCommentsAssertions(this);
	}

	public CodebeamerLocator getTab() {
		return getCodebeamerPage().locator(getTabId());
	}

	protected String getTabId() {
		return "[data-cy='commentsTabLabel']";
	}
}