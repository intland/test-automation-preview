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

package com.intland.codebeamer.integration.api.service.trackeritem.comment;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;

public class WikiCommentBuilder extends AbstractTrackerItemCommentBuilder<WikiCommentBuilder> {

	public WikiCommentBuilder() {
		super();
		commentType = WikiTextType.WIKI;
	}

	public WikiCommentBuilder comment(Function<WikiMarkupBuilder, WikiMarkupBuilder> wikiMarkupBuilder) {
		this.comment.append(wikiMarkupBuilder.apply(new WikiMarkupBuilder()).build());
		return this;
	}
}
