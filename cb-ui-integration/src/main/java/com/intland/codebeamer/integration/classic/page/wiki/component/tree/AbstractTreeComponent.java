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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractTreeComponent extends AbstractCodebeamerComponent<AbstractTreeComponent, AbstractTreeAssertions> {

	public AbstractTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#west");
	}

	public CodebeamerLocator getTreeItemByName(String treeItem) {
		return this.locator("div#treePane a:has-text('%s')".formatted(escapeCharFromItemName(treeItem)));
	}

	public CodebeamerLocator selectTreeItemByName(String treeItem) {
		return this.locator("div#treePane a:has-text('%s')".formatted(escapeCharFromItemName(treeItem))).click();
	}

	public CodebeamerLocator openTreeItemByName(String treeItem) {
		return this.locator("div#treePane li:has(> a:has-text('%s')) > i"
				.formatted(escapeCharFromItemName(escapeCharFromItemName(treeItem)))).click();
	}

	public CodebeamerLocator selectChildTreeItemByName(String parentTreeItem, String childTreeItem) {
		this.locator("div#treePane li:has(> a:has-text('%s')) > i".formatted(escapeCharFromItemName(parentTreeItem))).click();
		return this.locator("div#treePane li:has(> a:has-text('%s')) > i".formatted(escapeCharFromItemName(childTreeItem)))
				.click();
	}

	@Override
	public abstract AbstractTreeAssertions assertThat();

	private String escapeCharFromItemName(String itemName) {
		return itemName.replace("'", "\\'");
	}
}
