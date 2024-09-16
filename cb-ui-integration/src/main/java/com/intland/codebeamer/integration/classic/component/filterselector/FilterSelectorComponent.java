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

package com.intland.codebeamer.integration.classic.component.filterselector;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FilterSelectorComponent
		extends AbstractCodebeamerComponent<FilterSelectorComponent, FilterSelectorAssertions> {

	public FilterSelectorComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".filterSelector");
	}

	public FilterSelectorComponent(CodebeamerPage codebeamerPage, String framelocator) {
		super(codebeamerPage, framelocator, ".filterSelector");
	}

	public FilterSelectorAssertions assertThat() {
		return new FilterSelectorAssertions(this);
	}

	public void selectField(String addColumnName) {
		this.locator("+.addFieldSelector li:has(label span:has-text('%s'))".formatted(addColumnName)).click();
	}

	public void addGroupByOption(String listElementName) {
		this.locator("+.groupBySelector li:has(span:text-is('%s'))".formatted(listElementName)).click();
	}

	public void addOrderByOption(String listElementName) {
		this.locator("+.orderBySelector li:has(span:text-is('%s'))".formatted(listElementName)).click();
	}

}