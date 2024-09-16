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

import com.intland.codebeamer.integration.nextgen.page.reviewhub.AbstractReviewHubFormAssertion;

public class EditReviewFormAssertion extends AbstractReviewHubFormAssertion<EditReviewFormComponent, EditReviewFormAssertion> {

	public EditReviewFormAssertion(EditReviewFormComponent editReviewComponent) {
		super(editReviewComponent);
	}

	public EditReviewFormAssertion assertReviewName(String reviewName) {
		return assertAll("Updated review name should be %s".formatted(reviewName),
				() -> assertThat(getComponent().getReviewNameInput()).hasText(reviewName));
	}

	public EditReviewFormAssertion assertReviewDescription(String description) {
		return assertAll("Updated review description should be %s".formatted(description),
				() -> assertThat(getComponent().getReviewDescriptionInput()).hasText(description));
	}

	public EditReviewFormAssertion assertDeadLine(String deadLine) {
		return assertAll("Deadline should be updated ",
				() -> assertThat(getComponent().getDeadLineInput()).hasValue(deadLine));
	}

	public EditReviewFormAssertion assertNotifyReviewers() {
		return assertAll("NotifyReviewers checkbox should be checked ",
				() -> assertThat(getComponent().getNotifyReviewersCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertNotifyModerators() {
		return assertAll("NotifyModerators checkbox should be checked ",
				() -> assertThat(getComponent().getNotifyModeratorsCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertSendNotificationForEvents() {
		return assertAll("SendNotificationForEvents checkbox should be checked ",
				() -> assertThat(getComponent().getSendNotificationForEventsCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertRequireSignatureFromModerators() {
		return assertAll("RequireSignatureFromModerators checkbox should be checked ",
				() -> assertThat(getComponent().getRequireSignatureFromModeratorsCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertRequireSignatureFromReviewers() {
		return assertAll("RequireSignatureFromReviewers checkbox should be checked ",
				() -> assertThat(getComponent().getRequireSignatureFromReviewersCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertMinimumNumberOfSignatureCount(String minimumNumberOfSignature) {
		return assertAll("MinimumNumberOfSignatureCount value should match with updated value ",
				() -> assertThat(getComponent().getMinimumNumberOfSignatureInput()).hasValue(minimumNumberOfSignature));
	}

	public EditReviewFormAssertion assertFirstRuleAllCheckbox() {
		return assertAll("FirstRuleAllCheckbox should be checked ",
				() -> assertThat(getComponent().getFirstRuleAllCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertFirstRuleCount(String firstRuleCount) {
		return assertAll("FirstRuleCount value should match with updated value ",
				() -> assertThat(getComponent().getFirstRuleNumberInput()).hasValue(firstRuleCount));
	}

	public EditReviewFormAssertion assertSecondRuleAllCheckbox() {
		return assertAll("SecondRuleAllCheckbox should be checked ",
				() -> assertThat(getComponent().getSecondRuleAllCheckbox()).isChecked());
	}

	public EditReviewFormAssertion assertSecondRuleCount(String secondRuleCount) {
		return assertAll("SecondRuleCount value should match with updated value ",
				() -> assertThat(getComponent().getSecondRuleNumberInput()).hasValue(secondRuleCount));
	}
}
