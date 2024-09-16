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

package com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class EscalationTableAssertions
		extends AbstractCodebeamerComponentAssert<EscalationTableComponent, EscalationTableAssertions> {

	public EscalationTableAssertions(EscalationTableComponent component) {
		super(component);
	}

	public EscalationTableAssertions updateStatusToIs(int level, String status) {
		int statusId = getComponent().resolveStatusIdByName(status);
		return assertAll("In row #%d, the 'Update status to' field should be '%s'".formatted(Integer.valueOf(level), status),
				() -> assertThat(getComponent().getStatusElement(level)).hasValue(String.valueOf(statusId)));
	}
}
