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

package com.intland.codebeamer.integration.classic.component.field.edit.common.modal;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EditReferenceModalAssertions
		extends AbstractCodebeamerComponentAssert<EditReferenceModalComponent, EditReferenceModalAssertions> {

	public EditReferenceModalAssertions(EditReferenceModalComponent component) {
		super(component);
	}

	public EditReferenceModalAssertions isVisible() {
		return assertAll("Work Config Item Reference Modal is visible",
				() -> assertThat(getComponent().getEditReferenceModal()).isVisible());
	}

}
