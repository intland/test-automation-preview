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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReviewItemTreeComponent extends
		AbstractCodebeamerComponent<ReviewItemTreeComponent, ReviewItemTreeAssertion> {

	public ReviewItemTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-vertical-split-pane.left-component");
	}

	@Override
	public ReviewItemTreeAssertion assertThat() {
		return new ReviewItemTreeAssertion(this);
	}
}
