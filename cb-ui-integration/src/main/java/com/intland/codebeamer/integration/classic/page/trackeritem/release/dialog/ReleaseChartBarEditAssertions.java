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

package com.intland.codebeamer.integration.classic.page.trackeritem.release.dialog;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleaseChartBarEditAssertions extends
		AbstractCodebeamerComponentAssert<ReleaseChartBarEditDialog, ReleaseChartBarEditAssertions> {

	protected ReleaseChartBarEditAssertions(ReleaseChartBarEditDialog component) {
		super(component);
	}

	public ReleaseChartBarEditAssertions startDateIs(int year, int month, int day, DateFormat dateFormat) {
		String formattedDate = getComponent().getFormattedDate(year, month, day, dateFormat);
		return assertAll("Field should have '%s' value".formatted(formattedDate),
				() -> assertThat(getComponent().getStartDateInput()).hasValue(formattedDate));
	}

	public ReleaseChartBarEditAssertions endDateIs(int year, int month, int day, DateFormat dateFormat) {
		String formattedDate = getComponent().getFormattedDate(year, month, day, dateFormat);
		return assertAll("Field should have '%s' value".formatted(formattedDate),
				() -> assertThat(getComponent().getEndDateInput()).hasValue(formattedDate));
	}

	public ReleaseChartBarEditAssertions startDateIsBefore(int year, int month, int day, DateFormat dateFormat) {
		LocalDate checkValue = LocalDate.of(year, month, day);
		LocalDate currentValue = parseDate(getComponent().getStartDateInputValue(), dateFormat);

		return assertAll("Current value '%s' should be before '%s'".formatted(currentValue, checkValue),
				() -> assertTrue(currentValue.isBefore(checkValue)));
	}

	public ReleaseChartBarEditAssertions endDateIsAfter(int year, int month, int day, DateFormat dateFormat) {
		LocalDate checkValue = LocalDate.of(year, month, day);
		LocalDate currentValue = parseDate(getComponent().getEndDateInputValue(), dateFormat);

		return assertAll("Current value '%s' should be after '%s'".formatted(currentValue, checkValue),
				() -> assertTrue(checkValue.isAfter(currentValue)));
	}

	private LocalDate parseDate(String input, DateFormat dateFormat) {
		return LocalDate.parse(input, DateTimeFormatter.ofPattern(dateFormat.getValue()));
	}
}
