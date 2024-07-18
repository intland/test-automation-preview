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

package com.intland.codebeamer.integration.classic.page.tracker.config.fields.base;

import java.util.Arrays;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.DataSource;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.MemberType;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class FieldConfigDialog extends AbstractCodebeamerComponent<FieldConfigDialog, FieldConfigAssertions> {

	public FieldConfigDialog(CodebeamerPage codebeamerPage, String componentLocator) {
		super(codebeamerPage, componentLocator);
	}

	@Override
	public FieldConfigAssertions assertThat() {
		return new FieldConfigAssertions(this);
	}

	public FieldConfigDialog setLabel(String label) {
		getLabelInput().fill(label);
		return this;
	}

	public FieldConfigDialog setHidden(boolean hidden) {
		getHiddenCheckbox().check(hidden);
		return this;
	}

	public FieldConfigDialog setTypeSelector(FieldType type) {
		getTypeSelector().click();
		getTypeSelector().selectOption(type.getTypeIdAsString());
		return this;
	}

	public FieldConfigDialog setSharedField() {
		// TODO
		return this;
	}

	public FieldConfigDialog setListable(boolean listable) {
		getListableCheckbox().check(listable);
		return this;
	}

	public FieldConfigDialog setTitle(String title) {
		getTitleInput().fill(title);
		return this;
	}

	public FieldConfigDialog setDescription(String description) {
		getDescriptionInput().fill(description);
		return this;
	}

	public FieldConfigDialog setMultiple(boolean multiple) {
		getMultipleCheckbox().check(multiple);
		return this;
	}

	public FieldConfigDialog setDataSourceSelector(DataSource dataSource) {
		getDataSourceSelector().click();
		getDataSourceSelector().selectOption(dataSource.getTypeIdAsString());
		return this;
	}

	public FieldConfigDialog setDependsOnSelector(String dependsOn) {
		getDependsOnSelector().click();
		getDependsOnSelector().selectOption(dependsOn);
		return this;
	}

	public FieldConfigDialog setUnion(boolean union) {
		getUnionCheckbox().check(union);
		return this;
	}

	public FieldConfigDialog setMemberSelector(MemberType... type) {
		getMemberSelector().click();
		getMemberSelector().selectOption(Arrays.stream(type).map(MemberType::getTypeIdAsString).toArray(String[]::new));
		return this;
	}

	public FieldConfigDialog setFormatted(boolean formatted) {
		getFormattedCheckbox().check(formatted);
		return this;
	}

	public FieldConfigDialog setDigits(String digits) {
		getDigitsInput().fill(digits);
		return this;
	}

	public FieldConfigDialog setMin(String min) {
		getMinInput().fill(min);
		return this;
	}

	public FieldConfigDialog setMax(String max) {
		getMaxInput().fill(max);
		return this;
	}

	public FieldConfigDialog setWidth(String width) {
		getWidthInput().fill(width);
		return this;
	}

	public FieldConfigDialog setHeight(String height) {
		getHeightInput().fill(height);
		return this;
	}

	public FieldConfigDialog setOmitSuspected(boolean omitSuspected) {
		getOmitSuspectedCheckbox().check(omitSuspected);
		return this;
	}

	public FieldConfigDialog setOmitMerge(boolean omitMerge) {
		getOmitMergeCheckbox().check(omitMerge);
		return this;
	}

	public FieldConfigDialog setComputedAs(String computedAs) {
		getComputedAsInput().fill(computedAs);
		return this;
	}

	public FieldConfigDialog setHideIf(String hideIf) {
		getHideIfInput().fill(hideIf);
		return this;
	}

	public FieldConfigDialog setMandatoryIf(String mandatoryIf) {
		getMandatoryIfInput().fill(mandatoryIf);
		return this;
	}

	public FieldConfigDialog setMandatory(boolean mandatory) {
		getMandatoryCheckbox().check(mandatory);
		return this;
	}

	public FieldConfigDialog setMandatoryExceptInStatus() {
		// TODO
		return this;
	}

	public FieldConfigDialog setServiceDeskLabel(String serviceDeskLabel) {
		getServiceDeskLabelInput().fill(serviceDeskLabel);
		return this;
	}

	public FieldConfigDialog setServiceDeskDescription(String serviceDeskDescription) {
		getServiceDeskDescriptionInput().fill(serviceDeskDescription);
		return this;
	}

	public FieldConfigDialog setYear(boolean year) {
		getYearCheckbox().check(year);
		return this;
	}

	public FieldConfigDialog setMonth(boolean month) {
		getMonthCheckbox().check(month);
		return this;
	}

	public FieldConfigDialog setDay(boolean day) {
		getDayCheckbox().check(day);
		return this;
	}

	public FieldConfigDialog setTime(boolean time) {
		getTimeCheckbox().check(time);
		return this;
	}

	public FieldConfigDialog setRowNumbers(boolean rowNumbers) {
		getRowNumbersCheckbox().check(rowNumbers);
		return this;
	}

	public FieldConfigDialog setColumnPermissions(boolean columnPermissions) {
		getColumnPermissionsCheckbox().check(columnPermissions);
		return this;
	}

	public CodebeamerLocator getLabelInput() {
		return this.locator("td.dataCell input[name='label']");
	}

	public CodebeamerLocator getHiddenCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getTypeSelector() {
		return this.locator("td.dataCell select[name='type']");
	}

	public CodebeamerLocator getSharedFieldSelector() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getListableCheckbox() {
		return this.locator("td.labelCell label input[name='listable']");
	}

	public CodebeamerLocator getTitleInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getDescriptionInput() {
		return this.locator("td.dataCell textarea[name='description']");
	}

	public CodebeamerLocator getMultipleCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getDataSourceSelector() {
		return this.locator("td.dataCell select[name='refType']");
	}

	public CodebeamerLocator getDependsOnSelector() {
		return this.locator("td.dataCell select[name='dependsOn']");
	}

	public CodebeamerLocator getUnionCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMemberSelector() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getFormattedCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getDigitsInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMinInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMaxInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getWidthInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getHeightInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getOmitSuspectedCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getOmitMergeCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getComputedAsInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getHideIfInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMandatoryIfInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMandatoryCheckbox() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMandatoryExceptSelector() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getServiceDeskLabelInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getServiceDeskDescriptionInput() {
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getYearCheckbox() {
		assertThat().assertType(FieldType.DATE);
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getMonthCheckbox() {
		assertThat().assertType(FieldType.DATE);
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getDayCheckbox() {
		assertThat().assertType(FieldType.DATE);
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getTimeCheckbox() {
		assertThat().assertType(FieldType.DATE);
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getRowNumbersCheckbox() {
		assertThat().assertType(FieldType.TABLE);
		// TODO
		return this.locator("");
	}

	public CodebeamerLocator getColumnPermissionsCheckbox() {
		assertThat().assertType(FieldType.TABLE);
		// TODO
		return this.locator("");
	}

	public void clickOk() {
		this.locator("div.ui-dialog-buttonset button:has-text('OK')").click();
	}

	public void clickCancel() {
		this.locator("div.ui-dialog-buttonset button.cancelButton").click();
	}
}
