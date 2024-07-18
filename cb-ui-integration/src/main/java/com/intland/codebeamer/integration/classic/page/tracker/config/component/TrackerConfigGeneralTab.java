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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

public class TrackerConfigGeneralTab extends AbstractTrackerConfigTab<TrackerConfigGeneralTab, TrackerConfigGeneralAssertions> {

	public TrackerConfigGeneralTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-general");
	}

	public TrackerConfigGeneralTab fillInputName(String trackerName) {
		getNameField().fill(trackerName);
		return this;
	}

	public CodebeamerLocator getDeleteButton() {
		return this.locator("#trackerEditorForm .actionBar input[name='DELETE']");
	}

	public CodebeamerLocator getLockButton() {
		return this.locator("#trackerEditorForm .actionBar input[name='LOCK']");
	}

	public CodebeamerLocator getNameField() {
		return this.locator("#trackerEditorForm div.contentWithMargins table.formTableWithSpacing input[name='name']");
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
