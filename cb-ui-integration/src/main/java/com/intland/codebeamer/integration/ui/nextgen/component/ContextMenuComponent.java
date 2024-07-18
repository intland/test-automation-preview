package com.intland.codebeamer.integration.ui.nextgen.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ContextMenuComponent extends AbstractCodebeamerComponent<ContextMenuComponent, ContextMenuAssertions>  {

	public ContextMenuComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.p-contextmenu");
	}

	public CodebeamerLocator getMenuItemByName(String name) {
		return this.locator("a.p-menuitem-link:has(span.p-menuitem-text:text-is('%s'))".formatted(name));
	}

	@Override
	public ContextMenuAssertions assertThat() {
		return new ContextMenuAssertions(this);
	}

}