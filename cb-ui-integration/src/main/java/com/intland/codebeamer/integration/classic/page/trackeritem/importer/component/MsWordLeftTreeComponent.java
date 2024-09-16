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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class MsWordLeftTreeComponent
		extends AbstractCodebeamerComponent<MsWordLeftTreeComponent, MsWordLeftTreeAssertions> {

	public MsWordLeftTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-west");
	}

	@Override
	public MsWordLeftTreeAssertions assertThat() {
		return new MsWordLeftTreeAssertions(this);
	}

}
