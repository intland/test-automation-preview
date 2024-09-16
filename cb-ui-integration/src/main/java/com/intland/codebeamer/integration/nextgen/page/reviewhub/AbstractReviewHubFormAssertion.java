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

package com.intland.codebeamer.integration.nextgen.page.reviewhub;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractReviewHubFormAssertion<C extends AbstractReviewHubFormComponent<C, A>, A extends AbstractReviewHubFormAssertion<C, A>> extends AbstractCodebeamerComponentAssert<C, A> {

	public AbstractReviewHubFormAssertion(C component) {
		super(component);
	}

	public abstract A assertReviewName(String reviewName);

	public abstract A assertDeadLine(String deadLine);

	public abstract A assertNotifyReviewers();

	public abstract A assertNotifyModerators();

	public abstract A assertSendNotificationForEvents();

	public abstract A assertRequireSignatureFromModerators();

	public abstract A assertRequireSignatureFromReviewers();

	public abstract A assertMinimumNumberOfSignatureCount(String minimumNumberOfSignature);

	public abstract A assertFirstRuleAllCheckbox();

	public abstract A assertFirstRuleCount(String firstRuleCount);

	public abstract A assertSecondRuleAllCheckbox();

	public abstract A assertSecondRuleCount(String secondRuleCount);
}
