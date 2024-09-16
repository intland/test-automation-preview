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

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.statetransition.StateTransitionComponent;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.statetransition.StateTransitionDialogComponent;

public class TrackerConfigStateTransitionsTab extends AbstractTrackerConfigTab<TrackerConfigStateTransitionsTab, TrackerConfigStateTransitionsAssertions> {

	public TrackerConfigStateTransitionsTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-transitions");
	}

	@Override
	public TrackerConfigStateTransitionsAssertions assertThat() {
		return new TrackerConfigStateTransitionsAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-transitions-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#adminTransitionPermissionForm";
	}

	public StateTransitionComponent stateTransition(String from, String to) {
		return new StateTransitionComponent(getCodebeamerPage(), from, to);
	}

	public StateTransitionDialogComponent openNewStateTransitionDialog() {
		this.locator("div.moreStateTransitions .eventSelector").selectOption("0");
		return getStateTransitionDialogComponent();
	}

	public TrackerConfigStateTransitionsTab newStateTransitionDialog(Consumer<StateTransitionDialogComponent> formConsumer) {
		formConsumer.accept(getStateTransitionDialogComponent());
		return this;
	}

	private StateTransitionDialogComponent getStateTransitionDialogComponent() {
		return new StateTransitionDialogComponent(getCodebeamerPage(), ".ui-dialog");
	}
}
