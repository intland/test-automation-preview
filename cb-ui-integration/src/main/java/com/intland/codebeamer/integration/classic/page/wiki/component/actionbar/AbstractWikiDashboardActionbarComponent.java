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

package com.intland.codebeamer.integration.classic.page.wiki.component.actionbar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.dashboard.child.AbstractDashboardNewChildDialog;
import com.intland.codebeamer.integration.classic.page.wiki.child.AbstractWikiNewChildDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractWikiDashboardActionbarComponent
		extends AbstractCodebeamerComponent<AbstractWikiDashboardActionbarComponent, AbstractWikiDashboardActionbarAssertion> {

	public AbstractWikiDashboardActionbarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#centerDiv");
	}

	public CodebeamerLocator getWikiPageCreateButton() {
		return this.locator("[class*='dashboard-actionbar'] [src*='icon_new_childpage']");
	}

	public CodebeamerLocator getDashboardCreateButton() {
		return this.locator("[class*='dashboard-actionbar'] [src*='icon_new_dashboard']");
	}

	public abstract AbstractWikiNewChildDialog createNewWiki();

	public abstract AbstractDashboardNewChildDialog createNewDashBoard();

	@Override
	public abstract AbstractWikiDashboardActionbarAssertion assertThat();

}
