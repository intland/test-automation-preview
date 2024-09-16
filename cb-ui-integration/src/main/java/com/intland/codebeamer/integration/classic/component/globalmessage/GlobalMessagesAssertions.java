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

package com.intland.codebeamer.integration.classic.component.globalmessage;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class GlobalMessagesAssertions
		extends AbstractCodebeamerComponentAssert<GlobalMessagesComponent, GlobalMessagesAssertions> {

	GlobalMessagesAssertions(GlobalMessagesComponent component) {
		super(component);
	}

	public GlobalMessagesAssertions hasError() {
		return assertAll("Error message should be visible",
				() -> assertThat(getComponent().getErrorMessagesElement()).isVisible());
	}

	public GlobalMessagesAssertions hasErrorWithMessage(String message) {
		return assertAll("Error message '%s' should be present'".formatted(message),
				() -> assertThat(getComponent().getErrorMessagesElement()).hasText(Pattern.compile(message)));
	}

	public GlobalMessagesAssertions hasInfo() {
		return assertAll("Info message should be visible", () -> assertThat(getComponent().getInfoMessagesElement()).isVisible());
	}

	public GlobalMessagesAssertions hasWarning() {
		return assertAll("Warning message should be visible",
				() -> assertThat(getComponent().getWarningMessagesElement()).isVisible());
	}
}
