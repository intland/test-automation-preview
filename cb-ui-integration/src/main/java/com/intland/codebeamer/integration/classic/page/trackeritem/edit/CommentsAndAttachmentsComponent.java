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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CommentsAndAttachmentsComponent
		extends AbstractCodebeamerComponent<CommentsAndAttachmentsComponent, CommentsAndAttachmentsAssertions> {

	private static final String USER_PATTERN = "\\[USER:(\\d+)\\]";
	
	private TrackerItemId trackerItemId;

	public CommentsAndAttachmentsComponent(CodebeamerPage codebeamerPage, String selector, TrackerItemId trackerItemId) {
		super(codebeamerPage, selector);
		this.trackerItemId = trackerItemId;
	}

	public UserId getUser() {
		return getUser(getUserLink().getAttribute("data-wikilink")).orElseThrow(() -> new IllegalStateException("User cannot be created"));
	}
	
	public UserId getLastModifiedUser() {
		return getUser(getLastModifiedUserLink().getAttribute("data-wikilink")).orElseThrow(() -> new IllegalStateException("User cannot be created"));
	}
	
	public UpdateCommansAndAttachmentsDialog edit() {
		getActionElement().scrollIntoView();
		getActionElement().click();
		createInlineActionMenuComponent(getActionElement().getAttribute("id")).edit();
		return new UpdateCommansAndAttachmentsDialog(getCodebeamerPage(), trackerItemId);
	}

	public CodebeamerLocator getUserLink() {
		return this.locator("td:nth-child(1) table tr:nth-child(1) a[data-wikilink^='[USER']");
	}
	
	public CodebeamerLocator getLastModifiedUserLink() {
		return this.locator("td:nth-child(1) table tr:nth-child(2) a[data-wikilink^='[USER']");
	}
	
	public CodebeamerLocator getActionElement() {
		return this.locator("td:nth-child(2) > div[id^='actionMenu']");
	}
	
	public CodebeamerLocator getAttachmentElement(String attachmentName) {
		return getAttachmentContainerElement().concat("tr:has(td.commentListFileItem a:text-is('%s'))".formatted(attachmentName));
	}
	
	public CodebeamerLocator getAttachmentElements() {
		return getAttachmentContainerElement().concat("tr");
	}
	
	public CodebeamerLocator getAttachmentContainerElement() {
		return this.locator("td:nth-child(3) table");
	}
	
	public CodebeamerLocator getCommentContentElement() {
		return this.locator("td:nth-child(3) span.wikiContent");
	}
	
	@Override
	public CommentsAndAttachmentsAssertions assertThat() {
		return new CommentsAndAttachmentsAssertions(this);
	}

	private Optional<UserId> getUser(String attribute) {
		Pattern pattern = Pattern.compile(USER_PATTERN);
		Matcher matcher = pattern.matcher(attribute);

		if (matcher.find()) {
			return Optional.of(new UserId(Integer.parseInt(matcher.group(1))));
		} 
		
		return Optional.empty();
	}
	
	private InlineActionMenuComponent createInlineActionMenuComponent(String id) {
		return new InlineActionMenuComponent(getCodebeamerPage(), "td:nth-child(2) div[id='%spopup']".formatted(id), trackerItemId);
	}

}
