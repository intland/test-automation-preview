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

public abstract class AbstractTreeComponent<C extends AbstractTreeComponent<C, A>, A extends AbstractTreeAssertions<C, A>>
		extends AbstractCodebeamerComponent<C, A> {

	private final String treeLocator;

	protected AbstractTreeComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator,
			String treeLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.treeLocator = treeLocator;
	}

	protected AbstractTreeComponent(CodebeamerPage codebeamerPage, String componentLocator, String treeLocator) {
		this(codebeamerPage, null, componentLocator, treeLocator);
	}

	protected AbstractTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#west");
		this.treeLocator = "div#treePane";
	}

	public CodebeamerLocator getTreeItemByName(String treeItem) {
		return getTreeContainerElement().concat("a:has-text('%s')".formatted(escapeCharFromItemName(treeItem)));
	}

	public CodebeamerLocator getNameOfTreeItems() {
		return getTreeContainerElement().concat("li a[role='treeitem']");
	}

	public CodebeamerLocator selectTreeItemByName(String treeItem) {
		return getTreeItemByName(treeItem).click();
	}

	public CodebeamerLocator toggleTreeFolderByName(String treeItem) {
		return getTreeItemFolderToggleButton(treeItem).click();
	}

	public CodebeamerLocator selectChildTreeItemByName(String parentTreeItem, String childTreeItem) {
		getTreeItemFolderToggleButton(parentTreeItem).click();
		return getTreeItemFolderToggleButton(childTreeItem).click();
	}

	public CodebeamerLocator openContextMenuForTreeItemByName(String treeItem) {
		return getTreeItemByName(treeItem).rightClick();
	}

	public C dragTreeItemOntoTarget(String sourceTreeItem, String targetTreeItem) {
		getTreeItemByName(sourceTreeItem).drag(getTreeItemByName(targetTreeItem));
		return (C) this;
	}

	public C dragTreeItemBeforeTarget(String sourceTreeItem, String targetTreeItem) {
		getTreeItemByName(sourceTreeItem).dragBefore(getTreeItemByName(targetTreeItem));
		return (C) this;
	}

	public C dragTreeItemAfterTarget(String sourceTreeItem, String targetTreeItem) {
		getTreeItemByName(sourceTreeItem).dragAfter(getTreeItemByName(targetTreeItem));
		return (C) this;
	}

	public CodebeamerLocator selectMultipleTreeItemsByName(String... treeItems) {
		getCodebeamerPage().holdControlKey();
		for (int i = 0; i < treeItems.length - 1; i++) {
			selectTreeItemByName(treeItems[i]);
		}
		CodebeamerLocator locator = selectTreeItemByName(treeItems[treeItems.length - 1]);
		getCodebeamerPage().releaseControlKey();
		return locator;
	}

	@Override
	public abstract A assertThat();

	private CodebeamerLocator getTreeContainerElement() {
		return this.locator(treeLocator);
	}

	private CodebeamerLocator getTreeItemFolderToggleButton(String treeItem) {
		return getTreeContainerElement().concat("li:has(> a:has-text('%s')) > i"
				.formatted(escapeCharFromItemName(escapeCharFromItemName(treeItem))));
	}

	private String escapeCharFromItemName(String itemName) {
		return itemName.replace("'", "\\'");
	}
}
