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

package com.intland.codebeamer.integration.classic.page.projectadmin.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractProjectAdminPageAssertions<C extends AbstractProjectAdminPageTab<C, A>, A extends AbstractProjectAdminPageAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractProjectAdminPageAssertions(C component) {
		super(component);
	}

	public A isTabActive() {
		return assertAll("Active tab should be in focus", () -> assertThat(getComponent().getTab()).hasClass(Pattern.compile(".*ditch-focused.*")));
	}
}