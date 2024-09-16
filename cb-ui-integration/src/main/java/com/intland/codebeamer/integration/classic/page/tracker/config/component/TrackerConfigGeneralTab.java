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

import java.nio.file.Path;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.field.edit.common.CommonEditChoiceFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCheckboxFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditColorFieldComponent;
import com.intland.codebeamer.integration.classic.component.fileimport.FileImportFormComponent;

public class TrackerConfigGeneralTab extends AbstractTrackerConfigTab<TrackerConfigGeneralTab, TrackerConfigGeneralAssertions> {

	public TrackerConfigGeneralTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-general");
	}

	public TrackerConfigGeneralTab lock() {
		getLockButton().click();
		return this;
	}

	public TrackerConfigGeneralTab delete() {
		getDeleteButton().click();
		return this;
	}

	public TrackerConfigGeneralTab selectTemplate(String templateName) {
		getTemplateSelector().selectOption(templateName);
		return this;
	}

	public TrackerConfigGeneralTab setAvailableAsTemplate(boolean value) {
		getAvailableAsTemplateCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setName(String trackerName) {
		getNameField().fill(trackerName);
		return this;
	}

	public TrackerConfigGeneralTab setKeyName(String trackerKeyName) {
		getKeyNameField().fill(trackerKeyName);
		return this;
	}

	public TrackerConfigGeneralTab setColor(HtmlColor htmlColor) {
		getColorFieldComponent().fill(htmlColor);
		return this;
	}

	public TrackerConfigGeneralTab setBreadcrumbIcon(Path path) {
		getBreadcrumbIconFileField().addFile(path);
		return this;
	}

	public TrackerConfigGeneralTab setIcon(Path path) {
		getIconFileField().addFile(path);
		return this;
	}

	public TrackerConfigGeneralTab setDefaultLayoutToTable() {
		getDefaultLayoutSelector().selectOption("table");
		return this;
	}

	public TrackerConfigGeneralTab setDefaultLayoutToDocumentView() {
		getDefaultLayoutSelector().selectOption("document");
		return this;
	}

	public TrackerConfigGeneralTab setDefaultLayoutToTableEditDocumentView() {
		getDefaultLayoutSelector().selectOption("documentEdit");
		return this;
	}

	public TrackerConfigGeneralTab setDefaultLayoutToKanbanBoard() {
		getDefaultLayoutSelector().selectOption("cardboard");
		return this;
	}

	public TrackerConfigGeneralTab setDescription(String description) {
		getDescriptionField().fill(description);
		return this;
	}

	public TrackerConfigGeneralTab setWorkflowActive(boolean value) {
		getWorkflowCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setOnlyWorkflowActionCreatesReferringItems(boolean value) {
		getReferringItemsCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setUseQuickTransitions(boolean value) {
		getTransitionsCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setVisibility(boolean value) {
		getVisibilityCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab stOutlineShowAncestorItems(boolean value) {
		getOutlineShowAncestorItemsCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setOutlineShowDescendantItems(boolean value) {
		getOutlineShowDesendantItemsCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setWorkingSetShared(boolean value) {
		getWorkingSetSharedCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setItemCountVisibility(boolean value) {
		getItemCountVisibilityCheckbox().select(value);
		return this;
	}

	public TrackerConfigGeneralTab setReferenceVisibility(boolean value) {
		getReferenceVisibilityCheckbox().select(value);
		return this;
	}

	public CodebeamerLocator getDeleteButton() {
		return this.locator("#trackerEditorForm .actionBar input[name='DELETE']");
	}

	public CodebeamerLocator getLockButton() {
		return this.locator("#trackerEditorForm .actionBar input[name='LOCK']");
	}

	public CommonEditChoiceFieldComponent getTemplateSelector() {
		return new CommonEditChoiceFieldComponent(getCodebeamerPage(), "#templateTrackerId");
	}

	public EditCheckboxFieldComponent getAvailableAsTemplateCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "", "label[for='templateCheckbox']");
	}

	public CodebeamerLocator getNameField() {
		return this.locator("input[name='name']");
	}

	public CodebeamerLocator getKeyNameField() {
		return this.locator("input[name='keyName']");
	}

	public EditColorFieldComponent getColorFieldComponent() {
		return new EditColorFieldComponent(getCodebeamerPage(), "td:has(input#color)");
	}

	public FileImportFormComponent getBreadcrumbIconFileField() {
		return new FileImportFormComponent(getCodebeamerPage(), "#breadcrumbIconUploadConversationId_dropZone");
	}

	public FileImportFormComponent getCurrentBreadcrumbIcon() {
		return new FileImportFormComponent(getCodebeamerPage(), "label#currentIcon");
	}

	public FileImportFormComponent getIconFileField() {
		return new FileImportFormComponent(getCodebeamerPage(), "#trackerIconUploadConversationId_dropZone");
	}

	public CommonEditChoiceFieldComponent getDefaultLayoutSelector() {
		return new CommonEditChoiceFieldComponent(getCodebeamerPage(), "select[name='defaultLayout']");
	}

	public CodebeamerLocator getDescriptionField() {
		return this.locator("#editor");
	}

	public EditCheckboxFieldComponent getWorkflowCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='usingWorkflow']");
	}

	public EditCheckboxFieldComponent getReferringItemsCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='onlyWorkflowCanCreateNewReferringItem']");
	}

	public EditCheckboxFieldComponent getTransitionsCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='usingQuickTransitions']");
	}

	public EditCheckboxFieldComponent getVisibilityCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='visibility']");
	}

	public EditCheckboxFieldComponent getOutlineShowAncestorItemsCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='showAncestorItems']");
	}

	public EditCheckboxFieldComponent getOutlineShowDesendantItemsCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='showDescendantItems']");
	}

	public EditCheckboxFieldComponent getWorkingSetSharedCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='sharedInWorkingSet']");
	}

	public EditCheckboxFieldComponent getItemCountVisibilityCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='usingItemCountVisibility']");
	}

	public EditCheckboxFieldComponent getReferenceVisibilityCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "label[for='usingReferenceVisibility']");
	}

	@Override
	protected boolean waitForLoadingDialog() {
		return false;
	}

	@Override
	public TrackerConfigGeneralAssertions assertThat() {
		return new TrackerConfigGeneralAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-general-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#trackerEditorForm";
	}
}
