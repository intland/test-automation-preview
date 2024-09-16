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

package com.intland.codebeamer.integration.classic.component.field.edit.form;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditUrlFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.helper.UrlFieldValueConverter;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.artifactlinksdialog.ArtifactLinksDialog;

public class EditUrlFieldComponent extends AbstractEditUrlFieldComponent<EditUrlFieldComponent, EditUrlFieldAssertions> {

	public EditUrlFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public ArtifactLinksDialog editUrl() {
		getPopupButton().click();
		return new ArtifactLinksDialog(getCodebeamerPage());
	}

	public EditUrlFieldComponent remove() {
		getUrl()
				.map(UrlFieldValueConverter.UrlFieldValue::alias)
				.ifPresent(c -> getElement(c).click());
		return this;
	}

	public EditUrlFieldComponent remove(String referenceName) {
		getElement(referenceName).click();
		return this;
	}

	@Override
	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}

	@Override
	public EditUrlFieldAssertions assertThat() {
		return new EditUrlFieldAssertions(this);
	}

}