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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.test.testdata.CopiedFile;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CreateCommentsAndAttachmentsComponent
		extends AbstractCodebeamerComponent<CreateCommentsAndAttachmentsComponent, CreateCommentsAndAttachmentsAssertions> {

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	@Component(value = "Wikitext edit", includeInSitemap = false)
	private FroalaComponent froala;
	
	public CreateCommentsAndAttachmentsComponent(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#task-details #task-details-attachments");
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
		this.froala = new FroalaComponent(codebeamerPage, getSelector("div.addCommentBar"));
	}

	public CreateCommentsAndAttachmentsComponent setComment(String text) {
		this.froala.fill(text);
		return this;
	}

	public CreateCommentsAndAttachmentsComponent addAttachment(CopiedFile attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public CreateCommentsAndAttachmentsComponent addAttachment(Path attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public TrackerItemPage save() {
		this.collectRequests(() ->  this.froala.getSaveButton().click())
			.assertUrlCallExecuted("/ajax/tracker/inlineComment.spr");
		return new TrackerItemPage(getCodebeamerPage(), trackerId, trackerItemId);
	}
	
	public CodebeamerLocator getAttachmentContainerElement() {
		return this.locator("td.classAttachment");
	}
	
	@Override
	public CreateCommentsAndAttachmentsAssertions assertThat() {
		return new CreateCommentsAndAttachmentsAssertions(this);
	}

}
