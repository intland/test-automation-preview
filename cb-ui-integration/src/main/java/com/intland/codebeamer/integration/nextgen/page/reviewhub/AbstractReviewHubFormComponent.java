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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public abstract class AbstractReviewHubFormComponent<C extends AbstractReviewHubFormComponent<C, A>, A extends AbstractReviewHubFormAssertion<C, A>> extends AbstractCodebeamerComponent<C, A> {

	public AbstractReviewHubFormComponent(CodebeamerPage codebeamerPage, String formLocator) {
		super(codebeamerPage, formLocator);
	}

	public abstract C setReviewName(String reviewName);

	public abstract C setDeadLine(String deadLine);

	public abstract C notifyReviewers();

	public abstract C notifyModerators();

	public abstract C sendNotificationForEvents();

	public abstract C requireSignatureForModerators();

	public abstract C requireSignatureForReviewers();

	public abstract C setMinimumNumberOfSignature(String minimumNumberOfSignature);

	public abstract C allowModeratorsToCreateItemFromReviewComment();

	public abstract C setFirstRuleNumberInput(String firstRuleReviewersCount);

	public abstract C firstRuleAllCheckbox();

	public abstract C setSecondRuleNumberInput(String secondRuleReviewersCount);

	public abstract C secondRuleAllCheckbox();

	public CodebeamerLocator getReviewNameInput() {
		return this.locator("input#crc2stepNameInput");
	}

	public CodebeamerLocator getDeadLineInput() {
		return this.locator("p-calendar >> input");
	}

	public CodebeamerLocator getNotifyReviewersCheckbox() {
		return this.locator("p-checkbox[data-cy='notifyReviewersCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getNotifyModeratorsCheckbox() {
		return this.locator("p-checkbox[data-cy='notifyModeratorsCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getSendNotificationForEventsCheckbox() {
		return this.locator("p-checkbox[data-cy='sendNotificationCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getRequireSignatureFromModeratorsCheckbox() {
		return this.locator("p-checkbox[data-cy='requiresSignatureToFinishCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getRequireSignatureFromReviewersCheckbox() {
		return this.locator("p-checkbox[data-cy='requiresSignatureFromReviewersCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getAllowModeratorsToCreateItemFromReviewCommentCheckbox() {
		return this.locator("p-checkbox[data-cy='use-review-finding-checkbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getMinimumNumberOfSignatureInput() {
		return this.locator("p-inputnumber[data-cy='minimumNumberOfSignatureInputContainer'] input");
	}

	public CodebeamerLocator getProjectForItemsCreatedFromReviewCommentDropdown() {
		return this.locator("p-dropdown[data-cy='review-finding-project']");
	}

	public CodebeamerLocator getTrackerForItemsCreatedFromReviewCommentDropdown() {
		return this.locator("p-dropdown[data-cy='review-finding-tracker']");
	}

	public CodebeamerLocator getReferenceFieldForItemsCreatedFromReviewCommentDropdown() {
		return this.locator("p-dropdown[data-cy='review-finding-reference-field']");
	}

	public CodebeamerLocator getFirstRuleAllCheckbox() {
		return this.locator("p-checkbox[data-cy='rule1AllCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getFirstRuleNumberInput() {
		return this.locator("input[data-cy='rule1NumberInput']");
	}

	public CodebeamerLocator getSecondRuleAllCheckbox() {
		return this.locator("p-checkbox[data-cy='rule2AllCheckbox'] .p-checkbox-box");
	}

	public CodebeamerLocator getSecondRuleNumberInput() {
		return this.locator("input[data-cy='rule2NumberInput']");
	}

	public CodebeamerLocator getReviewDescriptionInput() {
		return this.locator("cb-editor[data-cy='crc2descriptionInput'] div.fr-element.fr-view[contenteditable='true']");
	}
}
