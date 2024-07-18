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

package com.intland.codebeamer.integration.classic.page.tracker.config;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigAuditTrailTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigEscalationTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigFieldsTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigGeneralTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigNotificationsTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigPermissionsTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigServiceDeskTab;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.TrackerConfigStateTransitionsTab;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public class TrackerConfigurationPage extends AbstractCodebeamerPage<TrackerConfigurationPage> {

	private static final String TRACKER_PAGE = "/proj/tracker/configuration.spr?tracker_id=%s";

	private Tracker tracker;

	private OverlayMessageBoxComponent overlayMessageBoxComponent;

	private TrackerConfigGeneralTab trackerConfigGeneralTab;

	private TrackerConfigPermissionsTab trackerConfigPermissionsTab;

	private TrackerConfigStateTransitionsTab trackerConfigStateTransitionsTab;

	private TrackerConfigFieldsTab trackerConfigFieldsTab;

	private TrackerConfigEscalationTab trackerConfigEscalationTab;

	private TrackerConfigNotificationsTab trackerConfigNotificationsTab;

	private TrackerConfigAuditTrailTab trackerConfigAuditTrailTab;

	private TrackerConfigServiceDeskTab trackerConfigServiceDeskTab;

	public TrackerConfigurationPage(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage);
		this.tracker = tracker;
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.trackerConfigGeneralTab = new TrackerConfigGeneralTab(getCodebeamerPage());
		this.trackerConfigPermissionsTab = new TrackerConfigPermissionsTab(getCodebeamerPage());
		this.trackerConfigStateTransitionsTab = new TrackerConfigStateTransitionsTab(getCodebeamerPage());
		this.trackerConfigFieldsTab = new TrackerConfigFieldsTab(getCodebeamerPage());
		this.trackerConfigEscalationTab = new TrackerConfigEscalationTab(getCodebeamerPage());
		this.trackerConfigNotificationsTab = new TrackerConfigNotificationsTab(getCodebeamerPage());
		this.trackerConfigAuditTrailTab = new TrackerConfigAuditTrailTab(getCodebeamerPage());
		this.trackerConfigServiceDeskTab = new TrackerConfigServiceDeskTab(getCodebeamerPage());
	}

	public TrackerConfigurationPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public OverlayMessageBoxComponent getOverlayMessageBoxComponent() {
		return overlayMessageBoxComponent;
	}

	public TrackerConfigurationPage changeToTrackerConfigGeneralTab() {
		trackerConfigGeneralTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigGeneralTab(Consumer<TrackerConfigGeneralTab> formConsumer) {
		formConsumer.accept(trackerConfigGeneralTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigPermissionsTab() {
		trackerConfigPermissionsTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigPermissionsTab(Consumer<TrackerConfigPermissionsTab> formConsumer) {
		formConsumer.accept(trackerConfigPermissionsTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigStateTransitionsTab() {
		trackerConfigStateTransitionsTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigStateTransitionsTab(Consumer<TrackerConfigStateTransitionsTab> formConsumer) {
		formConsumer.accept(trackerConfigStateTransitionsTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigFieldsTab() {
		trackerConfigFieldsTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigFieldsTab(Consumer<TrackerConfigFieldsTab> formConsumer) {
		formConsumer.accept(trackerConfigFieldsTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigEscalationTab() {
		trackerConfigEscalationTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigEscalationTab(Consumer<TrackerConfigEscalationTab> formConsumer) {
		formConsumer.accept(trackerConfigEscalationTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigNotificationsTab() {
		trackerConfigNotificationsTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigNotificationsTab(Consumer<TrackerConfigNotificationsTab> formConsumer) {
		formConsumer.accept(trackerConfigNotificationsTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigAuditTrailTab() {
		trackerConfigAuditTrailTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigAuditTrailTab(Consumer<TrackerConfigAuditTrailTab> formConsumer) {
		formConsumer.accept(trackerConfigAuditTrailTab);
		return this;
	}

	public TrackerConfigurationPage changeToTrackerConfigServiceDeskTab() {
		trackerConfigServiceDeskTab.activateTab();
		return this;
	}

	public TrackerConfigurationPage trackerConfigServiceDeskTab(Consumer<TrackerConfigServiceDeskTab> formConsumer) {
		formConsumer.accept(trackerConfigServiceDeskTab);
		return this;
	}

	@Action("Visit")
	public TrackerConfigurationPage visit() {
		navigate(formatPageUrl());
		return isActive();
	}

	@Override
	public TrackerConfigurationPage isActive() {
		assertUrl(formatPageUrl(), "Tracker config page should be the active page");
		return this;
	}

	private String formatPageUrl() {
		return TRACKER_PAGE.formatted(Integer.valueOf(tracker.id().id()));
	}
}
