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

public class MsProjectAssignColumnFormAssertions
		extends AssignColumnFormAssertions<MsProjectAssignColumnFormComponent, MsProjectAssignColumnFormAssertions> {

	protected MsProjectAssignColumnFormAssertions(MsProjectAssignColumnFormComponent component) {
		super(component);
	}

	public MsProjectAssignColumnFormAssertions isImportHierarchiesSelected() {
		return assertAll("Import hierarchies should be selected",
				() -> assertThat(getComponent().getDataOrCellTypeMappingLocator()).not().isChecked());
	}

	public MsProjectAssignColumnFormAssertions isImportHierarchiesNotSelected() {
		return assertAll("Import hierarchies shouldn't be selected",
				() -> assertThat(getComponent().getDataOrCellTypeMappingLocator()).isChecked());
	}
}

