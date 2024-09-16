package com.intland.codebeamer.integration.classic.page.wiki.child.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractWikiNewChildDialogFormComponent
		extends AbstractCodebeamerComponent<AbstractWikiNewChildDialogFormComponent, AbstractWikiNewChildDialogFormAssertions> {

	@Component(value = "Wikitext editor", includeInSitemap = false)
	private final FroalaComponent froala;

	public AbstractWikiNewChildDialogFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "form#wikiPageForm");
		this.froala = new FroalaComponent(codebeamerPage, frameLocator, getSelector());
	}

	public AbstractWikiNewChildDialogFormComponent pageName(String name) {
		getPageNameField().fill(name);
		return this;
	}

	public AbstractWikiNewChildDialogFormComponent withWikiEditor(Consumer<FroalaComponent> wikiEditorConsumer) {
		wikiEditorConsumer.accept(froala);
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
