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

package com.intland.codebeamer.integration.api.service.project;

public enum ProjectTemplate {

	DEFAULT_PROJECT("Default-Project"),
	AGILE_SCRUM("Agile-Scrum"),
	AGILE_WATERFALL_HYBRID("Agile-Waterfall-Hybrid");

	private final String templateName;

	ProjectTemplate(final String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}
}
