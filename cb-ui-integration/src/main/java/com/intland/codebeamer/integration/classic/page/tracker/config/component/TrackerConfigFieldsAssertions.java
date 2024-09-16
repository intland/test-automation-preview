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

package com.intland.codebeamer.integration.classic.page.tracker.config.component;

import java.util.List;
import java.util.regex.Pattern;

import org.testng.Assert;

import com.intland.codebeamer.integration.common.tracker.TrackerLayoutLabel;

public class TrackerConfigFieldsAssertions extends
		AbstractTrackerConfigAssertions<TrackerConfigFieldsTab, TrackerConfigFieldsAssertions> {

	protected TrackerConfigFieldsAssertions(TrackerConfigFieldsTab component) {
		super(component);
	}

	public TrackerConfigFieldsAssertions assertFieldIdBetween(String fieldName, int from, int to) {
		return assertAll("The id of %s field should be between %d to '%d' ".formatted(fieldName, from, to),
				() -> {
					assertThat(getComponent().getRowFieldId(fieldName))
							.hasAttribute("data-tt-id", Pattern.compile(".*"));
					int fieldId = Integer.parseInt(getComponent().getRowFieldId(fieldName).getAttribute("data-tt-id"));
					Assert.assertTrue(fieldId >= from && fieldId <= to);
				});
	}

	public TrackerConfigFieldsAssertions assertFieldOrder(List<TrackerLayoutLabel> configRowsIds) {
		return assertAll("Rows should be found: %s ".formatted(configRowsIds),
				() -> {
					for (int i = 1; i <= configRowsIds.size(); i++) {
						assertThat(getComponent().getFieldRow(i))
								.hasAttribute("data-tt-id", String.valueOf(configRowsIds.get(i - 1).getFieldId()));
					}
				});
	}
}
