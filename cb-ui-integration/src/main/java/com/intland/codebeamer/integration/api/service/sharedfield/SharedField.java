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

package com.intland.codebeamer.integration.api.service.sharedfield;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.MemberType;

public record SharedField(SharedFieldId id, String name, FieldType fieldType, String displayName, String description,
						  MemberType memberType, ArtifactType referenceType) {
}
