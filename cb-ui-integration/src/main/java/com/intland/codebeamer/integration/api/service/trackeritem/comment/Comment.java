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

import java.util.List;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public record Comment(CommentId id, String comment, List<AttachmentId> attachmentIds) {
}
