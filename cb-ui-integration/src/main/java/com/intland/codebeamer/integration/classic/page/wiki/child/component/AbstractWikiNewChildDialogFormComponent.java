package com.intland.codebeamer.integration.classic.page.wiki.child.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractWikiNewChildDialogFormComponent
		extends AbstractCodebeamerComponent<AbstractWikiNewChildDialogFormComponent, AbstractWikiNewChildDialogFormAssertions> {

	public AbstractWikiNewChildDialogFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form#wikiPageForm", true);
	}

	public AbstractWikiNewChildDialogFormComponent pageName(String name) {
		getPageNameField().fill(name);
		return this;
	}

	public AbstractWikiNewChildDialogFormComponent save() {
		getSaveButton().click();
		return this;
	}
	
	CodebeamerLocator getSaveButton() {
		return this.locator("input[type='submit'].saveButton");
	}

	public CodebeamerLocator getPageNameField() {
		return this.locator("input#name");
	}
	
	@Override
	public abstract AbstractWikiNewChildDialogFormAssertions assertThat();

}
