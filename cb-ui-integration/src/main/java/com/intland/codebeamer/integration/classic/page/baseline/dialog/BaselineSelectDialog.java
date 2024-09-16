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

package com.intland.codebeamer.integration.classic.page.baseline.dialog;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.child.AbstractBaselineSelectDialog;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.component.BaselineSelectDialogListAssertion;
import com.intland.codebeamer.integration.classic.page.baseline.dialog.component.BaselineSelectDialogListComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class BaselineSelectDialog
		extends AbstractBaselineSelectDialog<BaselineSelectDialogListComponent, BaselineSelectDialogListAssertion> {

	@Component("Baseline list")
	private final BaselineSelectDialogListComponent baselineSelectDialogListComponent;

	public BaselineSelectDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='baselines.spr']");
		this.baselineSelectDialogListComponent = new BaselineSelectDialogListComponent(codebeamerPage, this.getDialogLocator());
	}

	@Override
	public BaselineSelectDialog baselineSelectDialogListComponent(
			Consumer<BaselineSelectDialogListComponent> formConsumer) {
		formConsumer.accept(this.baselineSelectDialogListComponent);
		return this;
	}

	@Override
	public BaselineSelectDialog assertBaselineSelectDialogListComponent(
			Consumer<BaselineSelectDialogListAssertion> assertion) {
		assertion.accept(this.baselineSelectDialogListComponent.assertThat());
		return this;
	}

	@Override
	public BaselineSelectDialogNavigation select(BaselineId baselineId) {
		this.baselineSelectDialogListComponent.select(baselineId);
		this.baselineSelectDialogListComponent.getBaselineElement(baselineId).waitForDetached();
		return new BaselineSelectDialogNavigation(getCodebeamerPage());
	}
}
