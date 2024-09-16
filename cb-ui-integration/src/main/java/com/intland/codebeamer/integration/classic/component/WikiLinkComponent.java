package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class WikiLinkComponent extends AbstractCodebeamerComponent<WikiLinkComponent, WikiLinkAssertions> {

	public WikiLinkComponent(CodebeamerPage codebeamerPage, String parentLocator) {
		super(codebeamerPage,  parentLocator + " .wikiLinkContainer");
	}

	@Override
	public WikiLinkAssertions assertThat() {
		return new WikiLinkAssertions(this);
	}

	protected CodebeamerLocator getLinkLocator() {
		return this.locator("a");
	}
}
