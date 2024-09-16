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

package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions.IsVisibleOptions;

public class ProjectBrowserCategoryListAssertion extends AbstractCodebeamerComponentAssert<ProjectBrowserCategoryListComponent, ProjectBrowserCategoryListAssertion> {

	ProjectBrowserCategoryListAssertion(ProjectBrowserCategoryListComponent component) {
		super(component);
	}

	public ProjectBrowserCategoryListAssertion hasCategoryByName(String categoryName) {
		return assertAll("Category should be found by '%s' name".formatted(categoryName), 
				() -> assertThat(getComponent().getCategoryByName(categoryName)).isVisible(createIsVisibleOptions()));
	}


	public ProjectBrowserCategoryListAssertion hasNoCategoryByName(String categoryName) {
		return assertAll("Category should not be found by '%s' name".formatted(categoryName), 
				() -> assertThat(getComponent().getCategoryByName(categoryName)).not().isVisible(createIsVisibleOptions()));
	}

	private IsVisibleOptions createIsVisibleOptions() {
		return new IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
	
}
