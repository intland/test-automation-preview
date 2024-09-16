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

import static org.testng.Assert.assertTrue;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class CommentsAndAttachmentsTabComponent
		extends AbstractTrackerItemComponentTab<CommentsAndAttachmentsTabComponent, CommentsAndAttachmentsTabAssertions> {

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	public CommentsAndAttachmentsTabComponent(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#task-details #task-details-attachments");
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	public CreateCommentsAndAttachmentsComponent addCommentsAndAttachments() {
		getAddCommentOrAttachmentLink().click();
		return new CreateCommentsAndAttachmentsComponent(getCodebeamerPage(), trackerId, trackerItemId);
	}
	
	public CommentsAndAttachmentsTabComponent addCommentsAndAttachments(Consumer<CreateCommentsAndAttachmentsComponent> componentConsumer) {
		getAddCommentOrAttachmentLink().click();
		componentConsumer.accept(new CreateCommentsAndAttachmentsComponent(getCodebeamerPage(), trackerId, trackerItemId));
		return this;
	}
	
	public CommentsAndAttachmentsTabComponent getComment(int indexOfComment, Consumer<CommentsAndAttachmentsComponent> componentConsumer) {
		assertTrue(indexOfComment >= 1, "indexOfComment should be a positive non zero number");
		String selector = getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment);
		componentConsumer.accept(new CommentsAndAttachmentsComponent(getCodebeamerPage(), selector, trackerId, trackerItemId));
		return this;
	}
	
	public CommentsAndAttachmentsComponent getComment(int indexOfComment) {
		assertTrue(indexOfComment >= 1, "indexOfComment should be a positive non zero number");
		String selector = getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment);
		return new CommentsAndAttachmentsComponent(getCodebeamerPage(), selector, trackerId, trackerItemId);
	}
	
	public CommentsAndAttachmentsTabComponent assertComment(int indexOfComment, Consumer<CommentsAndAttachmentsAssertions> assertConsumer) {
		assertTrue(indexOfComment >= 1, "indexOfComment should be a positive non zero number");
		String selector = getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment);
		assertConsumer.accept(new CommentsAndAttachmentsComponent(getCodebeamerPage(), selector, trackerId, trackerItemId).assertThat());
		return this;
	}
	
	public CodebeamerLocator getEmptyCommentContainerElement() {
		return this.locator("table#emptyAttachment");
	}
	
	public CodebeamerLocator getCommentContainerElement() {
		return this.locator("table#attachment");
	}
	
	public CodebeamerLocator getCommentElement() {
		return this.locator("table#attachment > tbody > tr");
	}
	
	public CodebeamerLocator getAddCommentOrAttachmentLink() {
		return this.locator("div.addCommentLink a");
	}
	
	@Override
	public CommentsAndAttachmentsTabAssertions assertThat() {
		return new CommentsAndAttachmentsTabAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#task-details-attachments-tab";
	}

	@Override
	protected String getTabName() {
		return "Comments & Attachments";
	}
}
