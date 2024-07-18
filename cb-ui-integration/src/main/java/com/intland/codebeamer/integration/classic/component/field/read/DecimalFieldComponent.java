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
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DecimalFieldComponent extends AbstractCodebeamerComponent<DecimalFieldComponent, DecimalFieldAssertions> {

	public DecimalFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td:text-is('%s:')".formatted(fieldName));
	}
	
	public DecimalFieldComponent edit() {
		return this;
	}

	public CodebeamerLocator getValueElement() {
		return this.locator(" + td.tableItem");
	}

	@Override
	public DecimalFieldAssertions assertThat() {
		return new DecimalFieldAssertions(this);
	}

}
