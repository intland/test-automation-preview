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

import static org.testng.Assert.assertTrue;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CommentsAndAttachmentsTabComponent
		extends AbstractCodebeamerComponent<CommentsAndAttachmentsTabComponent, CommentsAndAttachmentsTabAssertions> {
	
	private TrackerItemId trackerItemId;
	
	public CommentsAndAttachmentsTabComponent(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#task-details #task-details-attachments");
		this.trackerItemId = trackerItemId;
	}

	public CreateCommentsAndAttachmentsComponent addCommentsAndAttachments() {
		getAddCommentOrAttachmentLink().click();
		return new CreateCommentsAndAttachmentsComponent(getCodebeamerPage(), trackerItemId);
	}
	
	public CommentsAndAttachmentsTabComponent addCommentsAndAttachments(Consumer<CreateCommentsAndAttachmentsComponent> componentConsumer) {
		getAddCommentOrAttachmentLink().click();
		componentConsumer.accept(new CreateCommentsAndAttachmentsComponent(getCodebeamerPage(), trackerItemId));
		return this;
	}
	
	public CommentsAndAttachmentsTabComponent getComment(int indexOfComment, Consumer<CommentsAndAttachmentsComponent> componentConsumer) {
		componentConsumer.accept(new CommentsAndAttachmentsComponent(getCodebeamerPage(), getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment), trackerItemId));
		return this;
	}
	
	public CommentsAndAttachmentsComponent getComment(int indexOfComment) {
		assertTrue(indexOfComment >= 1, "indexOfComment should be a pozitive non zero number");
		return new CommentsAndAttachmentsComponent(getCodebeamerPage(), getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment), trackerItemId);
	}
	
	public CommentsAndAttachmentsTabComponent assertComment(int indexOfComment, Consumer<CommentsAndAttachmentsAssertions> assertConsumer) {
		assertConsumer.accept(new CommentsAndAttachmentsComponent(getCodebeamerPage(), getSelector("table#attachment > tbody > tr:nth-child(%s)").formatted(indexOfComment), trackerItemId).assertThat());
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


}
