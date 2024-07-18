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

package com.intland.codebeamer.integration.api.builder.reference;

import java.util.List;

import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.api.service.project.Project;

public record ReferenceFilter(Project project, String trackerName, List<TrackerType> trackerTypes, StatusMeaning status) {
}
