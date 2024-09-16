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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.calendar.CalendarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.DateFormat;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseChartBarEditDialog
		extends AbstractCodebeamerComponent<ReleaseChartBarEditDialog, ReleaseChartBarEditAssertions> {

	public static final String DIALOG_SELECTOR  = "div.gantt_cal_light";

	public ReleaseChartBarEditDialog(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: popup dialog needs a proper identifier - double click on a release bar element in a release gantt chart
		super(codebeamerPage, DIALOG_SELECTOR);
	}

	public void save() {
		getOkButton().click();
	}

	public void cancel() {
		getCancelButton().click();
	}

	public String getStartDateInputValue() {
		return getStartDateInput().value();
	}

	public String getEndDateInputValue() {
		return getEndDateInput().value();
	}

	public ReleaseChartBarEditDialog openStartDateCalendar(Consumer<CalendarComponent> calendar) {
		getStartDateCalendarButton().click();
		calendar.accept(new CalendarComponent(getCodebeamerPage()));
		return this;
	}

	public ReleaseChartBarEditDialog openEndDateCalendar(Consumer<CalendarComponent> calendar) {
		getEndDateCalendarButton().click();
		calendar.accept(new CalendarComponent(getCodebeamerPage()));
		return this;
	}

	public ReleaseChartBarEditDialog setStartDate(int year, int month, int day, DateFormat dateFormat) {
		getStartDateInput().clear().fill(getFormattedDate(year, month, day, dateFormat));
		return this;
	}

	public ReleaseChartBarEditDialog setEndDate(int year, int month, int day, DateFormat dateFormat) {
		getEndDateInput().clear().fill(getFormattedDate(year, month, day, dateFormat));
		return this;
	}

	public CodebeamerLocator getStartDateCalendarButton() {
		return this.locator("#calendarLink_start_date_input");
	}

	public CodebeamerLocator getEndDateCalendarButton() {
		return this.locator("#calendarLink_end_date_input");
	}

	public CodebeamerLocator getStartDateInput() {
		return this.locator("#start_date_input");
	}

	public CodebeamerLocator getEndDateInput() {
		return this.locator("#end_date_input");
	}

	public CodebeamerLocator getOkButton() {
		return this.locator("div.gantt_save_btn_set");
	}

	public CodebeamerLocator getCancelButton() {
		return this.locator("div.gantt_cancel_btn_set");
	}

	protected String getFormattedDate(int year, int month, int day, DateFormat dateFormat) {
		LocalDate dateTime = LocalDate.of(year, month, day);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.getValue()).withLocale(Locale.ENGLISH);
		return dateTime.format(formatter);
	}

	@Override
	public ReleaseChartBarEditAssertions assertThat() {
		return new ReleaseChartBarEditAssertions(this);
	}
}
