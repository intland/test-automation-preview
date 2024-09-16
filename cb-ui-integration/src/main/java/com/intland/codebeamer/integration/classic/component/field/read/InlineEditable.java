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

package com.intland.codebeamer.integration.classic.component.field.read;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.microsoft.playwright.Locator;

public interface InlineEditable<T extends AbstractCodebeamerComponent> {

	CodebeamerLocator getValueElement();

	default T edit() {
		getValueElement().doubleClick(new Locator.DblclickOptions().setDelay(200));
		return (T) this;
	}
}
