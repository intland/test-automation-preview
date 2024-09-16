package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.dialog.EditCountryDialogComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public abstract class AbstractEditCountryFieldSelectorComponent<C extends AbstractEditCountryFieldSelectorComponent<C, A>, A extends AbstractEditCountryFieldSelectorAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	@Component("Edit country dialog")
	protected EditCountryDialogComponent countryDialogComponent;

	public AbstractEditCountryFieldSelectorComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.countryDialogComponent = new EditCountryDialogComponent(codebeamerPage);
	}

	public List<Country> getSelectedCountries() {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li p.name").all().stream()
				.map(CodebeamerLocator::text)
				.map(Country::findByName)
				.toList();
	}

	public CodebeamerLocator getElement(Country country) {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li:has(p:text-is('%s'))".formatted(country.getName()));
	}

	public CodebeamerLocator getRemoveElement(Country country) {
		return getElement(country).concat("span.token-input-delete-token-facebook");
	}

	public abstract CodebeamerLocator getValueContainerElement();

	public abstract CodebeamerLocator getValueField();

}
