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

package com.intland.codebeamer.integration.classic.page.reviewhub.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class AllReviewListAssertions extends AbstractCodebeamerComponentAssert<AllReviewListComponent, AllReviewListAssertions> {

	protected AllReviewListAssertions(AllReviewListComponent component) {
		super(component);
	}

	public AllReviewListAssertions isHeaderReady() {
		return assertAll("All Review Header Visible", () -> {
			assertThat(getComponent().getAllReviewsHeaderElement()).isVisible();
			assertThat(getComponent().getAllReviewsHeaderIcon()).isVisible();
		});
	}

	public AllReviewListAssertions isButtonsReady() {
		return assertAll("All Review Page Buttons Loaded", () -> {
			assertThat(getComponent().getCreateReviewButton()).isVisible();
			assertThat(getComponent().getCreateReviewButton()).isEnabled();
			assertThat(getComponent().getClearButton()).isVisible();
			assertThat(getComponent().getClearButton()).isDisabled();
			assertThat(getComponent().getApplyButton()).isVisible();
			assertThat(getComponent().getApplyButton()).isDisabled();
		});
	}

	public AllReviewListAssertions validateOpenReviewTab() {
		return assertAll("Open Review Tab Visible", () -> {
			assertThat(getComponent().getOpenReviewTabPanel()).isVisible();
			assertThat(getComponent().getOpenReviewTabPanel()).isEnabled();
			assertThat(getComponent().getOpenReviewTabTitleElement()).isVisible();
		});
	}

	public AllReviewListAssertions validateCloseReviewTab() {
		return assertAll("Close Review Tab Visible", () -> {
			assertThat(getComponent().getCloseReviewTabPanel()).isVisible();
			assertThat(getComponent().getCloseReviewTabPanel()).isEnabled();
			assertThat(getComponent().getCloseReviewTabTitleElement()).isVisible();
		});
	}
}
