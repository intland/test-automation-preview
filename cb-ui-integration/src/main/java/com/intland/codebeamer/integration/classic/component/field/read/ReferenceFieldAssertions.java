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

package com.intland.codebeamer.integration.classic.component.field.read;

import java.util.Arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.classic.component.field.helper.ReferenceFieldValueConverter.ReferenceFieldValue;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReferenceFieldAssertions extends AbstractCodebeamerComponentAssert<ReferenceFieldComponent, ReferenceFieldAssertions> {

	protected ReferenceFieldAssertions(ReferenceFieldComponent component) {
		super(component);
	}

	public ReferenceFieldAssertions contains(TrackerItem trackerItem) {
		return assertAll("Tracker item(%s - %s) should be selected".formatted(trackerItem.id().id(), trackerItem.name()), () -> {
			assertThat(getComponent().getValueElement()).isAttached();
			MatcherAssert.assertThat(getComponent().getReferences(), Matchers.hasItem(convertToReferenceFieldValue(trackerItem)));
		});
	}

	public ReferenceFieldAssertions containsOnly(TrackerItem... trackerItems) {
		return assertAll("Tracker items (%s) should be selected".formatted(Arrays.toString(trackerItems)), () -> {
			assertThat(getComponent().getValueElement()).isAttached();

			ReferenceFieldValue[] expectedReferences = Arrays.stream(trackerItems)
					.map(this::convertToReferenceFieldValue)
					.toArray(ReferenceFieldValue[]::new);
			MatcherAssert.assertThat(getComponent().getReferences(), Matchers.contains(expectedReferences));
		});
	}

	private ReferenceFieldValue convertToReferenceFieldValue(TrackerItem trackerItem) {
		return new ReferenceFieldValue(ArtifactType.TRACKER_ITEM, trackerItem.id().id());
	}

}
