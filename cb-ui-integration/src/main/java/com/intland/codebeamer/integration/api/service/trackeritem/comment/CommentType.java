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

public enum CommentType {

	COMMENT("Comment"),

	QUESTION("Question"),

	PROBLEM("Problem"),

	FEATURE_REQUEST("FeatureRequest");

	private String value;

	CommentType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CommentType fromValue(String value) {
		for (CommentType commentType : CommentType.values()) {
			if (commentType.value.equals(value)) {
				return commentType;
			}
		}
		throw new IllegalArgumentException("Unexpected value '" + value + "'");
	}
}
