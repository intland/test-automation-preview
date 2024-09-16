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

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation.EscalationTableAssertions;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation.EscalationTableComponent;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation.EscalationViewFormDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class TrackerConfigEscalationTab
		extends AbstractTrackerConfigTab<TrackerConfigEscalationTab, TrackerConfigEscalationAssertions> {

	@Component("Escalation view form component")
	private final EscalationViewFormDialog escalationViewFormDialog;

	private final Tracker tracker;

	public TrackerConfigEscalationTab(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "#tracker-customize-escalation");
		this.escalationViewFormDialog = new EscalationViewFormDialog(codebeamerPage);
		this.tracker = tracker;
	}

	public TrackerConfigEscalationTab editEscalationRules(String escalationView, Consumer<EscalationTableComponent> consumer) {
		consumer.accept(new EscalationTableComponent(getCodebeamerPage(), escalationView, tracker));
		return this;
	}

	public TrackerConfigEscalationTab addEscalationRules(String escalationView,
			Consumer<EscalationViewFormDialog> escalationViewConsumer,
			Consumer<EscalationTableComponent> escalationTableConsumer) {
		this.getEscalationItemSelectorElement().selectOption("-NEW-");
		getNewEscalationRulesElement().click();
		escalationViewFormDialog.setName(escalationView);
		escalationViewConsumer.accept(escalationViewFormDialog);
		escalationTableConsumer.accept(new EscalationTableComponent(getCodebeamerPage(), escalationView, tracker));
		return this;
	}

	public TrackerConfigEscalationTab assertEscalationRules(String escalationView,
			Consumer<EscalationTableAssertions> assertions) {
		assertions.accept(new EscalationTableComponent(getCodebeamerPage(), escalationView, tracker).assertThat());
		return this;
	}

	@Override
	public TrackerConfigEscalationAssertions assertThat() {
		return new TrackerConfigEscalationAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-escalation-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#tracker-customize-escalation";
	}

	private CodebeamerLocator getNewEscalationRulesElement() {
		return this.locatorByTestId("newEscalationRulesButton");
	}

	private CodebeamerLocator getEscalationItemSelectorElement() {
		return this.locator("select.escalationItemsSelector");
	}
}
