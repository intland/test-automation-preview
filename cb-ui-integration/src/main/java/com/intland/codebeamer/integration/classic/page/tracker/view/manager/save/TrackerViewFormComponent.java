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

package com.intland.codebeamer.integration.classic.page.tracker.view.manager.save;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.multiselect.MultiSelectMenuComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerViewFormComponent
		extends AbstractCodebeamerComponent<TrackerViewFormComponent, TrackerViewFormAssertions> {

	public static final String IFRAME_LOCATOR = "iframe#inlinedPopupIframe[src*='proj/query/permission.spr?reportSelectorSaveMode=true']";

	public TrackerViewFormComponent(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: tracker view save dialog needs a proper identifier
		super(codebeamerPage, IFRAME_LOCATOR, "");
	}

	public TrackerViewFormComponent save() {
		getSaveButton().click();
		this.getLoadingDialogElement().waitForDetached();
		return this;
	}

	public TrackerViewFormComponent name(String name) {
		getNameInput().fill(name);
		return this;
	}

	public TrackerViewFormComponent description(String description) {
		getDescriptionInput().fill(description);
		return this;
	}

	public TrackerViewFormComponent addNewFolder(String folderName) {
		return addNewFolder(folderName, "");
	}

	public TrackerViewFormComponent addNewFolder(String folderName, String description) {
		getAddFolderElement().click();
		getNewFolderNameInput().fill(folderName);
		getNewFolderDescriptionInput().fill(description);
		getSaveNewFolderElement().click();
		return this;
	}

	public TrackerViewFormComponent publicView() {
		getPublicViewInput().click();
		return this;
	}

	public TrackerViewFormComponent saveTo(String folder) {
		getFolderSelector().click();
		new MultiSelectMenuComponent(getCodebeamerPage(), IFRAME_LOCATOR, "folderSelectMenu").select(folder);
		return this;
	}

	public TrackerViewFormComponent roles(String... roles) {
		getRoleSelectorElement().click();
		new MultiSelectMenuComponent(getCodebeamerPage(), IFRAME_LOCATOR, "roleSelectMenu").select(roles);
		getRoleSelectorElement().click();
		return this;
	}

	@Override
	public TrackerViewFormAssertions assertThat() {
		return new TrackerViewFormAssertions(this);
	}

	public CodebeamerLocator getRoleSelectorElement() {
		return this.locator("#roleSelector_ms");
	}

	public CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("#saveButton");
	}

	public CodebeamerLocator getNameInput() {
		return this.locator("#name");
	}

	public CodebeamerLocator getDescriptionInput() {
		return this.locator("#description");
	}

	public CodebeamerLocator getPublicViewInput() {
		return this.locator("#isPublic");
	}

	public CodebeamerLocator getFolderSelector() {
		return this.locator("#folderSelector_ms");
	}

	public CodebeamerLocator getSaveNewFolderElement() {
		return this.locator(".folderInputAdd");
	}

	public CodebeamerLocator getNewFolderDescriptionInput() {
		return this.locator("#newFolderDescription");
	}

	public CodebeamerLocator getNewFolderNameInput() {
		return this.locator("#newFolderName");
	}

	public CodebeamerLocator getAddFolderElement() {
		return this.locator(".addFolder");
	}
}
