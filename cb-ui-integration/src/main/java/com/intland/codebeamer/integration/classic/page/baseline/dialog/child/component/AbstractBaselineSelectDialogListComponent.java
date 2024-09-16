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

package com.intland.codebeamer.integration.classic.page.baseline.dialog.child.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractBaselineSelectDialogListComponent extends
		AbstractCodebeamerComponent<AbstractBaselineSelectDialogListComponent, AbstractBaselineSelectDialogListAssertion> {

	public AbstractBaselineSelectDialogListComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, ".baselineList");
	}

	public AbstractBaselineSelectDialogListComponent select(BaselineId baselineId) {
		getBaselineElement(baselineId).click();
		return this;
	}

	public CodebeamerLocator getBaselineElement(BaselineId baselineId) {
		return this.locator(String.format(".baselineName[data-baseline-id='%s']", baselineId.id()));
	}

	@Override
	public abstract AbstractBaselineSelectDialogListAssertion assertThat();
}