package com.intland.codebeamer.integration.classic.component.field.edit.common.dialog;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class EditCountryDialogComponent extends
		AbstractCodebeamerComponent<EditCountryDialogComponent, EditCountryDialogAssertions> {

	public EditCountryDialogComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.token-input-dropdown-facebook");
	}

	public EditCountryDialogComponent select(Country country) {
		this.locator("li:text-matches('%s .*')".formatted(country.getName())).click();
		return this;
	}
	
	public CodebeamerLocator getValueField() {
		return this.locator(" + td.fieldValue ul li input[type=text]");
	}
	
	@Override
	public EditCountryDialogAssertions assertThat() {
		return new EditCountryDialogAssertions(this);
	}

}
