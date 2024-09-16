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

package com.intland.codebeamer.integration.classic.page.baseline.dialog.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.child.component.AbstractBaselineSelectDialogListComponent;

public class BaselineSelectDialogListComponent extends
		AbstractBaselineSelectDialogListComponent {

	public BaselineSelectDialogListComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator);
	}

	@Override
	public BaselineSelectDialogListAssertion assertThat() {
		return new BaselineSelectDialogListAssertion(this);
	}
}