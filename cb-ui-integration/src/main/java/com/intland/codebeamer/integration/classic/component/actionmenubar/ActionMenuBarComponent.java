package com.intland.codebeamer.integration.classic.component.actionmenubar;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

/**
 * Represents the black header under the main menus.
 */
public class ActionMenuBarComponent extends AbstractCodebeamerComponent<ActionMenuBarComponent, ActionMenuBarAssertions> {

	@Component("Working-set selector")
	private final WorkingSetSelectorComponent workingSetSelectorComponent;

	public ActionMenuBarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".actionMenuBar");
		this.workingSetSelectorComponent = new WorkingSetSelectorComponent(codebeamerPage);
	}

	@Override
	public ActionMenuBarAssertions assertThat() {
		return new ActionMenuBarAssertions(this);
	}

	public CodebeamerLocator getActionMenuBarElement(String element) {
		return this.locator(element);
	}

	public ActionMenuBarComponent hoverOverCopyItemLink() {
		getCopyItemLink().hover();
		return this;
	}

	public CodebeamerLocator getSummaryElement() {
		return locator(".breadcrumbs-summary a.generated-link");
	}

	public CodebeamerLocator getIdElement() {
		return locator("#breadcrumbs-item-id");
	}

	public CodebeamerLocator getDocumentEditViewActionIcon() {
		return this.locator("a.documentViewEditActionIcon");
	}

	public CodebeamerLocator getCopyItemLink() {
		return this.locator(".copy-wiki-link-to-clipboard-button");
	}

	public CodebeamerLocator getItemVersion() {
		return this.locator("span.breadcrumbs-item-version");
	}

	public CodebeamerLocator getBaselineNameElement() {
		return this.workingSetSelectorComponent.getBaselineNameElement();
	}

	public ActionMenuBarComponent workingSetSelector(Consumer<WorkingSetSelectorComponent> consumer) {
		consumer.accept(this.workingSetSelectorComponent);
		return this;
	}


	public WorkingSetSelectorComponent getWorkingSetSelector() {
		return workingSetSelectorComponent;
	}

	public CodebeamerLocator getSelfLocator() {
		return selfLocator();
	}
}
