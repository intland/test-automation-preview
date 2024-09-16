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

package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.edititemtemplates;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormComponent;

public class EditItemTemplatesFormComponent extends TrackerItemFieldEditFormComponent {

	public EditItemTemplatesFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator);
	}

	public EditItemTemplatesFormComponent templateName(String name) {
		getTemplateNameField().fill(name);
		return this;
	}

	public EditItemTemplatesFormComponent templateDescription(String description) {
		getTemplateDescriptionField().fill(description);
		return this;
	}

	public EditItemTemplatesFormComponent publicTemplate(boolean publicTemplate) {
		getPublicCheckbox().check(publicTemplate);
		return this;
	}

	public void save() {
		getSaveButton().click();
	}

	public CodebeamerLocator getTemplateNameField() {
		return this.locator("input#templateName");
	}

	public CodebeamerLocator getTemplateDescriptionField() {
		return this.locator("textarea#templateDescription");
	}

	public CodebeamerLocator getPublicCheckbox() {
		return this.locator("input#isPublicTemplate");
	}

	@Override
	public EditItemTemplatesFormAssertions assertThat() {
		return new EditItemTemplatesFormAssertions(this);
	}

}
