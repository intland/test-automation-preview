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

public class TrackerConfigAuditTrailTab extends
		AbstractTrackerConfigTab<TrackerConfigAuditTrailTab, TrackerConfigAuditTrailAssertions> {

	private static final String SAVE_BUTTON_DOES_NOT_EXIST = "Save button does not exist on Audit Trail tab!";

	private static final String CANCEL_BUTTON_DOES_NOT_EXIST = "Cancel button does not exist on Audit Trail tab!";

	public TrackerConfigAuditTrailTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-history");
	}

	@Override
	public TrackerConfigAuditTrailTab saveTrackerConfig() {
		throw new UnsupportedOperationException(SAVE_BUTTON_DOES_NOT_EXIST);
	}

	@Override
	public TrackerConfigAuditTrailTab cancelTrackerConfig() {
		throw new UnsupportedOperationException(CANCEL_BUTTON_DOES_NOT_EXIST);
	}

	@Override
	public CodebeamerLocator getSaveButton() {
		throw new UnsupportedOperationException(SAVE_BUTTON_DOES_NOT_EXIST);
	}

	@Override
	public CodebeamerLocator getCancelButton() {
		throw new UnsupportedOperationException(CANCEL_BUTTON_DOES_NOT_EXIST);
	}

	@Override
	public TrackerConfigAuditTrailAssertions assertThat() {
		return new TrackerConfigAuditTrailAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-history-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#auditTrailLazyLoad";
	}
}
