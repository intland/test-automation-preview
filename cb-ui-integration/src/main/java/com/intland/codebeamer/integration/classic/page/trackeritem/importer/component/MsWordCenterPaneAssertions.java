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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class MsWordCenterPaneAssertions
		extends AbstractCodebeamerComponentAssert<MsWordCenterPaneComponent, MsWordCenterPaneAssertions> {

	MsWordCenterPaneAssertions(MsWordCenterPaneComponent component) {
		super(component);
	}

	public MsWordCenterPaneAssertions areItemsIgnored(int... indices) {
		return assertAll("Items should be ignored: %s".formatted(Arrays.toString(indices)),
				() -> Arrays.stream(indices)
						.forEach(index -> assertThat(getComponent().getItemLocatorByIndex(index))
								.hasClass(Pattern.compile(".*notInclude.*"))));
	}

	public MsWordCenterPaneAssertions areItemsSelected(int... indices) {
		return assertAll("Items should be selected: %s".formatted(Arrays.toString(indices)),
				() -> Arrays.stream(indices)
						.forEach(index -> assertThat(getComponent().getItemLocatorByIndex(index))
								.hasClass(Pattern.compile(".*include.*"))));
	}
}
