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

import java.util.List;

import com.intland.codebeamer.integration.classic.page.trackeritem.importer.enums.ImportRuleConditionType;

public class MsWordImportCondition {

	private final ImportRuleConditionType conditionType;

	private List<String> selectedStyles;

	private String conditionText;

	public MsWordImportCondition(ImportRuleConditionType conditionType, List<String> selectedStyles) {
		this.conditionType = conditionType;
		this.selectedStyles = selectedStyles;
	}

	public MsWordImportCondition(ImportRuleConditionType conditionType, String conditionText) {
		this.conditionType = conditionType;
		this.conditionText = conditionText;
	}

	public MsWordImportCondition(ImportRuleConditionType conditionType) {
		this.conditionType = conditionType;
	}

	public ImportRuleConditionType getConditionType() {
		return conditionType;
	}

	public List<String> getSelectedStyles() {
		return selectedStyles;
	}

	public String getConditionText() {
		return conditionText;
	}
}
