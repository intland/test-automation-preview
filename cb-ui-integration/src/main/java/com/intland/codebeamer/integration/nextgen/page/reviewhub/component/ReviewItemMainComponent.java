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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewItemMainComponent extends
		AbstractCodebeamerComponent<ReviewItemMainComponent, ReviewItemMainAssertions> {

	@Component("Review item references accordion component")
	private ReviewItemReferencesComponent reviewItemReferencesComponent;

	public ReviewItemMainComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "p-panel[class='review-hub-panel']");
		this.reviewItemReferencesComponent = new ReviewItemReferencesComponent(getCodebeamerPage());
	}

	public ReviewItemReferencesComponent getReviewItemReferencesComponent() {
		return reviewItemReferencesComponent;
	}

	@Override
	public ReviewItemMainAssertions assertThat() {
		return new ReviewItemMainAssertions(this);
	}
}
