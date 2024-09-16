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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.multiselect.MultiSelectMenuComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.ImportRuleConditionType;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class MsWordRightPaneFormComponent
		extends AbstractCodebeamerComponent<MsWordRightPaneFormComponent, MsWordRightPaneFormAssertions> {

	private static final Logger logger = LogManager.getLogger(MsWordRightPaneFormComponent.class);

	@Component(value = "Multiselect", includeInSitemap = false)
	private final MultiSelectMenuComponent multiSelectMenuComponent;

	@Component("Confirmation dialog")
	private final ConfirmationDialogComponent confirmationDialogComponent;

	public MsWordRightPaneFormComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".ui-layout-east");
		this.multiSelectMenuComponent = new MultiSelectMenuComponent(codebeamerPage);
		this.confirmationDialogComponent = new ConfirmationDialogComponent(codebeamerPage);
	}

	@Override
	public MsWordRightPaneFormAssertions assertThat() {
		return new MsWordRightPaneFormAssertions(this);
	}

	public MsWordRightPaneFormComponent save() {
		this.getSaveButtonLocator().click();
		return this;
	}

	public MsWordRightPaneFormComponent preview() {
		this.getPreviewButtonLocator().click();
		return this;
	}

	public MsWordRightPaneFormComponent cancel() {
		this.getCancelButtonLocator().click();
		return this;
	}

	public MsWordRightPaneFormComponent addNewRule(MsWordImportRule importRule) {
		this.getAddNewRuleButtonLocator().click();
		addRuleTargetTracker(importRule);
		addRuleScope(importRule);
		addBooleanOperator(importRule);
		addConditions(importRule);
		return this;
	}

	public MsWordRightPaneFormComponent confirmDialogYes() {
		confirmationDialogComponent.yes();
		return this;
	}

	public MsWordRightPaneFormComponent confirmDialogCancel() {
		confirmationDialogComponent.cancel();
		return this;
	}

	public CodebeamerLocator getStatisticRowById(int id) {
		return this.locator("table#statisticsBox tr[data-action-id='%d'] span".formatted(Integer.valueOf(id)));
	}

	private void addConditions(MsWordImportRule importRule) {
		if (importRule.msWordImportConditions() == null || importRule.msWordImportConditions().isEmpty()) {
			logger.warn("Import rule conditions are not set, using default value: 'has the following style: All Styles'");
			return;
		}

		boolean addNewCondition = false;
		for (MsWordImportCondition importCondition : importRule.msWordImportConditions()) {
			if (addNewCondition) {
				getLastRuleLocator().concat("a.addNewCondition").click();
			} else {
				addNewCondition = true;
			}
			selectConditionType(importCondition);
			handleConditionText(importCondition);
		}
	}

	private void selectConditionType(MsWordImportCondition importCondition) {
		this.locator("div.rule select[name=conditionType] >> nth=-1").selectOption(
				String.valueOf(importCondition.getConditionType().getValue()));
	}

	private void handleConditionText(MsWordImportCondition importCondition) {
		if (importCondition.getConditionType() == ImportRuleConditionType.HAS_STYLE
				&& importCondition.getSelectedStyles() != null) {
			getLastConditionTextButtonLocator().click();
			this.multiSelectMenuComponent
					.uncheckAll()
					.select(importCondition.getSelectedStyles());
			getLastConditionTextButtonLocator().click();
		} else if (importCondition.getConditionType() != ImportRuleConditionType.NO_CONDITION) {
			this.locator("div.rule td.conditionText input >> nth=-1").fill(importCondition.getConditionText());
		}
	}

	private void addBooleanOperator(MsWordImportRule importRule) {
		getLastRuleLocator().concat("div.allConditionContainer select")
				.selectOption(importRule.allConditionsAreMet() ? "1" : "2");
	}

	private void addRuleScope(MsWordImportRule importRule) {
		if (importRule.containers() != null) {
			getRuleScopeButtonLocator().click();
			this.multiSelectMenuComponent.uncheckAll();
			this.multiSelectMenuComponent.select(importRule.containers());
			getRuleScopeButtonLocator().click();
		} else {
			logger.warn("Rule container not set, using default value: 'the whole Document'");
		}
	}

	private void addRuleTargetTracker(MsWordImportRule importRule) {
		getLastRuleLocator().concat("select.styleAction.styleActionSelector").selectOption(
				String.valueOf(importRule.targetTrackerId()));
	}

	private CodebeamerLocator getRuleScopeButtonLocator() {
		return this.locator("div.rule div.branchContainer button >> nth=-1");
	}

	private CodebeamerLocator getLastConditionTextButtonLocator() {
		return this.locator("div.rule td.conditionText button >> nth=-1");
	}

	private CodebeamerLocator getLastRuleLocator() {
		return this.locator("div.rule >> nth=-1");
	}

	private CodebeamerLocator getAddNewRuleButtonLocator() {
		return this.locator("#addNewRuleButton");
	}

	private CodebeamerLocator getPreviewButtonLocator() {
		return this.locator("#previewButton");
	}

	private CodebeamerLocator getSaveButtonLocator() {
		return this.locator("#nextButton");
	}

	private CodebeamerLocator getCancelButtonLocator() {
		return this.locator("input[name=_eventId_back]");
	}
}
