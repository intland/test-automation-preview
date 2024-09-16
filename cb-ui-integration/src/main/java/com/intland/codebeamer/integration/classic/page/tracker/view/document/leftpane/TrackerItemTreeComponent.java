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

package com.intland.codebeamer.integration.classic.page.tracker.view.document.leftpane;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.component.tree.AbstractTreeComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.KeyboardModifier;

public class TrackerItemTreeComponent extends AbstractTreeComponent<TrackerItemTreeComponent, TrackerItemTreeAssertions> {

	public TrackerItemTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	public TrackerItemTreeAssertions assertThat() {
		return new TrackerItemTreeAssertions(this);
	}

	public CodebeamerLocator selectNode(String itemName) {
		return this.getTreeItemByName(itemName).click();
	}

	public CodebeamerLocator selectNode(String itemName, double timeout) {
		return this.selectNode(itemName, new Locator.ClickOptions().setTimeout(timeout));
	}

	public CodebeamerLocator selectNode(String itemName, Locator.ClickOptions clickOptions) {
		return this.getTreeItemByName(itemName).click(clickOptions);
	}

	public void selectNodes(String from, String to) {
		selectNode(from);
		selectNode(to, new Locator.ClickOptions().setModifiers(List.of(KeyboardModifier.SHIFT)));
	}

	public void selectNodes(String from, String to, double timeout) {
		selectNode(from, timeout);
		selectNode(to, new Locator.ClickOptions()
				.setModifiers(List.of(KeyboardModifier.SHIFT))
				.setTimeout(timeout));
	}
}
