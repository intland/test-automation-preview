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

package com.intland.codebeamer.integration.api.service.reviewhub;

import java.util.Date;
import java.util.Optional;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ThresholdChangeOption;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.swagger.client.model.Member;
import com.intland.swagger.client.model.NotificationSettings;
import com.intland.swagger.client.model.Project;
import com.intland.swagger.client.model.ReviewFindingSettings;
import com.intland.swagger.client.model.ReviewTemplate;
import com.intland.swagger.client.model.SignatureSettings;

public class CreateReviewTemplateBuilder {

	private ReviewTemplate reviewTemplate;

	private UserApiService userService;

	private UserGroupApiService userGroupApiService;

	private RoleApiService roleApiService;

	public CreateReviewTemplateBuilder(ProjectId project, String templateName,
			UserApiService userService, UserGroupApiService userGroupApiService, RoleApiService roleApiService) {
		this.reviewTemplate = createReviewTemplate(project, templateName);
		this.userService = userService;
		this.userGroupApiService = userGroupApiService;
		this.roleApiService = roleApiService;
	}

	public CreateReviewTemplateBuilder approvedStatusThreshold(int approvedStatusThreshold) {
		reviewTemplate.setApprovedStatusThreshold(approvedStatusThreshold);
		return this;
	}

	public CreateReviewTemplateBuilder approvedThresholdChangeOption(
			ThresholdChangeOption approvedThresholdChangeOption) {
		reviewTemplate.setApprovedThresholdChangeOption(
				ReviewTemplate.ApprovedThresholdChangeOptionEnum.fromValue(approvedThresholdChangeOption.getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder createdBy(User createdBy) {
		reviewTemplate.setCreatedBy(new Member().id(createdBy.getId()));
		return this;
	}

	public CreateReviewTemplateBuilder deadline(Date deadline) {
		reviewTemplate.setDeadline(deadline);
		return this;
	}

	public CreateReviewTemplateBuilder description(String description) {
		reviewTemplate.setDescription(description);
		return this;
	}

	public CreateReviewTemplateBuilder id(int id) {
		reviewTemplate.setId(id);
		return this;
	}

	public CreateReviewTemplateBuilder notifyReviewers(boolean notifyReviewers) {
		reviewTemplate.setNotificationSettings(createNotificationSettings().notifyReviewers(notifyReviewers));
		return this;
	}

	public CreateReviewTemplateBuilder notifyModerators(boolean notifyModerators) {
		reviewTemplate.setNotificationSettings(createNotificationSettings().notifyModerators(notifyModerators));
		return this;
	}

	public CreateReviewTemplateBuilder notifyOnItemUpdate(boolean notifyOnItemUpdate) {
		reviewTemplate.setNotificationSettings(createNotificationSettings().notifyOnItemUpdate(notifyOnItemUpdate));
		return this;
	}

	public CreateReviewTemplateBuilder rejectedStatusThreshold(int rejectedStatusThreshold) {
		reviewTemplate.setRejectedStatusThreshold(rejectedStatusThreshold);
		return this;
	}

	public CreateReviewTemplateBuilder rejectedThresholdChangeOption(
			ThresholdChangeOption rejectedThresholdChangeOption) {
		reviewTemplate.setRejectedThresholdChangeOption(
				ReviewTemplate.RejectedThresholdChangeOptionEnum.fromValue(rejectedThresholdChangeOption.getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder reviewFindingSettings(ProjectId projectId, TrackerId trackerId,
			TrackerFieldId referenceFieldId) {
		reviewTemplate.setReviewFindingSettings(new ReviewFindingSettings()
				.trackerId(trackerId.id())
				.projectId(projectId.id())
				.referenceFieldId(referenceFieldId.id()));
		return this;
	}

	public CreateReviewTemplateBuilder minimumSignaturesRequired(int minimumSignaturesRequired) {
		reviewTemplate.setSignatureSettings(createSignatureSettings().minimumSignaturesRequired(minimumSignaturesRequired));
		return this;
	}

	public CreateReviewTemplateBuilder necessaryBaselineSignature(boolean necessaryBaselineSignature) {
		reviewTemplate.setSignatureSettings(createSignatureSettings().necessaryBaselineSignature(necessaryBaselineSignature));
		return this;
	}

	public CreateReviewTemplateBuilder requiresSignatureFromReviewers(boolean requiresSignatureFromReviewers) {
		reviewTemplate.setSignatureSettings(
				createSignatureSettings().requiresSignatureFromReviewers(requiresSignatureFromReviewers));
		return this;
	}

	public CreateReviewTemplateBuilder templateName(String templateName) {
		reviewTemplate.setTemplateName(templateName);
		return this;
	}

	public CreateReviewTemplateBuilder addUserAsReviewer(String userName) {
		User user = userService.findUserByName(userName);
		reviewTemplate.addReviewersItem(new Member().id(user.getId()).groupType(user.getArtifactType().getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder addGroupAsReviewer(String groupName) {
		UserGroup userGroup = userGroupApiService.findUserGroupByName(groupName);
		reviewTemplate.addReviewersItem(new Member().id(userGroup.id().id()).groupType(ArtifactType.ARTIFACT.getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder addRoleAsReviewer(String roleName) {
		Role role = roleApiService.findRoleByName(roleName);
		reviewTemplate.addReviewersItem(new Member().id(role.getId()));
		return this;
	}

	public CreateReviewTemplateBuilder addUserAsModerator(String userName) {
		User user = userService.findUserByName(userName);
		reviewTemplate.addModeratorsItem(new Member().id(user.getId()).groupType(user.getArtifactType().getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder addGroupAsModerator(String groupName) {
		UserGroup userGroup = userGroupApiService.findUserGroupByName(groupName);
		reviewTemplate.addModeratorsItem(new Member().id(userGroup.id().id()).groupType(ArtifactType.ARTIFACT.getValue()));
		return this;
	}

	public CreateReviewTemplateBuilder addRoleAsModerator(String roleName) {
		Role role = roleApiService.findRoleByName(roleName);
		reviewTemplate.addModeratorsItem(new Member().id(role.getId()));
		return this;
	}

	public ReviewTemplate build() {
		return this.reviewTemplate;
	}

	private NotificationSettings createNotificationSettings() {
		return Optional.ofNullable(reviewTemplate.getNotificationSettings())
				.orElse(new NotificationSettings());
	}

	private SignatureSettings createSignatureSettings() {
		return Optional.ofNullable(reviewTemplate.getSignatureSettings())
				.orElse(new SignatureSettings());
	}

	private ReviewTemplate createReviewTemplate(ProjectId project, String templateName) {
		ReviewTemplate reviewTemplate = new ReviewTemplate();
		reviewTemplate.setProject(new Project().id(Integer.valueOf(project.id())));
		reviewTemplate.setTemplateName(templateName);
		return reviewTemplate;
	}
}
