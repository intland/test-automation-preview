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

package com.intland.codebeamer.integration.classic.page.dashboard.child;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.component.AbstractDashboardNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.dashboard.child.component.AbstractDashboardNewChildDialogFormComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public abstract class AbstractDashboardNewChildDialog<C extends AbstractDashboardNewChildDialogFormComponent, A extends AbstractDashboardNewChildDialogFormAssertions>
		extends AbstractCodebeamerDialog {

	public AbstractDashboardNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='create.spr']");
	}

	protected abstract AbstractDashboardNewChildDialog dashboardNewChildDialogFormComponent(Consumer<C> formConsumer);

	protected abstract AbstractDashboardNewChildDialog assertDashboardNewChildDialogFormComponent(Consumer<A> assertion);

	protected abstract AbstractDashboardNewChildDialogNavigation save();

}
