package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

import java.util.function.Consumer;

public class FooterComponent extends AbstractCodebeamerComponent<FooterComponent, FooterComponentAssertions> {

	@Component("Hotkeys table")
	private final HotkeysComponent hotkeysComponent;

	public FooterComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#footer");
		hotkeysComponent = new HotkeysComponent(codebeamerPage);
	}

	public FooterComponent openHotkeysTable() {
		getHotkeysToggle().click();
		return this;
	}

	private CodebeamerLocator getHotkeysToggle() {
		return this.locator("#hotkey-toggle");
	}

	public FooterComponent hotkeysTable(Consumer<HotkeysComponent> consumer) {
		consumer.accept(hotkeysComponent);
		return this;
	}

	public FooterComponent assertHotkeysTable(Consumer<HotkeysComponentAssertions> assertions) {
		assertions.accept(hotkeysComponent.assertThat());
		return this;
	}

	@Override
	public FooterComponentAssertions assertThat() {
		return new FooterComponentAssertions(this);
	}
}
