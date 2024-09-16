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

package com.intland.codebeamer.integration.classic.component.trackerheaderfilter;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.filterselector.FilterSelectorComponent;
import com.intland.codebeamer.integration.classic.component.loadingIndicator.LoadingIndicatorComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerHeaderFilterComponent
		extends AbstractCodebeamerComponent<TrackerHeaderFilterComponent, TrackerHeaderFilterAssertions> {

	@Component("Filter selector")
	private FilterSelectorComponent filterSelectorComponent;

	public TrackerHeaderFilterComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "[class*='menu']");
		filterSelectorComponent = new FilterSelectorComponent(codebeamerPage);
	}

	public TrackerHeaderFilterAssertions assertThat() {
		return new TrackerHeaderFilterAssertions(this);
	}

	public void addColumnToTable(String addColumnName) {
		//UI-AUTOMATION: add data-test id for 'Add Column'
		selectAddColumnOption();
		filterSelectorComponent.selectField(addColumnName);
		new LoadingIndicatorComponent(getCodebeamerPage()).waitForDetached();
	}

	private void selectAddColumnOption() {
		this.locator(".context-menu-item:has-text('Add Column')").click();
	}

}
