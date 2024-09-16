package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.List;

import org.testng.Assert;

import com.beust.jcommander.internal.Lists;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponentAssertions;

public abstract class AbstractEditReferenceFieldAssertions<C extends AbstractEditReferenceFieldComponent<C, A>, A extends AbstractEditReferenceFieldAssertions<C, A>>
		extends AbstractEditFieldComponentAssertions<C, A> {

	protected AbstractEditReferenceFieldAssertions(C component) {
		super(component);
	}

	public A is(TrackerItem... trackerItems) {
		return is(Lists.newArrayList(trackerItems));
	}

	public A is(List<TrackerItem> trackerItems) {
		String expectedHiddenInputValueRegex = getComponent().createTrackerItemAssertionRegex(trackerItems);
		return assertAll(
				"Reference Editor value should match with the following pattern: %s".formatted(expectedHiddenInputValueRegex),
				() -> {
					assertThat(getComponent().getFieldReferenceHiddenInput()).not().isEmpty();
					Assert.assertTrue(getComponent().getFieldReferenceHiddenInputValue().matches(expectedHiddenInputValueRegex));
				});
	}

	public A isEmpty() {
		return assertAll("Reference Field should be empty", () -> {
			assertThat(getComponent().getFieldReferenceHiddenInput()).isEmpty();
		});
	}
}
