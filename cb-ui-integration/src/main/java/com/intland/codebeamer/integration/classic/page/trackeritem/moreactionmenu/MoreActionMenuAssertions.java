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

package com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class MoreActionMenuAssertions
		extends AbstractCodebeamerComponentAssert<MoreActionMenuComponent, MoreActionMenuAssertions> {

	protected MoreActionMenuAssertions(MoreActionMenuComponent component) {
		super(component);
	}

	public MoreActionMenuAssertions hasCopyWorkItemButton() {
		return assertAll("Should have a 'Copy Work Item Link' button",
				() -> {
					assertThat(getComponent().getCopyWorkItemButton()).isVisible();
					assertThat(getComponent().getCopyWorkItemButton()).hasAttribute("title", "Copy Work Item Link (Alt + W)");
				});
	}
}
