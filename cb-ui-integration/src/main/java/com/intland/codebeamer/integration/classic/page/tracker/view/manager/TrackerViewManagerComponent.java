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

package com.intland.codebeamer.integration.classic.page.tracker.view.manager;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.tracker.view.manager.save.TrackerViewFormComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.manager.tree.TrackerViewTreeAssertions;
import com.intland.codebeamer.integration.classic.page.tracker.view.manager.tree.TrackerViewTreeComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerViewManagerComponent
		extends AbstractCodebeamerComponent<TrackerViewManagerComponent, TrackerViewManagerAssertions> {

	@Component("Tracker view tree")
	private final TrackerViewTreeComponent trackerViewTreeComponent;

	@Component("Tracker view form")
	private final TrackerViewFormComponent trackerViewFormComponent;

	public TrackerViewManagerComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div.reportPickerContainer");
		this.trackerViewTreeComponent = new TrackerViewTreeComponent(codebeamerPage, frameLocator);
		this.trackerViewFormComponent = new TrackerViewFormComponent(codebeamerPage);
	}

	public void treeComponent(Consumer<TrackerViewTreeComponent> treeConsumer) {
		treeConsumer.accept(trackerViewTreeComponent);
	}

	public void assertTreeComponent(Consumer<TrackerViewTreeAssertions> assertionsConsumer) {
		assertionsConsumer.accept(trackerViewTreeComponent.assertThat());
	}

	public void clearDefaultView() {
		this.locator("span.clearDefault").click();
	}

	public void setAsDefaultView() {
		this.locator("span.setAsDefault").click();
	}

	public void save() {
		getMenuElement().concat(".save");
		this.getLoadingDialogElement().waitForDetached();
	}

	public TrackerViewManagerComponent saveAs(Consumer<TrackerViewFormComponent> formConsumer) {
		getMenuElement().concat(".saveAs").click();
		this.getLoadingDialogElement().waitForDetached();
		formConsumer.accept(trackerViewFormComponent);
		return this;
	}

	public void revert() {
		getMenuElement().concat(".revert").click();
	}

	public void showChildren() {
		this.getShowChildrenElement().click();
	}

	public void showAncestorItems() {
		this.getShowAncestorItemsElement().click();
	}

	public void showDescendantItems() {
		this.getShowDescendantItemsElement().click();
	}

	public void showColumnFilters() {
		this.getShowColumnFiltersElement().click();
	}

	public void panelView() {
		this.getPanelViewElement().click();
	}

	public void detailedPanelView() {
		this.getDetailedPanelViewElement().click();
	}

	public void resizableColumnsPercentages() {
		this.getResizableColumnsInPercentagesElement().click();
	}

	public void resizableColumnsPixels() {
		this.getResizableColumnsInPixelsElement().click();
	}

	@Override
	public TrackerViewManagerAssertions assertThat() {
		return new TrackerViewManagerAssertions(this);
	}

	public TrackerViewManagerComponent assertViewManagerComponent(Consumer<TrackerViewManagerAssertions> assertionsConsumer) {
		assertionsConsumer.accept(this.assertThat());
		return this;
	}

	public CodebeamerLocator getActiveViewElement() {
		return this.locator(".reportName");
	}

	public CodebeamerLocator getMenuElement() {
		return this.locator(".reportPickerMenuHeader");
	}

	public CodebeamerLocator getShowChildrenElement() {
		return this.locator("#showAllChildren");
	}

	public CodebeamerLocator getShowAncestorItemsElement() {
		return this.locator("#showAncestorItems");
	}

	public CodebeamerLocator getShowDescendantItemsElement() {
		return this.locator("#showDescendantItems");
	}

	public CodebeamerLocator getShowColumnFiltersElement() {
		return this.locator("#showColumnFilters");
	}

	public CodebeamerLocator getPanelViewElement() {
		return this.locator("#panelViewCheckboxBottom");
	}

	public CodebeamerLocator getDetailedPanelViewElement() {
		return this.locator("#panelViewCheckboxRight");
	}

	public CodebeamerLocator getResizableColumnsInPercentagesElement() {
		return this.locator("#resizeableColumns");
	}

	public CodebeamerLocator getResizableColumnsInPixelsElement() {
		return this.locator("#resizeableColumnsInPixels");
	}

	public CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}

}
