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

package com.intland.codebeamer.integration.classic.component.field.edit;

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.component.FroalaComponent.Type;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditWikiTextFieldComponent extends AbstractCodebeamerComponent<EditWikiTextFieldComponent, EditWikiTextFieldAssertions> {

	private FroalaComponent froala;

	public EditWikiTextFieldComponent(CodebeamerPage codebeamerPage, String fieldName) {
		super(codebeamerPage, "td.fieldLabel:has(span.labelText:text-is('%s:'))".formatted(fieldName));
		this.froala = new FroalaComponent(codebeamerPage, getSelector("+ .fieldValue"));
	}

	public EditWikiTextFieldComponent fill(String value) {
		this.froala.fill(value, Type.MARKUP);
		return this;
	}
	
	public EditWikiTextFieldComponent fill(String value, Type type) {
		this.froala.fill(value, type);
		return this;
	}
	
	public EditWikiTextFieldComponent setComment(String text) {
		this.froala.fill(text);
		return this;
	}

	public EditWikiTextFieldComponent addAttachment(Path attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		this.froala.pressEnter();
		
		return this;
	}
	
	protected FroalaComponent getFroala() {
		return froala;
	}

	@Override
	public EditWikiTextFieldAssertions assertThat() {
		return new EditWikiTextFieldAssertions(this);
	}
	
}
