package com.intland.codebeamer.integration.classic.component.globalmessage;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class GlobalMessagesComponent
		extends AbstractCodebeamerComponent<GlobalMessagesComponent, GlobalMessagesAssertions> {

	public GlobalMessagesComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div#globalMessages");
	}

	public GlobalMessagesComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div#globalMessages");
	}

	public CodebeamerLocator getErrorMessagesElement() {
		return this.locator("div.error");
	}

	public CodebeamerLocator getInfoMessagesElement() {
		return this.locator("div.information");
	}

	public CodebeamerLocator getWarningMessagesElement() {
		return this.locator("div.warning");
	}

	@Override
	public GlobalMessagesAssertions assertThat() {
		return new GlobalMessagesAssertions(this);
	}

}
