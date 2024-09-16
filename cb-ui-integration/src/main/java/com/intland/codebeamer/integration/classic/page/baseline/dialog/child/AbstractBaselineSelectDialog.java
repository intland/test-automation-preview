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

package com.intland.codebeamer.integration.classic.page.baseline.dialog.child;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.child.component.AbstractBaselineSelectDialogListAssertion;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.child.component.AbstractBaselineSelectDialogListComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public abstract class AbstractBaselineSelectDialog<C extends AbstractBaselineSelectDialogListComponent, A extends AbstractBaselineSelectDialogListAssertion>
		extends AbstractCodebeamerDialog {
	public AbstractBaselineSelectDialog(CodebeamerPage codebeamerPage, String dialogLocator) {
		super(codebeamerPage, dialogLocator);
	}

	protected abstract AbstractBaselineSelectDialog baselineSelectDialogListComponent(Consumer<C> formConsumer);

	protected abstract AbstractBaselineSelectDialog assertBaselineSelectDialogListComponent(Consumer<A> assertion);

	protected abstract AbstractBaselineSelectDialogNavigation select(BaselineId baselineId);

}
