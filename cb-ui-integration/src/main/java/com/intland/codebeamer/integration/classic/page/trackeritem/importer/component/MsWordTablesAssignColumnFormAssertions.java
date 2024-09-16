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

public class MsWordTablesAssignColumnFormAssertions
		extends AssignColumnFormAssertions<MsWordTablesAssignColumnFormComponent, MsWordTablesAssignColumnFormAssertions> {

	protected MsWordTablesAssignColumnFormAssertions(MsWordTablesAssignColumnFormComponent component) {
		super(component);
	}

	public MsWordTablesAssignColumnFormAssertions isStartImportAtRow(int row) {
		return assertAll("Start import at row should be '%s'".formatted(Integer.valueOf(row)),
				() -> assertThat(getComponent().getStartImportAtRowLocator()).hasValue(String.valueOf(row)));
	}

	/**
	 * Asserts that the data hierarchy is based on the specified column.
	 *
	 * @param index columnIndex
	 *              <ul>
	 *              <li>An empty string ("") for "Do not generate hierarchy"</li>
	 *              <li>"-1" for "Use the column mapped to Name"</li>
	 *              <li>The zero-indexed column number for other cases.</li>
	 *              </ul>
	 */
	public MsWordTablesAssignColumnFormAssertions isDataHierarchyBasedOnColumn(String index) {
		return assertAll("Data hierarchy based on column should be '%s'".formatted(index),
				() -> assertThat(getComponent().getDataHierarchyBasedOnColumnLocator()).hasValue(index));
	}
}