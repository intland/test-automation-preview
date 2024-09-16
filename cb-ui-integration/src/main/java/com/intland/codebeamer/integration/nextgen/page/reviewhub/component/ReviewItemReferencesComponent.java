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
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewItemReferencesComponent extends
		AbstractCodebeamerComponent<ReviewItemReferencesComponent, ReviewItemReferencesAssertions> {

	public ReviewItemReferencesComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div");
	}

	@Action("Get review item references")
	public ReviewHubNavigation reviewItemReferences(String itemId) {
		getReferencesForReviewItemElement(itemId).click();
		return new ReviewHubNavigation(getCodebeamerPage());
	}

	public CodebeamerLocator getReferencesForReviewItemElement(String itemId) {
		return getCodebeamerPage().locator("div[id='%s'] button".formatted(itemId));
	}

	public CodebeamerLocator getNewVersionAvailableBadgeForReferencesButton() {
		return this.locator("button.relation-status-badge.relation-new-version");
	}

	@Override
	public ReviewItemReferencesAssertions assertThat() {
		return new ReviewItemReferencesAssertions(this);
	}
}
