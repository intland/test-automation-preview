package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import java.util.Arrays;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditCountryFieldSelectorComponent;
import com.microsoft.playwright.Locator;

public class InlineEditCountryFieldSelectorComponent extends
		AbstractEditCountryFieldSelectorComponent<InlineEditCountryFieldSelectorComponent, InlineEditCountryFieldSelectorAssertions> {

	public InlineEditCountryFieldSelectorComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditCountryFieldSelectorComponent selectOption(Country... country) {
		waitForReferenceInlineEditStart();

		Arrays.stream(country).forEach(this::selectCountry);

		stopInlineEdit();
		return this;
	}

	public InlineEditCountryFieldSelectorComponent removeOption(Country... country) {
		waitForReferenceInlineEditStart();

		Arrays.stream(country).forEach(this::removeCountry);

		stopInlineEdit();
		return this;
	}

	public InlineEditCountryFieldSelectorComponent removeAllOptions() {
		waitForReferenceInlineEditStart();

		getSelectedCountries().forEach(this::removeCountry);

		stopInlineEdit();
		return this;
	}

	@Override
	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" ul.token-input-list-facebook");
	}

	@Override
	public CodebeamerLocator getValueField() {
		return this.locator(" ul li input[type=text]");
	}

	@Override
	public InlineEditCountryFieldSelectorAssertions assertThat() {
		return new InlineEditCountryFieldSelectorAssertions(this);
	}

	@Override
	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2).setDelay(500));
		waitForReferenceInlineEditEnd();
	}

	private void selectCountry(Country country) {
		getValueField().type(country.getName());

		this.countryDialogComponent.select(country);

		getElement(country).waitForAttached();
	}

	private void removeCountry(Country country) {
		CodebeamerLocator element = getElement(country);
		element.waitForAttached();

		getRemoveElement(country).click();

		element.waitForDetached();
	}

}
