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

package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplateId;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.edititemtemplates.EditItemTemplatesDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ManageItemTemplatesFormComponent
		extends AbstractCodebeamerComponent<ManageItemTemplatesFormComponent, ManageItemTemplatesFormAssertions> {

	private final EditItemTemplatesDialog editItemTemplatesDialog;

	public ManageItemTemplatesFormComponent(CodebeamerPage codebeamerPage, String frameLocator, Tracker tracker) {
		super(codebeamerPage, frameLocator, "div#itemTemplatesContainer");
		this.editItemTemplatesDialog = new EditItemTemplatesDialog(codebeamerPage, tracker);
	}

	public EditItemTemplatesDialog edit(TrackerItemTemplateId trackerItemTemplateId) {
		getEditButtonForTemplate(trackerItemTemplateId).click();
		return editItemTemplatesDialog;
	}

	public EditItemTemplatesDialog edit(String trackerItemTemplateName) {
		getEditButtonForTemplate(trackerItemTemplateName).click();
		return editItemTemplatesDialog;
	}

	public void close() {
		getCloseButton().click();
	}

	public CodebeamerLocator getEditButtonForTemplate(TrackerItemTemplateId trackerItemTemplateId) {
		return this.locator(String.format("tr:has(td.itemTemplateId:has-text('%s')) span.editItemTemplate",
				Integer.valueOf(trackerItemTemplateId.id())));
	}

	public CodebeamerLocator getCloseButton() {
		return getCodebeamerPage().locator("div#inlinedPopup0 a.container-close");
	}

	public CodebeamerLocator getEditButtonForTemplate(String trackerItemTemplateName) {
		return this.locator(String.format("tr:has(td.nameColumn:has-text('%s')) span.editItemTemplate",
				trackerItemTemplateName));
	}

	@Override
	public ManageItemTemplatesFormAssertions assertThat() {
		return new ManageItemTemplatesFormAssertions(this);
	}

}
