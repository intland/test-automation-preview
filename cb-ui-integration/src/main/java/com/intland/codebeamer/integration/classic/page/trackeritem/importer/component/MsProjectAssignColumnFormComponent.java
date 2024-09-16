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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

public class MsProjectAssignColumnFormComponent
		extends AssignColumnFormComponent<MsProjectAssignColumnFormComponent, MsProjectAssignColumnFormAssertions> {

	public MsProjectAssignColumnFormComponent(CodebeamerPage page) {
		super(page);
	}

	@Override
	public MsProjectAssignColumnFormAssertions assertThat() {
		return new MsProjectAssignColumnFormAssertions(this);
	}

	public MsProjectAssignColumnFormComponent selectImportHierarchies() {
		getImportHierarchiesLocator().click();
		return this;
	}

	public CodebeamerLocator getImportHierarchiesLocator() {
		return this.locator("input#importHierarchies");
	}
}
