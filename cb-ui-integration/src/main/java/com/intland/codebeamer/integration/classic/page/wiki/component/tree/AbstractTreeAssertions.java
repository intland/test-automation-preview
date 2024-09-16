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

import static org.testng.Assert.assertEquals;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;
import com.microsoft.playwright.assertions.LocatorAssertions;

public class AbstractTreeAssertions<C extends AbstractTreeComponent<C, A>, A extends AbstractTreeAssertions<C, A>>
		extends AbstractCodebeamerComponentAssert<C, A> {

	protected AbstractTreeAssertions(C component) {
		super(component);
	}

	public A treeItemExistsByName(String treeItemName) {
		return assertAll("Tree item should be found by '%s' name".formatted(treeItemName),
				() -> assertThat(getComponent().getTreeItemByName(treeItemName)).isVisible(createIsVisibleOptions()));
	}

	public A treeHasNumberOfItems(int numberOfItems) {
		return assertAll("Number of tree items should be %d".formatted(numberOfItems),
				() -> assertEquals(getComponent().getNameOfTreeItems().count(), numberOfItems));
	}

	public A treeItemsExistInSpecificOrder(String... flattenedTreeItems) {
		return assertAll("Flattened list of tree items should match",
				() -> assertThat(getComponent().getNameOfTreeItems()).hasText(flattenedTreeItems));
	}

	protected LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
}
