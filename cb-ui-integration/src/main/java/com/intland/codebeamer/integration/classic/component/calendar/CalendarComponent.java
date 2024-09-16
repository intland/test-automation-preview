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

package com.intland.codebeamer.integration.classic.component.calendar;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class CalendarComponent extends AbstractCodebeamerComponent<CalendarComponent, CalendarComponentAssertions> {

	public CalendarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".xdsoft_datetimepicker");
	}

	public void selectToday() {
		getTodayButton().click();
	}

	public void selectDay(int year, int month, int day) {
		selectyear(year);
		selectMonth(month);
		selectDay(month, day);
	}

	public void selectDay(int month, int day) {
		getDayElement(month, day).click();
	}

	public void selectMonth(int month) {
		getMonthPickerElement().click();
		getMonthElement(month).click();
	}

	public void selectyear(int year) {
		getYearPickerElement().click();
		getYearElement(year).click();
	}

	public CodebeamerLocator getDayElement(int month, int day) {
		return this.locator("div.xdsoft_calendar td[data-date='%s'][data-month='%s']".formatted(day, month - 1));
	}

	public CodebeamerLocator getMonthPickerElement() {
		return this.locator("div.xdsoft_month");
	}

	public CodebeamerLocator getMonthElement(int month) {
		return this.locator("div.xdsoft_select.xdsoft_monthselect div[data-value='%d'].xdsoft_option".formatted(month - 1));
	}

	public CodebeamerLocator getYearPickerElement() {
		return this.locator("div.xdsoft_year");
	}

	public CodebeamerLocator getYearElement(int year) {
		return this.locator("div.xdsoft_select.xdsoft_yearselect div[data-value='%d'].xdsoft_option".formatted(year));
	}

	public CodebeamerLocator getTodayButton() {
		return this.locator("input[type='button'].xdsoft_today_button");
	}

	@Override
	public CalendarComponentAssertions assertThat() {
		return new CalendarComponentAssertions(this);
	}
}
