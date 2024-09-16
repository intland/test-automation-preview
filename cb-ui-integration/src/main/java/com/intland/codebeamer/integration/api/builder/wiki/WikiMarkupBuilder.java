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

package com.intland.codebeamer.integration.api.builder.wiki;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class WikiMarkupBuilder {

	private StringBuilder markup;

	public WikiMarkupBuilder() {
		this.markup = new StringBuilder();
	}

	public WikiMarkupBuilder text(String text) {
		markup.append(text);
		return this;
	}

	public WikiMarkupBuilder bold(String text) {
		markup.append(WikiTextDecoration.BOLD.format(text));
		return this;
	}

	public WikiMarkupBuilder italic(String text) {
		markup.append(WikiTextDecoration.ITALIC.format(text));
		return this;
	}

	public WikiMarkupBuilder underline(String text) {
		markup.append(WikiTextDecoration.UNDERLINE.format(text));
		return this;
	}

	public WikiMarkupBuilder strikeThrough(String text) {
		markup.append(WikiTextDecoration.STRIKETHROUGH.format(text));
		return this;
	}

	public WikiMarkupBuilder heading1(String text) {
		markup.append(WikiTextDecoration.HEADING_1.format(text));
		enter();
		return this;
	}

	public WikiMarkupBuilder heading2(String text) {
		markup.append(WikiTextDecoration.HEADING_2.format(text));
		enter();
		return this;
	}

	public WikiMarkupBuilder heading3(String text) {
		markup.append(WikiTextDecoration.HEADING_3.format(text));
		enter();
		return this;
	}

	public WikiMarkupBuilder heading4(String text) {
		markup.append(WikiTextDecoration.HEADING_4.format(text));
		enter();
		return this;
	}

	public WikiMarkupBuilder heading5(String text) {
		markup.append(WikiTextDecoration.HEADING_5.format(text));
		enter();
		return this;
	}

	public WikiMarkupBuilder preFormatted(String text) {
		markup.append(WikiTextDecoration.PREFORMATTED.format(text));
		newLine();
		return this;
	}

	public WikiMarkupBuilder newLine() {
		markup.append(WikiTextDecoration.LINE_BREAK.getMarkup());
		enter();
		return this;
	}

	public WikiMarkupBuilder doubleNewLine() {
		newLine();
		newLine();
		return this;
	}

	public WikiMarkupBuilder picture(String attachmentName) {
		markup.append(WikiTextDecoration.ATTACHMENT_LINK.format(attachmentName));
		enter();
		return this;
	}

	public WikiMarkupBuilder trackerItemLink(TrackerItemId trackerItemId) {
		markup.append(WikiTextDecoration.ISSUE_LINK.format(trackerItemId.id()));
		enter();
		return this;
	}

	public String build() {
		return markup.toString();
	}

	private void enter() {
		markup.append("\n");
	}
}
