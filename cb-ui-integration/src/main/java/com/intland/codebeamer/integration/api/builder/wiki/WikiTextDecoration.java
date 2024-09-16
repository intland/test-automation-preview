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

public enum WikiTextDecoration {

	NONE(" %s "),
	BOLD(" __%s__ "),
	ITALIC(" ''%s'' "),
	UNDERLINE(" %%%%(text-decoration:underline;)%s%%! "),
	STRIKETHROUGH(" %%%%(text-decoration:line-through;)%s%%! "),
	LINE_BREAK("\\\\"),
	HEADING_1("!1 %s \\\\"),
	HEADING_2("!2 %s \\\\"),
	HEADING_3("!3 %s \\\\"),
	HEADING_4("!4 %s \\\\"),
	HEADING_5("!5 %s \\\\"),
	PREFORMATTED("{{{ %s }}}"),
	ISSUE_LINK("[ISSUE:%s]"),
	ATTACHMENT_LINK("[!%s!]");

	private final String markup;

	WikiTextDecoration(String markup) {
		this.markup = markup;
	}

	public String getMarkup() {
		return markup;
	}

	public String format(Object... input) {
		return String.format(this.markup, input);
	}
}
