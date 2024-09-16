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

package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.Arrays;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.EditFieldComponentFactory;
import com.intland.codebeamer.integration.classic.component.field.edit.common.modal.EditReferenceModalComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCountryFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditLanguageFieldSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditReferenceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditWikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.globalmessage.GlobalMessagesComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemFieldEditFormComponent extends AbstractCodebeamerComponent<TrackerItemFieldEditFormComponent, TrackerItemFieldEditFormAssertions> {

	private static final String FORM_SELECTOR = "#addUpdateTaskForm";

	private FieldLocators fieldLocators;

	private String frameLocator;

	private EditFieldComponentFactory editFieldComponentFactory;

	public TrackerItemFieldEditFormComponent(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, null);
	}

	public TrackerItemFieldEditFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		this(codebeamerPage, frameLocator, new FieldLocators(
				null,
				"td.fieldLabel:has(span.labelText:text-is('%s:')) + td.fieldValue"::formatted,
				null
		));
	}

	public TrackerItemFieldEditFormComponent(CodebeamerPage codebeamerPage, String frameLocator, FieldLocators fieldLocators) {
		super(codebeamerPage, frameLocator, FORM_SELECTOR);
		this.frameLocator = frameLocator;
		this.fieldLocators = fieldLocators;
		this.editFieldComponentFactory = new EditFieldComponentFactory(codebeamerPage, frameLocator);
	}


	@Override
	public TrackerItemFieldEditFormAssertions assertThat() {
		return new TrackerItemFieldEditFormAssertions(this);
	}

	public CodebeamerLocator getSaveButton() {
		return this.locator("input[type='submit'][name='SUBMIT']");
	}
	
	public CodebeamerLocator getSaveAndNewButton() {
		return this.locator("input[type='submit'][name='SUBMIT_AND_NEW']");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("input[type='submit'][name='_cancel']");
	}

	public TrackerItemFieldEditFormComponent setTextField(String fieldName, String value) {
		return field(f -> f.getTextFieldComponent(getFieldSelector(fieldName)).fill(value));
	}
	
	public TrackerItemFieldEditFormComponent setWikiText(String fieldName, String value) {
		return field(f -> f.getWikiTextFieldComponent(getFieldSelector(fieldName)).fill(value));
	}
	
	public TrackerItemFieldEditFormComponent setWikiText(String fieldName, Consumer<EditWikiTextFieldComponent> wikiTextFieldComponentConsumer) {
		return field(f -> wikiTextFieldComponentConsumer.accept(f.getWikiTextFieldComponent(getFieldSelector(fieldName))));
	}
	
	public TrackerItemFieldEditFormComponent setChoiceField(String fieldName, String value) {
		return field(f -> f.getChoiceFieldComponent(getFieldSelector(fieldName)).selectOption(value));
	}
	
	public TrackerItemFieldEditFormComponent setDurationField(String fieldName, String value) {
		return field(f -> f.getDurationFieldComponent(getFieldSelector(fieldName)).fill(value));
	}
	
	public TrackerItemFieldEditFormComponent setColorField(String fieldName, HtmlColor htmlColor) {
		return field(f -> f.getColorFieldComponent(getFieldSelector(fieldName)).fill(htmlColor));
	}
	
	public TrackerItemFieldEditFormComponent setIntegerField(String fieldName, int value) {
		return field(f -> f.getIntegerFieldComponent(getFieldSelector(fieldName)).fill(value));
	}
	
	public TrackerItemFieldEditFormComponent setDecimalField(String fieldName, double value) {
		return field(f -> f.getDecimalFieldComponent(getFieldSelector(fieldName)).fill(value));
	}
	
	public TrackerItemFieldEditFormComponent setDateField(String fieldName, int year, int month, int day, int hour, int minute) {
		return field(f -> f.getDateFieldComponent(getFieldSelector(fieldName)).fill(year, month, day, hour, minute));
	}
	
	public TrackerItemFieldEditFormComponent setBooleanField(String fieldName, boolean value) {
		return field(f -> f.getBooleanFieldComponent(getFieldSelector(fieldName)).selectOption(value));
	}
	
	public TrackerItemFieldEditFormComponent clearCountryField(String fieldName) {
		return field(f -> f.getCountryFieldSelectorComponent(getFieldSelector(fieldName)).removeAllOptions());
	}
	
	public TrackerItemFieldEditFormComponent removeCountryField(String fieldName, Country... county) {
		return field(f -> {
			EditCountryFieldSelectorComponent component = f.getCountryFieldSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(county).forEach(component::removeOption);
		});
	}
	
	public TrackerItemFieldEditFormComponent addCountryField(String fieldName, Country... county) {
		return field(f -> {
			EditCountryFieldSelectorComponent component = f.getCountryFieldSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(county).forEach(component::selectOption);
		});
	}
	
	public TrackerItemFieldEditFormComponent clearLanguageField(String fieldName) {
		return field(f -> f.getLanguageFieldSelectorComponent(getFieldSelector(fieldName)).removeAllOptions());
	}
	
	public TrackerItemFieldEditFormComponent addLanguageField(String fieldName, Language... language) {
		return field(f -> {
			EditLanguageFieldSelectorComponent component = f.getLanguageFieldSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(language).forEach(component::selectOption);
		});
	}

	public TrackerItemFieldEditFormComponent removeLanguageField(String fieldName, Language... language) {
		return field(f -> {
			EditLanguageFieldSelectorComponent component = f.getLanguageFieldSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(language).forEach(component::removeOption);
		});
	}
	
	public TrackerItemFieldEditFormComponent clearMemberField(String fieldName) {
		return field(f -> f.getRoleAndMemberSelectorComponent(getFieldSelector(fieldName)).removeAll());
	}
	
	public TrackerItemFieldEditFormComponent removeMemberFrom(String fieldName, MemberReference... memberReference) {
		return field(f -> {
			EditRoleAndMemberSelectorComponent component = f.getRoleAndMemberSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(memberReference).forEach(component::removeMember);
		});
	}
	
	public TrackerItemFieldEditFormComponent addMemberTo(String fieldName, MemberReference... memberReference) {
		return field(f -> {
			EditRoleAndMemberSelectorComponent component = f.getRoleAndMemberSelectorComponent(getFieldSelector(fieldName));
			Arrays.stream(memberReference).forEach(component::selectMember);
		});
	}

	public TrackerItemFieldEditFormComponent setUrlField(String fieldName, String url, String alias) {
		return field(form -> form.getUrlEditComponent(getFieldSelector(fieldName)).editUrl()
				.artifactLinksComponent(f -> f
						.switchToWikiLinkUrlTab()
						.setUrl(url, alias))
				.save()
		);
	}

	public TrackerItemFieldEditFormComponent setWikiLinkField(String fieldName, TrackerItemId wikiLinkUrl, String alias) {
		return field(form -> form.getUrlEditComponent(getFieldSelector(fieldName)).editUrl()
				.artifactLinksComponent(f -> f
						.switchToWikiLinkUrlTab()
						.setWikiLink(wikiLinkUrl, alias))
				.save()
		);
	}
	
	public TrackerItemFieldEditFormComponent clearUrlField(String fieldName) {
		return field(f -> f.getUrlEditComponent(getFieldSelector(fieldName)).remove());
	}
	
	public TrackerItemFieldEditFormComponent removeUrlFrom(String fieldName, String url) {
		return field(f -> f.getUrlEditComponent(getFieldSelector(fieldName)).remove(url));
	}
	
	public TrackerItemFieldEditFormComponent clearReferenceField(String fieldName) {
		return field(f -> f.getReferenceEditComponent(getFieldSelector(fieldName)).removeAll());
	}
	
	public TrackerItemFieldEditFormComponent removeReferenceFrom(String fieldName, String... references) {
		return field(f -> {
			EditReferenceFieldComponent component = f.getReferenceEditComponent(getFieldSelector(fieldName));
			Arrays.stream(references).forEach(component::remove);
		});
	}
	
	public TrackerItemFieldEditFormComponent addReferenceTo(String fieldName, String... references) {
		return field(f -> {
			EditReferenceFieldComponent component = f.getReferenceEditComponent(getFieldSelector(fieldName));
			Arrays.stream(references).forEach(component::select);
		});
	}

	public TrackerItemFieldEditFormComponent editReferenceFieldByModal(String fieldName,
			Consumer<EditReferenceModalComponent> edit) {
		return field(f -> f.getReferenceEditComponent(getFieldSelector(fieldName)).referenceModal(edit));
	}

	public EditReferenceModalComponent editReferenceFieldByModal(String fieldName) {
		return getFieldComponentFactory().getReferenceEditComponent(getFieldSelector(fieldName))
				.openReferenceModal();
	}

	public TrackerItemFieldEditFormComponent addReferenceToPosition(String fieldName, String reference, int position) {
		return field(f -> f.getReferenceEditComponent(getFieldSelector(fieldName)).select(reference, position));
	}
	
	public TrackerItemFieldEditFormComponent field(Consumer<EditFieldComponentFactory> factory) {
		factory.accept(getFieldComponentFactory());
		return this;
	}

	public GlobalMessagesComponent getGlobalMessagesComponent() {
		return new GlobalMessagesComponent(getCodebeamerPage(), frameLocator);
	}

	protected String getFieldSelector(String fieldName) {
		return fieldLocators.editFieldLocator().apply(fieldName);
	}

	protected EditFieldComponentFactory getFieldComponentFactory() {
		return this.editFieldComponentFactory;
	}

}
