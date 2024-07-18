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

package com.intland.codebeamer.integration.classic.page.trackeritem;

import java.util.function.Consumer;

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
import com.intland.codebeamer.integration.classic.component.field.read.LanguageSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.RoleAndMemberSelectorAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.TextFieldAssertions;
import com.intland.codebeamer.integration.classic.component.field.read.WikiTextFieldAssertions;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class TrackerItemFieldFormAssertions extends AbstractCodebeamerComponentAssert<TrackerItemFieldFormComponent, TrackerItemFieldFormAssertions> {

	protected TrackerItemFieldFormAssertions(TrackerItemFieldFormComponent component) {
		super(component);
	}
	
	public TrackerItemFieldFormAssertions description(Consumer<DescriptionWikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(new DescriptionWikiTextFieldComponent(getComponent().getCodebeamerPage()).assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions choiceField(String fieldName, Consumer<ChoiceFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getChoiceFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions wikiText(String fieldName, Consumer<WikiTextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getWikiTextFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions textField(String fieldName, Consumer<TextFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getTextFieldComponent().assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions integerField(String fieldName, Consumer<IntegerFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getIntegerFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions decimalField(String fieldName, Consumer<DecimalFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDecimalFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions dateField(String fieldName, Consumer<DateFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDateFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions booleanField(String fieldName, Consumer<BooleanFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getBooleanFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions durationField(String fieldName, Consumer<DurationFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getDurationFieldComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions colorField(String fieldName, Consumer<ColorFieldAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getColorFieldComponent().assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions memberField(String fieldName, Consumer<RoleAndMemberSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getRoleAndMemberSelectorComponent().assertThat());
		return this;
	}

	public TrackerItemFieldFormAssertions countryField(String fieldName, Consumer<CountrySelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getCountrySelectorComponent().assertThat());
		return this;
	}
	
	public TrackerItemFieldFormAssertions languageField(String fieldName, Consumer<LanguageSelectorAssertions> assertionConsumer) {
		assertionConsumer.accept(getComponent().getFieldComponentFactory(fieldName).getLanguageSelectorComponent().assertThat());
		return this;
	}
	
}
