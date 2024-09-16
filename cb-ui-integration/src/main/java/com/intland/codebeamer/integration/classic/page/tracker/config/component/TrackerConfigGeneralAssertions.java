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

import com.intland.codebeamer.integration.api.service.utility.DocumentFileInfo;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;

public class TrackerConfigGeneralAssertions
		extends AbstractTrackerConfigAssertions<TrackerConfigGeneralTab, TrackerConfigGeneralAssertions> {

	public static final String THE_CHECKBOX_VALUE_SHOULD_BE = "The checkbox value should be %s";

	protected TrackerConfigGeneralAssertions(TrackerConfigGeneralTab component) {
		super(component);
	}

	public TrackerConfigGeneralAssertions nameEqualsTo(String trackerName) {
		return assertAll("Tracker name should be: %s".formatted(trackerName),
				() -> assertThat(getComponent().getNameField()).hasValue(trackerName));
	}

	public TrackerConfigGeneralAssertions keyNameEqualsTo(String trackerKey) {
		return assertAll("Tracker key name should be: %s".formatted(trackerKey),
				() -> assertThat(getComponent().getKeyNameField()).hasValue(trackerKey));
	}

	public TrackerConfigGeneralAssertions descriptionEqualsTo(String description) {
		return assertAll("Tracker description should be: %s".formatted(description),
				() -> assertThat(getComponent().getDescriptionField()).hasValue(description));
	}

	public TrackerConfigGeneralAssertions assertAvailableAsTemplate(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getAvailableAsTemplateCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertWorkflowCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getWorkflowCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertReferringItemsCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getReferringItemsCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertTransitionsCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getTransitionsCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertVisibilityCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getVisibilityCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertOutlineShowAncestorItemsCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getOutlineShowAncestorItemsCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertOutlineShowDesendantItemsCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getOutlineShowDesendantItemsCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertWorkingSetSharedCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getWorkingSetSharedCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertItemCountVisibilityCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getItemCountVisibilityCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions assertReferenceVisibilityCheckbox(boolean value) {
		return assertAll(THE_CHECKBOX_VALUE_SHOULD_BE.formatted(value),
				() -> getComponent().getReferenceVisibilityCheckbox().assertThat().is(value));
	}

	public TrackerConfigGeneralAssertions referringItemsCheckboxIsDisabled() {
		return assertAll("Referring Items checkbox should be disabled",
				() -> getComponent().getReferringItemsCheckbox().assertThat().isDisabled());
	}

	public TrackerConfigGeneralAssertions referringItemsCheckboxIsEnabled() {
		return assertAll("Referring Items checkbox should be enabled",
				() -> getComponent().getReferringItemsCheckbox().assertThat().isEnabled());
	}

	public TrackerConfigGeneralAssertions transitionsCheckboxIsDisabled() {
		return assertAll("Transitions checkbox should be disabled",
				() -> getComponent().getTransitionsCheckbox().assertThat().isDisabled());
	}

	public TrackerConfigGeneralAssertions transitionsCheckboxIsEnabled() {
		return assertAll("Transitions checkbox should be enabled",
				() -> getComponent().getTransitionsCheckbox().assertThat().isEnabled());
	}

	public TrackerConfigGeneralAssertions assertColor(HtmlColor color) {
		return assertAll("Transitions checkbox should be %s".formatted(color),
				() -> getComponent().getColorFieldComponent().assertThat().is(color));
	}

	public TrackerConfigGeneralAssertions assertDefaultLayoutIsTable() {
		return assertAll("Default layout should be set to Table",
				() -> getComponent().getDefaultLayoutSelector().assertThat().is("table"));
	}

	public TrackerConfigGeneralAssertions assertDefaultLayoutIsDocumentView() {
		return assertAll("Default layout should be set to DocumentView",
				() -> getComponent().getDefaultLayoutSelector().assertThat().is("document"));
	}

	public TrackerConfigGeneralAssertions assertDefaultLayoutIsDocumentEdit() {
		return assertAll("Default layout should be set to DocumentEdit",
				() -> getComponent().getDefaultLayoutSelector().assertThat().is("documentEdit"));
	}

	public TrackerConfigGeneralAssertions assertDefaultLayoutIsKanbanBoard() {
		return assertAll("Default layout should be set to KanbanBoard",
				() -> getComponent().getDefaultLayoutSelector().assertThat().is("cardboard"));
	}

	public TrackerConfigGeneralAssertions assertBreadcrumbsIconUploaded(DocumentFileInfo sourceIcon) {
		return assertAll("For Breadcrumbs Icon a file should be uploaded",
				() -> getComponent().getCurrentBreadcrumbIcon().assertThat().isUploadedIconEquals(sourceIcon));
	}

}
