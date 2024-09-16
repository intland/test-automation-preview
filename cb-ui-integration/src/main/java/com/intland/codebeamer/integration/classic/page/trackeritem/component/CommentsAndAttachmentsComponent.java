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

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CommentsAndAttachmentsComponent
		extends AbstractCodebeamerComponent<CommentsAndAttachmentsComponent, CommentsAndAttachmentsAssertions> {

	private static final String USER_PATTERN = "\\[USER:(\\d+)\\]";

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	public CommentsAndAttachmentsComponent(CodebeamerPage codebeamerPage, String selector, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, selector);
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	public UserId getUser() {
		return getUser(getUserLink().getAttribute("data-wikilink")).orElseThrow(() -> new IllegalStateException("User cannot be created"));
	}

	public int getCommentId() {
		// UI-AUTOMATION: add data-testid attribute on the UI to easily get the id of a comment
		String[] firstCellClasses = this.locator("td:nth-child(1)[class*='comment']").getAttribute("class").split("\\s+");
		return Arrays.stream(firstCellClasses)
				.filter(className -> className.startsWith("comment-"))
				.map(className -> StringUtils.substringAfter(className, "comment-"))
				.mapToInt(Integer::parseInt)
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Couldn't find comment id"));
	}

	public UserId getLastModifiedUser() {
		return getUser(getLastModifiedUserLink().getAttribute("data-wikilink")).orElseThrow(() -> new IllegalStateException("User cannot be created"));
	}
	
	public CommentsAndAttachmentsComponent copyCommentLink() {
		openInlineActionMenu().copyCommentLink();
		return this;
	}

	public UpdateCommentsAndAttachmentsDialog edit() {
		openInlineActionMenu().edit();
		return new UpdateCommentsAndAttachmentsDialog(getCodebeamerPage(), trackerId, trackerItemId);
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

	private InlineActionMenuComponent openInlineActionMenu() {
		getActionElement().scrollIntoView();
		getActionElement().click();
		return createInlineActionMenuComponent(getActionElement().getAttribute("id"));
	}

	private InlineActionMenuComponent createInlineActionMenuComponent(String id) {
		return new InlineActionMenuComponent(getCodebeamerPage(), "td:nth-child(2) div[id='%spopup']".formatted(id), trackerId, trackerItemId);
	}

}
