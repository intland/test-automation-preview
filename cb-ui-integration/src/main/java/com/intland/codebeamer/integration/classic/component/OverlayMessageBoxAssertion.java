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

package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions;

public class OverlayMessageBoxAssertion extends AbstractCodebeamerComponentAssert<OverlayMessageBoxComponent, OverlayMessageBoxAssertion> {

	OverlayMessageBoxAssertion(OverlayMessageBoxComponent component) {
		super(component);
	}
	
	public OverlayMessageBoxAssertion hasError() {
		return assertAll("Error message is visible", () -> {
			getComponent().sleep(2);
			assertThat(getComponent().getErrorMessageElement()).isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
			assertThat(getComponent().getSuccessMessageElement()).not().isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
		});
	}
	
	public OverlayMessageBoxAssertion hasSuccess() {
		return assertAll("Success message should be visible", () -> {
			getComponent().sleep(2);
			assertThat(getComponent().getErrorMessageElement()).not().isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
			assertThat(getComponent().getSuccessMessageElement()).isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
		});
	}

	public OverlayMessageBoxAssertion hasSuccess(String message) {
		return assertAll("Success message should be visible with text '%s'".formatted(message), () -> {
			getComponent().sleep(2);
			assertThat(getComponent().getErrorMessageElement()).not().isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
			assertThat(getComponent().getSuccessMessageElement()).isVisible(new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS));
			assertThat(getComponent().getSuccessMessageElement()).hasText(message);
		});
	}

	public OverlayMessageBoxAssertion hasTrackerItemInSuccessMessage() {
		return this.hasSuccess().assertAll("Tracker item link should be available in the success message", () -> {
			assertThat(getComponent().getTrackerItemLinkElementFromSuccessMessage()).isVisible();
		});
	}
}
