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

package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.microsoft.playwright.assertions.LocatorAssertions;

public class CommentsAndAttachmentsTabAssertions extends AbstractTrackerItemComponentTabAssertion<CommentsAndAttachmentsTabComponent, CommentsAndAttachmentsTabAssertions> {

	protected CommentsAndAttachmentsTabAssertions(CommentsAndAttachmentsTabComponent component) {
		super(component);
	}

	public CommentsAndAttachmentsTabAssertions hasNumberOfComment(int numberOfComment) {
		return assertAll("Number of comment should be %s".formatted(numberOfComment),
				() -> {
					assertThat(getComponent().getCommentContainerElement()).isAttached();
					assertThat(getComponent().getCommentElement()).hasCount(numberOfComment);
				});
	}

	public CommentsAndAttachmentsTabAssertions hasNoComment() {
		return assertAll("There should be no comment",
				() -> assertThat(getComponent().getCommentContainerElement()).not().isVisible(createIsVisibleOptions()));
	}

	private LocatorAssertions.IsVisibleOptions createIsVisibleOptions() {
		return new LocatorAssertions.IsVisibleOptions().setTimeout(ONE_SECOND_AS_MILLIS);
	}
}
