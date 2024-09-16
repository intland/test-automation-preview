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

package com.intland.codebeamer.integration.api.service.trackeritem;

import com.intland.codebeamer.integration.api.service.trackeritem.comment.CommentType;
import com.intland.swagger.client.model.AddTrackerItemCommentRequest;

public class AddCommentBuilder {

	private final AddTrackerItemCommentRequest addTrackerItemCommentRequest;

	public AddCommentBuilder(AddTrackerItemCommentRequest addTrackerItemComment) {
		this.addTrackerItemCommentRequest = addTrackerItemComment;
	}

	public AddTrackerItemCommentRequest build() {
		return addTrackerItemCommentRequest;
	}

	public AddCommentBuilder setComment(String comment) {
		addTrackerItemCommentRequest.setComment(comment);
		return this;
	}

	public AddCommentBuilder setConversationId(String conversationId) {
		addTrackerItemCommentRequest.setConversationId(conversationId);
		return this;
	}

	public AddCommentBuilder setTrackerItem(Integer itemId) {
		addTrackerItemCommentRequest.setTrackerItemId(itemId);
		return this;
	}

	public AddCommentBuilder setParentId(Integer parentId) {
		addTrackerItemCommentRequest.setParentId(parentId);
		return this;
	}

	public AddCommentBuilder setCommentType(CommentType commentType) {
		addTrackerItemCommentRequest.setCommentType(
				AddTrackerItemCommentRequest.CommentTypeEnum.fromValue(commentType.getValue()));
		return this;
	}
}
