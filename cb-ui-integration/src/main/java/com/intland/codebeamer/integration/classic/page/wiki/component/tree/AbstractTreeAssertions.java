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

package com.intland.codebeamer.integration.classic.page.wiki.component.tree;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class AbstractTreeAssertions extends AbstractCodebeamerComponentAssert<AbstractTreeComponent, AbstractTreeAssertions> {

	protected AbstractTreeAssertions(AbstractTreeComponent component) {
		super(component);
	}

	public AbstractTreeAssertions treeItemExistsByName(String treeItemName) {
		return assertAll("Tree item should be found by '%s' name".formatted(treeItemName),
				() -> assertThat(getComponent().getTreeItemByName(treeItemName)).isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
}
