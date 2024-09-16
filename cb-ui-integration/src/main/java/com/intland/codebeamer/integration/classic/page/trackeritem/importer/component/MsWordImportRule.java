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

package com.intland.codebeamer.integration.classic.page.trackeritem.importer.component;

import java.util.List;

/**
 * @param targetTrackerId     <ul>
 *                            <li>0 to ignore</li>
 *                            <li>1 to import into the current tracker</li>
 *                            <li>2 to append to parent</li>
 *                            <li>otherwise, the ID of the tracker</li>
 *                            </ul>
 * @param containers          List of container names for the rule, e.g., 'the whole Document'
 * @param allConditionsAreMet indicating if all conditions are met: true for all, false for any
 */
public record MsWordImportRule(
		int targetTrackerId,
		List<String> containers,
		boolean allConditionsAreMet,
		List<MsWordImportCondition> msWordImportConditions) {
}
