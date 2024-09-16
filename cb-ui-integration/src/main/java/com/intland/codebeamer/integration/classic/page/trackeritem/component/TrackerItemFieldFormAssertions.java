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
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.classic.component.field.read.BooleanFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.ChoiceFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.ColorFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.CountrySelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.DateFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.DecimalFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.DescriptionWikiTextFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.DescriptionWikiTextFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.read.DurationFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.IntegerFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.LabelAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.LanguageSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.ReferenceFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.RoleAndMemberSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.TextFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.UrlFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.WikiTextFieldAssertions;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemFieldFormAssertions
		extends AbstractCodebeamerComponentAssert<TrackerItemFieldFormComponent, TrackerItemFieldFormAssertions> {

	private static final Pattern INLINE_EDIT_ENABLED_CLASS_PATTERN = Pattern.compile("inlineEditEnabled");

	protected TrackerItemFieldFormAssertions(TrackerItemFieldFormComponent component) {
		super(component);
	}

	public TrackerItemFieldFormAssertions description(Consumer<DescriptionWikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(new DescriptionWikiTextFieldComponent(getComponent().getCodebeamerPage()).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions choiceField(String fieldName, Consumer<ChoiceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getChoiceFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions wikiTextField(String fieldName, Consumer<WikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getWikiTextFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions textField(String fieldName, Consumer<TextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getTextFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions integerField(String fieldName, Consumer<IntegerFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getIntegerFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions decimalField(String fieldName, Consumer<DecimalFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getDecimalFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions dateField(String fieldName, Consumer<DateFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getDateFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions booleanField(String fieldName, Consumer<BooleanFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getBooleanFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions durationField(String fieldName, Consumer<DurationFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getDurationFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions colorField(String fieldName, Consumer<ColorFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getColorFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions urlField(String fieldName, Consumer<UrlFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getUrlFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions referenceField(String fieldName, Consumer<ReferenceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getReferenceFieldComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions memberField(String fieldName,
			Consumer<RoleAndMemberSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getRoleAndMemberSelectorComponent(getFieldSelector(fieldName))
						.assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions countryField(String fieldName, Consumer<CountrySelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getCountrySelectorComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions languageField(String fieldName,
			Consumer<LanguageSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(
				getComponent().getFieldComponentFactory().getLanguageSelectorComponent(getFieldSelector(fieldName)).assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions fieldLabel(String fieldName, Consumer<LabelAssertions> assertionsConsumer) {
		assertionsConsumer.accept(
				getComponent().getFieldComponentFactory().getLabelComponent(getComponent().getLabelSelector(fieldName))
						.assertThat());
		return this;
	}

	private String getFieldSelector(String fieldName) {
		return getComponent().getReadFieldSelector(fieldName);
	}

	public TrackerItemFieldFormAssertions isNotEditable() {
		return assertAll("Should be not editable", () ->
				assertThat(getComponent().getLocator()).not().hasClass(INLINE_EDIT_ENABLED_CLASS_PATTERN));
	}
}
