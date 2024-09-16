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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditCheckboxFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.form.EditRoleAndMemberSelectorComponent;
import com.intland.codebeamer.integration.classic.component.model.MemberReference;

public class TrackerConfigNotificationsTab extends AbstractTrackerConfigTab<TrackerConfigNotificationsTab, TrackerConfigNotificationsAssertions> {

	public TrackerConfigNotificationsTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-notification");
	}

	@Override
	public TrackerConfigNotificationsAssertions assertThat() {
		return new TrackerConfigNotificationsAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-notification-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#trackerItemSubscriptionForm";
	}

	public TrackerConfigNotificationsTab setAnyNotificationField(MemberReference memberReference) {
		new EditRoleAndMemberSelectorComponent(getCodebeamerPage(), "h2:has-text('Any notifications') + table").selectMember(
				memberReference);
		return this;
	}

	public TrackerConfigNotificationsTab setStatusChangeNotification(MemberReference memberReference) {
		new EditRoleAndMemberSelectorComponent(getCodebeamerPage(),
				"h2:has-text('Status change notifications') + table").selectMember(memberReference);
		return this;
	}

	public TrackerConfigNotificationsTab setSubscriberForStatus(String statusName, MemberReference memberReference) {
		new EditRoleAndMemberSelectorComponent(getCodebeamerPage(),
				"td.status:has(span.issueStatus:has-text('%s')) + td.subscribers".formatted(statusName)).selectMember(
				memberReference);
		return this;
	}

	public TrackerConfigNotificationsTab setOnlyMemberForAnyNotificationCheckbox(boolean value) {
		getOnlyMemberForAnyNotificationCheckbox().select(value);
		return this;
	}

	public TrackerConfigNotificationsTab setOnlyMemberStatusChangeNotificationCheckbox(boolean value) {
		getOnlyMemberForStatusChangeNotificationCheckbox().select(value);
		return this;
	}

	public TrackerConfigNotificationsTab setOnlyMemberForStatusCheckbox(String statusName, boolean value) {
		getOnlyMemberForStatusCheckbox(statusName).select(value);
		return this;
	}

	public EditRoleAndMemberSelectorComponent getAnyNotificationField() {
		return new EditRoleAndMemberSelectorComponent(getCodebeamerPage(), "h2:has-text('Any notifications') + table");
	}

	public EditRoleAndMemberSelectorComponent getStatusChangeNotificationField() {
		return new EditRoleAndMemberSelectorComponent(getCodebeamerPage(), "h2:has-text('Status change notifications') + table");
	}

	public EditRoleAndMemberSelectorComponent getSubscriberForStatusField(String statusName) {
		return new EditRoleAndMemberSelectorComponent(getCodebeamerPage(),
				"td.status:has(span.issueStatus:has-text('%s')) + td.subscribers".formatted(statusName));
	}

	public EditCheckboxFieldComponent getOnlyMemberForAnyNotificationCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(), "h2:has-text('Any notifications') + table td.onlyMembers");
	}

	public EditCheckboxFieldComponent getOnlyMemberForStatusChangeNotificationCheckbox() {
		return new EditCheckboxFieldComponent(getCodebeamerPage(),
				"h2:has-text('Status change notifications') + table td.onlyMembers");
	}

	public EditCheckboxFieldComponent getOnlyMemberForStatusCheckbox(String statusName) {
		return new EditCheckboxFieldComponent(getCodebeamerPage(),
				"td.status:has(span.issueStatus:has-text('%s')) ~ td.onlyMembers".formatted(statusName));
	}

}
