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
import java.util.List;

import com.intland.codebeamer.integration.api.builder.wiki.WikiTextType;

public record CommentItem(String comment, List<File> attachments, WikiTextType commentType) {
}
