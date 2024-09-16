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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;

public abstract class AbstractTrackerItemCommentBuilder<B extends AbstractTrackerItemCommentBuilder> {

	protected StringBuilder comment;

	protected List<File> attachments;

	protected WikiTextType commentType;

	public AbstractTrackerItemCommentBuilder() {
		comment = new StringBuilder();
		attachments = new ArrayList<>();
	}

	public B comment(String comment) {
		this.comment.append(comment);
		return (B) this;
	}

	public B attachment(File attachment) {
		this.attachments.add(attachment);
		return (B) this;
	}

	public B attachment(Path attachment) {
		File file = attachment.toFile();
		this.attachments.add(file);
		return (B) this;
	}

	public CommentItem build() {
		return new CommentItem(comment.toString(), attachments, commentType);
	}

}
