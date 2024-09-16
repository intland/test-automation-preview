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

package com.intland.codebeamer.integration.api.service.tracker;

import java.util.List;

import com.intland.codebeamer.integration.api.builder.TrackerType;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.swagger.client.model.TrackerBasicInformation;

public class TrackerGeneralSettingsBuilder {

	private static final List<TrackerType> TYPES_WITH_MANDATORY_SHARED_WORKING_SET = List.of(TrackerType.BUG, TrackerType.CONTACT,
			TrackerType.DOCUMENT, TrackerType.ISSUE, TrackerType.PLATFORM, TrackerType.TEAM, TrackerType.TEST_CONF,
			TrackerType.TEST_RUN, TrackerType.WORKLOG);

	private TrackerBasicInformation trackerBasicInformation;

	public TrackerGeneralSettingsBuilder() {
		this.trackerBasicInformation = new TrackerBasicInformation();
	}

	public TrackerGeneralSettingsBuilder(TrackerBasicInformation trackerBasicInformation) {
		this.trackerBasicInformation = trackerBasicInformation;
	}

	public TrackerGeneralSettingsBuilder alwaysUseQuickTransitions() {
		validateWorkflowTransitionRelatedFields();

		trackerBasicInformation.setAlwaysUseQuickTransitions(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder color(HtmlColor color) {
		trackerBasicInformation.setColor(color.getHexCode());
		return this;
	}

	public TrackerGeneralSettingsBuilder defaultLayout(TrackerLayout trackerLayout) {
		trackerBasicInformation.setDefaultLayout(TrackerBasicInformation.DefaultLayoutEnum.fromValue(trackerLayout.name()));
		return this;
	}

	public TrackerGeneralSettingsBuilder description(String description) {
		trackerBasicInformation.setDescription(description);
		return this;
	}

	public TrackerGeneralSettingsBuilder inboxId(int inboxId) {
		trackerBasicInformation.setInboxId(Integer.valueOf(inboxId));
		return this;
	}

	public TrackerGeneralSettingsBuilder key(String key) {
		trackerBasicInformation.setKey(key);
		return this;
	}

	public TrackerGeneralSettingsBuilder hidden() {
		trackerBasicInformation.setHidden(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder visibleItemCounts() {
		trackerBasicInformation.setItemCountVisibility(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder locked() {
		trackerBasicInformation.setLocked(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder onlyWorkflowActionsCanCreateNewReferringItems() {
		validateWorkflowTransitionRelatedFields();

		trackerBasicInformation.setOnlyWorkflowActionsCanCreateNewReferringItems(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder visibleReferences() {
		trackerBasicInformation.setReferenceVisibility(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder sharedInWorkingSets() {
		return sharedInWorkingSets(true);
	}

	public TrackerGeneralSettingsBuilder sharedInWorkingSets(boolean sharedInWorkingSets) {
		TrackerType trackerType = TrackerType.fromId(trackerBasicInformation.getIssueTypeId());
		if (!sharedInWorkingSets && isSharedInWorkingSetMandatory(trackerType)) {
			throw new IllegalStateException("Cannot turn off shared in working set for tracker type: %s".formatted(trackerType));
		}

		trackerBasicInformation.setSharedInWorkingSets(Boolean.valueOf(sharedInWorkingSets));
		return this;
	}

	public TrackerGeneralSettingsBuilder showAncestorItems() {
		trackerBasicInformation.setShowAncestorItems(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder showDescendantItems() {
		trackerBasicInformation.setShowDescendantItems(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder template() {
		trackerBasicInformation.setTemplate(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder activateWorkflow() {
		validateWorkflowTransitionRelatedFields();

		trackerBasicInformation.setWorkflowIsActive(Boolean.TRUE);
		return this;
	}

	public TrackerGeneralSettingsBuilder templateId(Tracker tracker) {
		return templateId(tracker.id());
	}

	public TrackerGeneralSettingsBuilder templateId(TrackerId trackerId) {
		trackerBasicInformation.setTemplateId(Integer.valueOf(trackerId.id()));
		return this;
	}

	public TrackerBasicInformation build() {
		return trackerBasicInformation;
	}

	private void validateWorkflowTransitionRelatedFields() {
		TrackerType trackerType = TrackerType.fromId(trackerBasicInformation.getIssueTypeId());

		if (!isWorkflowAllowed(trackerType)) {
			throw new IllegalStateException("Cannot activate workflow related fields for tracker type: %s"
					.formatted(trackerType));
		}
	}

	/**
	 * State transitions are not allowed for some tracker types. It will cause issues if we set workflow to true on
	 * these trackers.
	 *
	 * @param type current type in the builder
	 * @return is workflow will be turned on or not
	 */
	private boolean isWorkflowAllowed(TrackerType type) {
		return !type.equals(TrackerType.AREA) && !type.equals(TrackerType.TEAM);
	}

	/**
	 * Some tracker types must always be shared amongs working sets.
	 *
	 * @param type current type in the builder
	 * @return whether the type must be shared amongst working sets
	 */
	private boolean isSharedInWorkingSetMandatory(TrackerType type) {
		return TYPES_WITH_MANDATORY_SHARED_WORKING_SET.contains(type);
	}
}
