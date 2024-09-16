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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.editreviewdialog.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.AbstractReviewHubFormComponent;

public class EditReviewFormComponent extends AbstractReviewHubFormComponent<EditReviewFormComponent, EditReviewFormAssertion> {

	public EditReviewFormComponent(CodebeamerPage codebeamerPage, String formLocator) {
		super(codebeamerPage, formLocator);
	}

	public EditReviewFormComponent setReviewName(String reviewName) {
		getReviewNameInput().fill(reviewName);
		return this;
	}

	public EditReviewFormComponent setDeadLine(String deadLine) {
		getDeadLineInput().fill(deadLine);
		return this;
	}

	public EditReviewFormComponent notifyReviewers() {
		getNotifyReviewersCheckbox().click();
		return this;
	}

	public EditReviewFormComponent notifyModerators() {
		getNotifyModeratorsCheckbox().click();
		return this;
	}

	public EditReviewFormComponent sendNotificationForEvents() {
		getSendNotificationForEventsCheckbox().click();
		return this;
	}

	public EditReviewFormComponent requireSignatureForModerators() {
		getRequireSignatureFromModeratorsCheckbox().click();
		return this;
	}

	public EditReviewFormComponent requireSignatureForReviewers() {
		getRequireSignatureFromReviewersCheckbox().click();
		return this;
	}

	public EditReviewFormComponent allowModeratorsToCreateItemFromReviewComment() {
		getAllowModeratorsToCreateItemFromReviewCommentCheckbox().click();
		return this;
	}

	public EditReviewFormComponent projectForItemsCreatedFromReviewComment() {
		getProjectForItemsCreatedFromReviewCommentDropdown().click();
		return this;
	}

	public EditReviewFormComponent setFirstRuleNumberInput(String firstRuleReviewersCount) {
		getFirstRuleNumberInput().fill(firstRuleReviewersCount);
		return this;
	}

	public EditReviewFormComponent firstRuleAllCheckbox() {
		getFirstRuleAllCheckbox().click();
		return this;
	}

	public EditReviewFormComponent setSecondRuleNumberInput(String secondRuleReviewersCount) {
		getSecondRuleNumberInput().fill(secondRuleReviewersCount);
		return this;
	}

	public EditReviewFormComponent secondRuleAllCheckbox() {
		getSecondRuleAllCheckbox().click();
		return this;
	}

	public EditReviewFormComponent setMinimumNumberOfSignature(String minimumNumberOfSignature) {
		getMinimumNumberOfSignatureInput().fill(minimumNumberOfSignature);
		return this;
	}

	public EditReviewFormComponent reviewDescription(String reviewDescription) {
		getReviewDescriptionInput().type(reviewDescription);
		return this;
	}

	@Override
	public EditReviewFormAssertion assertThat() {
		return new EditReviewFormAssertion(this);
	}
}
