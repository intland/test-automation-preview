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

package com.intland.codebeamer.integration.classic.component.field.edit;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class AbstractEditFieldComponentAssertions<C extends AbstractEditFieldComponent<C, A>, A extends AbstractEditFieldComponentAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractEditFieldComponentAssertions(C component) {
		super(component);
	}
	
	public A hasTitle(String title) {
		return assertAll("Field should have title %s".formatted(title),
				() -> assertThat(getComponent().getTitleElement()).hasAttribute("title", title));
	}

	public A hasNoTitle() {
		return assertAll("Field should have no title %s",
				() -> {
					assertThat(getComponent().getTitleElement()).isAttached();
					assertTrue(StringUtils.isBlank(getComponent().getTitleElement().getAttribute("title")));
				});
	}
	
}
