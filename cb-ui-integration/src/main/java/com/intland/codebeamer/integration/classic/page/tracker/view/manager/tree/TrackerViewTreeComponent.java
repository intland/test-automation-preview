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

package com.intland.codebeamer.integration.classic.page.tracker.view.manager.tree;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.component.tree.AbstractTreeComponent;

public class TrackerViewTreeComponent extends AbstractTreeComponent<TrackerViewTreeComponent, TrackerViewTreeAssertions> {

	public TrackerViewTreeComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, ".reportPickerContainer", ".reportSelectorTreePane");
	}

	@Override
	public TrackerViewTreeAssertions assertThat() {
		return new TrackerViewTreeAssertions(this);
	}
}
