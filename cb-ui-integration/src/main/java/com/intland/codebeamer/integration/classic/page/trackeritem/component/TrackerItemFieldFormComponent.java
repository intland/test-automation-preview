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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.EditFieldComponentFactory;
import com.intland.codebeamer.integration.classic.component.field.edit.common.modal.EditReferenceModalComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditDescriptionFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.inline.InlineEditWikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.read.DescriptionWikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.read.FieldComponentFactory;
import com.intland.codebeamer.integration.classic.component.field.read.WikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemFieldFormComponent
		extends AbstractCodebeamerComponent<TrackerItemFieldFormComponent, TrackerItemFieldFormAssertions> {

	private static final String TRACKER_ITEM_PAGE_FORM_SELECTOR = ".propertyTable";

	private final FieldLocators fieldLocators;

	private final FieldComponentFactory fieldComponentFactory;

	private final EditFieldComponentFactory editFieldComponentFactory;

	public TrackerItemFieldFormComponent(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, null);
	}

	public TrackerItemFieldFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		this(codebeamerPage, frameLocator, TRACKER_ITEM_PAGE_FORM_SELECTOR, new FieldLocators(
				"td:text-is('%s:') + td.tableItem"::formatted,
				"td.fieldLabel:has(span.labelText:text-is('%s:')) + td.fieldValue"::formatted,
				"td:text-is('%s:') + td.columnEditingInProgress"::formatted));
	}

	public TrackerItemFieldFormComponent(CodebeamerPage codebeamerPage, String frameLocator,
			String componentLocator, FieldLocators fieldLocators) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.fieldLocators = fieldLocators;
		this.fieldComponentFactory = new FieldComponentFactory(codebeamerPage);
		this.editFieldComponentFactory = new EditFieldComponentFactory(codebeamerPage, frameLocator);
	}

	@Override
	public TrackerItemFieldFormAssertions assertThat() {
		return new TrackerItemFieldFormAssertions(this);
	}

	public TrackerItemFieldFormComponent assertThat(Consumer<TrackerItemFieldFormAssertions> assertions) {
		assertions.accept(assertThat());
		return this;
	}

	public WikiTextFieldComponent wikiField(String fieldName) {
		return getFieldComponentFactory().getWikiTextFieldComponent(getReadFieldSelector(fieldName));
	}

	public DescriptionWikiTextFieldComponent descriptionField() {
		return new DescriptionWikiTextFieldComponent(getCodebeamerPage());
	}

	@Override
	public CodebeamerLocator getLocator() {
		return selfLocator();
	}
	
	public TrackerItemFieldFormComponent editTextField(String fieldName, String value) {
		getFieldComponentFactory().getTextFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineTextFieldComponent(getInlineEditFieldSelector(fieldName)).fill(value));
	}

	public TrackerItemFieldFormComponent editWikiText(String fieldName, String value) {
		getFieldComponentFactory().getWikiTextFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineWikiTextFieldComponent(getInlineEditFieldSelector(fieldName))
				.fillAndSave(value));
	}

	public TrackerItemFieldFormComponent editWikiText(String fieldName,
			Consumer<InlineEditWikiTextFieldComponent> wikiTextFieldComponentConsumer) {
		getFieldComponentFactory().getWikiTextFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> wikiTextFieldComponentConsumer
				.accept(f.getInlineWikiTextFieldComponent(getInlineEditFieldSelector(fieldName))));
	}

	public TrackerItemFieldFormComponent editDescription(Consumer<InlineEditDescriptionFieldComponent> wikiTextFieldComponentConsumer) {
		new DescriptionWikiTextFieldComponent(getCodebeamerPage()).edit();

		InlineEditDescriptionFieldComponent descriptionFieldComponent = new InlineEditDescriptionFieldComponent(getCodebeamerPage(), getFrameLocator());
		wikiTextFieldComponentConsumer.accept(descriptionFieldComponent);
		return this;
	}

	public TrackerItemFieldFormComponent editChoiceField(String fieldName, String value) {
		getFieldComponentFactory().getChoiceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineChoiceFieldComponent(getInlineEditFieldSelector(fieldName))
				.selectOption(value));
	}

	public TrackerItemFieldFormComponent editDurationField(String fieldName, String value) {
		getFieldComponentFactory().getDurationFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineDurationFieldComponent(getInlineEditFieldSelector(fieldName))
				.fill(value));
	}

	public TrackerItemFieldFormComponent editColorField(String fieldName, HtmlColor htmlColor) {
		getFieldComponentFactory().getColorFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineColorFieldComponent(getInlineEditFieldSelector(fieldName))
				.fill(htmlColor));
	}

	public TrackerItemFieldFormComponent editIntegerField(String fieldName, int value) {
		getFieldComponentFactory().getIntegerFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineIntegerFieldComponent(getInlineEditFieldSelector(fieldName))
				.fill(value));
	}

	public TrackerItemFieldFormComponent editDecimalField(String fieldName, double value) {
		getFieldComponentFactory().getDecimalFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineDecimalFieldComponent(getInlineEditFieldSelector(fieldName))
				.fill(value));
	}

	public TrackerItemFieldFormComponent editDateField(String fieldName, int year, int month, int day, int hour, int minute) {
		getFieldComponentFactory().getDateFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineDateFieldComponent(getInlineEditFieldSelector(fieldName))
				.fill(year, month, day, hour, minute));
	}

	public TrackerItemFieldFormComponent editBooleanField(String fieldName, boolean value) {
		getFieldComponentFactory().getBooleanFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineBooleanFieldComponent(getInlineEditFieldSelector(fieldName))
				.selectOption(value));
	}

	public TrackerItemFieldFormComponent editUrlField(String fieldName, String url, String alias) {
		getFieldComponentFactory().getUrlFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(form -> form.getInlineUrlEditComponent(getInlineEditFieldSelector(fieldName))
				.editUrl(url, alias));
	}

	public TrackerItemFieldFormComponent editWikiLinkField(String fieldName, TrackerItemId wikiLinkUrl, String alias) {
		getFieldComponentFactory().getUrlFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(form -> form.getInlineUrlEditComponent(getInlineEditFieldSelector(fieldName))
				.editUrl(wikiLinkUrl, alias));
	}

	public TrackerItemFieldFormComponent clearUrlField(String fieldName) {
		getFieldComponentFactory().getUrlFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineUrlEditComponent(getInlineEditFieldSelector(fieldName)).remove());
	}

	public TrackerItemFieldFormComponent removeUrlFrom(String fieldName, String url) {
		getFieldComponentFactory().getUrlFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineUrlEditComponent(getInlineEditFieldSelector(fieldName))
				.remove(url));
	}

	public TrackerItemFieldFormComponent setCountryField(String fieldName, Country... country) {
		getFieldComponentFactory().getCountrySelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineCountryFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.selectOption(country));
	}

	public TrackerItemFieldFormComponent removeCountryField(String fieldName, Country... country) {
		getFieldComponentFactory().getCountrySelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineCountryFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeOption(country));
	}

	public TrackerItemFieldFormComponent clearCountryField(String fieldName) {
		getFieldComponentFactory().getCountrySelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineCountryFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeAllOptions());
	}

	public TrackerItemFieldFormComponent setLanguageField(String fieldName, Language... language) {
		getFieldComponentFactory().getLanguageSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineLanguageFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.selectOption(language));
	}

	public TrackerItemFieldFormComponent removeLanguageField(String fieldName, Language... language) {
		getFieldComponentFactory().getLanguageSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineLanguageFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeOption(language));
	}

	public TrackerItemFieldFormComponent clearLanguageField(String fieldName) {
		getFieldComponentFactory().getLanguageSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineLanguageFieldSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeAllOptions());
	}

	public TrackerItemFieldFormComponent clearMemberField(String fieldName) {
		getFieldComponentFactory().getRoleAndMemberSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineRoleAndMemberSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeAll());
	}

	public TrackerItemFieldFormComponent removeMemberFrom(String fieldName, MemberReference... memberReference) {
		getFieldComponentFactory().getRoleAndMemberSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineRoleAndMemberSelectorComponent(getInlineEditFieldSelector(fieldName))
				.removeMember(memberReference));
	}

	public TrackerItemFieldFormComponent addMemberTo(String fieldName, MemberReference... memberReference) {
		getFieldComponentFactory().getRoleAndMemberSelectorComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineRoleAndMemberSelectorComponent(getInlineEditFieldSelector(fieldName))
				.selectMember(memberReference));
	}

	public TrackerItemFieldFormComponent clearReferenceField(String fieldName) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.removeAll());
	}

	public TrackerItemFieldFormComponent removeReferenceFrom(String fieldName, String... references) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.remove(references));
	}

	public TrackerItemFieldFormComponent addReferenceTo(String fieldName, String... references) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.select(references));
	}

	public TrackerItemFieldFormComponent editReferenceFieldByModal(String fieldName,
			Consumer<EditReferenceModalComponent> edit) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		getEditFieldComponentFactory().getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.referenceModal(edit);

		return this;
	}

	public EditReferenceModalComponent editReferenceFieldByModal(String fieldName) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return getEditFieldComponentFactory().getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.openReferenceModal();
	}

	public TrackerItemFieldFormComponent addReferenceToPosition(String fieldName, String reference, int position) {
		getFieldComponentFactory().getReferenceFieldComponent(getReadFieldSelector(fieldName)).edit();

		return editField(f -> f.getInlineReferenceEditComponent(getInlineEditFieldSelector(fieldName))
				.select(reference, position));
	}

	protected TrackerItemFieldFormComponent editField(Consumer<EditFieldComponentFactory> factory) {
		factory.accept(getEditFieldComponentFactory());
		return this;
	}

	protected FieldComponentFactory getFieldComponentFactory() {
		return this.fieldComponentFactory;
	}

	protected EditFieldComponentFactory getEditFieldComponentFactory() {
		return this.editFieldComponentFactory;
	}

	protected String getReadFieldSelector(String fieldName) {
		return this.fieldLocators.readFieldLocator().apply(fieldName);
	}

	protected String getInlineEditFieldSelector(String fieldName) {
		return this.fieldLocators.inlineEditFieldLocator().apply(fieldName);
	}

	protected String getLabelSelector(String fieldName) {
		return "td:text-is('%s:')".formatted(fieldName);
	}
}
