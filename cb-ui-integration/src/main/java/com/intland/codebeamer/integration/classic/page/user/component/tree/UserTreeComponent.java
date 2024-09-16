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

package com.intland.codebeamer.integration.classic.page.user.component.tree;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.component.tree.AbstractTreeComponent;

public class UserTreeComponent extends AbstractTreeComponent<UserTreeComponent, UserTreeAssertions> {

	public UserTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	public UserTreeAssertions assertThat() {
		return new UserTreeAssertions(this);
	}
}
