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

public class UpdateCommentsAndAttachmentsFormComponent
		extends AbstractCodebeamerComponent<UpdateCommentsAndAttachmentsFormComponent, UpdateCommentsAndAttachmentsFormAssertions> {

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	@Component(value = "Wikitext edit", includeInSitemap = false)
	private FroalaComponent froala;

	public UpdateCommentsAndAttachmentsFormComponent(CodebeamerPage codebeamerPage, String frameLocator, TrackerId trackerId,
			TrackerItemId trackerItemId) {
		super(codebeamerPage, frameLocator, "form#popupLayoutContentArea");
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
		this.froala = new FroalaComponent(codebeamerPage, frameLocator, getSelector("div.edit-comment-section"));
	}
	
	public UpdateCommentsAndAttachmentsFormComponent setComment(String text) {
		this.froala.fill(text);
		return this;
	}

	public UpdateCommentsAndAttachmentsFormComponent addAttachment(CopiedFile attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public UpdateCommentsAndAttachmentsFormComponent addAttachment(Path attachment) {
		getCodebeamerPage().uploadFiles(() -> froala.getUploadFileButton().click(), attachment);
		return this;
	}
	
	public TrackerItemPage save() {
		this.collectRequests(() -> this.froala.getSaveButton().click())
			.assertUrlCallExecuted("/wysiwyg/convertHTMLToWiki.spr");
		return new TrackerItemPage(getCodebeamerPage(), trackerId, trackerItemId);
	}
	
	public CodebeamerLocator getAttachmentContainerElement() {
		return this.locator("td.classAttachment");
	}
	
	@Override
	public UpdateCommentsAndAttachmentsFormAssertions assertThat() {
		return new UpdateCommentsAndAttachmentsFormAssertions(this);
	}

}
