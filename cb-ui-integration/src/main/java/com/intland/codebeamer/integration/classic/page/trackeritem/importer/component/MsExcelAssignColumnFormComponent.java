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

public class MsExcelAssignColumnFormComponent
		extends AssignColumnFormComponent<MsExcelAssignColumnFormComponent, MsExcelAssignColumnFormAssertions> {

	public MsExcelAssignColumnFormComponent(CodebeamerPage page) {
		super(page);
	}

	@Override
	public MsExcelAssignColumnFormAssertions assertThat() {
		return new MsExcelAssignColumnFormAssertions(this);
	}

	public MsExcelAssignColumnFormComponent fillStartImportAtRow(int start) {
		this.getStartImportAtRowLocator().fill(String.valueOf(start));
		return this;
	}

	public CodebeamerLocator getStartImportAtRowLocator() {
		return this.locator("input#startImportAtRow");
	}

	/**
	 * Sets the data hierarchy based on the specified column.
	 *
	 * @param index columnIndex
	 *              <ul>
	 *              <li>An empty string ("") for "Do not generate hierarchy"</li>
	 *              <li>"-1" for "Use the column mapped to Name"</li>
	 *              <li>The zero-indexed column number for other cases.</li>
	 *              </ul>
	 */
	public MsExcelAssignColumnFormComponent selectDataHierarchyBasedOnColumn(String index) {
		this.getDataHierarchyBasedOnColumnLocator().selectOption(index);
		return this;
	}

	public CodebeamerLocator getDataHierarchyBasedOnColumnLocator() {
		return this.locator("select#importHierarchyIndentationColumn");
	}
}
