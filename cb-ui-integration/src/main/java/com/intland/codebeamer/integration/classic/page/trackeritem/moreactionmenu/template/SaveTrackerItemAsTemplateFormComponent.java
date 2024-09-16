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

package com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu.template;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class SaveTrackerItemAsTemplateFormComponent
		extends AbstractCodebeamerComponent<SaveTrackerItemAsTemplateFormComponent, SaveTrackerItemAsTemplateFormAssertions> {

	public SaveTrackerItemAsTemplateFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form#command");
	}

	public SaveTrackerItemAsTemplateFormComponent templateName(String name) {
		getTemplateNameField().fill(name);
		return this;
	}

	public SaveTrackerItemAsTemplateFormComponent setToPublic() {
		getPublicCheckbox().click();
		return this;
	}

	public SaveTrackerItemAsTemplateFormComponent overwriteTemplate(String name) {
		switchToOverwriteTab();
		getOverwriteSelector().selectOption(name);
		return this;
	}

	public void save() {
		getSaveButton().click();
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input[type='submit'][value='Save']");
	}

	public CodebeamerLocator getTemplateNameField() {
		return this.locator("input#newTemplateName");
	}

	public CodebeamerLocator getPublicCheckbox() {
		return this.locator("input#newTemplateIsPublic");
	}

	public CodebeamerLocator getOverwriteSelector() {
		return this.locator("select#templateToOverwrite");
	}

	public void switchToOverwriteTab() {
		this.locator("div#overwriteItemTemplate-tab").click();
	}

	@Override
	public SaveTrackerItemAsTemplateFormAssertions assertThat() {
		return new SaveTrackerItemAsTemplateFormAssertions(this);
	}

}
