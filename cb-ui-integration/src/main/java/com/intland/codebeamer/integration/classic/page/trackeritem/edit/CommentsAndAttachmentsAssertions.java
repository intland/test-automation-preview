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

package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import java.util.Arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class CommentsAndAttachmentsAssertions extends AbstractCodebeamerComponentAssert<CommentsAndAttachmentsComponent, CommentsAndAttachmentsAssertions> {

	protected CommentsAndAttachmentsAssertions(CommentsAndAttachmentsComponent component) {
		super(component);
	}
	
	public CommentsAndAttachmentsAssertions isCreatedBy(User user) {
		return assertAll("Comment should be created by User(%s-%s)".formatted(user.getUserId(), user.getName()),
				() -> {
					assertThat(getComponent().getUserLink()).isVisible();
					MatcherAssert.assertThat(getComponent().getUser(), Matchers.equalTo(user.getUserId()));
				});
	}
	
	public CommentsAndAttachmentsAssertions isUpdatedBy(User user) {
		return assertAll("Comment should be updated by User(%s-%s)".formatted(user.getUserId(), user.getName()),
				() -> {
					assertThat(getComponent().getLastModifiedUserLink()).isVisible();
					MatcherAssert.assertThat(getComponent().getLastModifiedUser(), Matchers.equalTo(user.getUserId()));
				});
	}
	
	public CommentsAndAttachmentsAssertions hasText(String text) {
		return assertAll("Comment should have text(%s)".formatted(text),
				() -> {
					assertThat(getComponent().getCommentContentElement()).hasText(text);
					System.out.println(getComponent().getCommentContentElement().text());
				});
	}
	
	public CommentsAndAttachmentsAssertions containsText(String text) {
		return assertAll("Comment should contain text(%s)".formatted(text),
				() -> assertThat(getComponent().getCommentContentElement()).containsText(text));
	}
	
	public CommentsAndAttachmentsAssertions hasAttachments(String... attachmentName) {
		hasNumberOfAttachments(attachmentName.length);
		Arrays.stream(attachmentName).forEach(this::hasAttachment);
		return this;
	}
	
	public CommentsAndAttachmentsAssertions hasAttachment(String attachmentName) {
		return assertAll("Comment should contain attachment(%s)".formatted(attachmentName),
				() -> assertThat(getComponent().getAttachmentElement(attachmentName)).isVisible());
	}

	public CommentsAndAttachmentsAssertions hasNumberOfAttachments(int numberOfAttachment) {
		return assertAll("Comment should have %s attachments".formatted(numberOfAttachment),
				() -> assertThat(getComponent().getAttachmentElements()).hasCount(numberOfAttachment));
	}


	
}
