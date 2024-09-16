/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.component.multiselect;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class MultiSelectMenuComponent
		extends AbstractCodebeamerComponent<MultiSelectMenuComponent, MultiSelectMenuAssertion> {

	public MultiSelectMenuComponent(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: active multiselect needs a proper selector
		super(codebeamerPage, ".ui-multiselect-menu[style*='display: block']");
	}

	public MultiSelectMenuComponent(CodebeamerPage codebeamerPage, String menuTestDataId) {
		this(codebeamerPage, null, menuTestDataId);
	}

	public MultiSelectMenuComponent(CodebeamerPage codebeamerPage, String frameLocator, String menuTestDataId) {
		super(codebeamerPage, frameLocator, "div[data-testid='%s']".formatted(menuTestDataId));
	}

	public MultiSelectMenuComponent select(int... indices) {
		for (int i : indices) {
			this.getByIndex(i).click();
		}
		return this;
	}

	public CodebeamerLocator getByIndex(int index) {
		return this.locator((getMultiselectCheckBoxesClass() + " input >> nth=%d").formatted(Integer.valueOf(index)));
	}

	public MultiSelectMenuComponent select(String... names) {
		for (String name : names) {
			this.getByName(name).click();
		}
		return this;
	}

	public MultiSelectMenuComponent select(List<String> names) {
		for (String name : names) {
			this.getByName(name).click();
		}
		return this;
	}

	public MultiSelectMenuComponent uncheckAll() {
		this.locator(".ui-multiselect-none span.ui-icon.ui-icon-closethick").click();
		return this;
	}

	public CodebeamerLocator getByName(String name) {
		return this.locator(getMultiselectCheckBoxesClass() + " span:text-is('%s')".formatted(name));
	}

	public CodebeamerLocator getSelectedLocator() {
		return this.locator(getMultiselectCheckBoxesClass() + " input:checked");
	}

	private String getMultiselectCheckBoxesClass() {
		// UI-AUTOMATION: active multiselect checkboxes should have a proper selector
		return ".ui-multiselect-checkboxes";
	}

	@Override
	public MultiSelectMenuAssertion assertThat() {
		return new MultiSelectMenuAssertion(this);
	}
}
