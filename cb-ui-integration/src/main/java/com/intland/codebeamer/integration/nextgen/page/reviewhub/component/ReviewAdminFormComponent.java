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
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewAdminFormComponent
		extends AbstractCodebeamerComponent<ReviewAdminFormComponent, ReviewAdminFormAssertions> {

	public ReviewAdminFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-review-admin");
	}

	public CodebeamerLocator getReviewAdminPageTitle() {
		return this.locator(".review-admin-header .review-title");
	}

	@Override
	public ReviewAdminFormAssertions assertThat() {
		return new ReviewAdminFormAssertions(this);
	}
}
