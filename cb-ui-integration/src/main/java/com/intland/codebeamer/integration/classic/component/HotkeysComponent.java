package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

import java.util.List;

public class HotkeysComponent
		extends AbstractCodebeamerComponent<HotkeysComponent, HotkeysComponentAssertions> {

	public HotkeysComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#hotkeysHint");
	}

	protected List<HotKeyHint> getHotkeys() {
		return locator("tr").all().stream()
				.map(row -> new HotKeyHint(row.concat("td:nth-child(1)").text(),  row.concat("td:nth-child(2)").text()))
				.toList();
	}

	@Override
	public HotkeysComponentAssertions assertThat() {
		return new HotkeysComponentAssertions(this);
	}


	protected record HotKeyHint(String hotKey, String description) {

	}
}
