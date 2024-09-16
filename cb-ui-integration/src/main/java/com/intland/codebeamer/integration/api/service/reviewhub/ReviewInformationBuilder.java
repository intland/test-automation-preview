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

import java.util.ArrayList;
import java.util.List;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ThresholdChangeOption;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewType;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroup;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.swagger.client.model.ReviewFindingSettings;
import com.intland.swagger.client.model.ReviewTypeRequest;

public class ReviewInformationBuilder {

	public final static String ARTIFACTTYPE_ENTITYID_SEPARATOR = "-";

	private ReviewTypeRequest reviewTypeRequest;

	private UserApiService userService;

	private RoleApiService roleApiService;

	private UserGroupApiService userGroupApiService;

	private List<String> reviewers = new ArrayList<>();

	private List<String> viewers = new ArrayList<>();

	private List<String> moderators = new ArrayList<>();

	public ReviewInformationBuilder(ReviewTypeRequest reviewTypeRequest, UserApiService userService,
			UserGroupApiService userGroupApiService, RoleApiService roleApiService) {
		this.reviewTypeRequest = reviewTypeRequest;
		this.userService = userService;
		this.userGroupApiService = userGroupApiService;
		this.roleApiService = roleApiService;
	}

	public ReviewInformationBuilder approvedStatusThreshold(int approvedStatusThreshold) {
		reviewTypeRequest.setApprovedStatusThreshold(approvedStatusThreshold);
		return this;
	}

	public ReviewInformationBuilder approvedThresholdChangeOption(ThresholdChangeOption approvedThresholdChangeOption) {
		reviewTypeRequest.setApprovedThresholdChangeOption(
				ReviewTypeRequest.ApprovedThresholdChangeOptionEnum.fromValue(approvedThresholdChangeOption.getValue()));
		return this;
	}

	public ReviewInformationBuilder baselineId(int baselineId) {
		reviewTypeRequest.setBaselineId(baselineId);
		return this;
	}

	public ReviewInformationBuilder branchMergeRequest() {
		reviewTypeRequest.setBranchMergeRequest(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder cbql(String cbql) {
		reviewTypeRequest.setCbql(cbql);
		return this;
	}

	public ReviewInformationBuilder cbqlId(String cbqlId) {
		reviewTypeRequest.setCbqlId(cbqlId);
		return this;
	}

	public ReviewInformationBuilder deadline(String deadline) {
		reviewTypeRequest.setDeadline(deadline);
		return this;
	}

	public ReviewInformationBuilder description(String description) {
		reviewTypeRequest.setDescription(description);
		return this;
	}

	public ReviewInformationBuilder fromBaselineDifference() {
		reviewTypeRequest.setFromBaselineDifference(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder mergeRequest() {
		reviewTypeRequest.setMergeRequest(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder minimumSignaturesRequired(int minimumSignaturesRequired) {
		reviewTypeRequest.setMinimumSignaturesRequired(minimumSignaturesRequired);
		return this;
	}

	public ReviewInformationBuilder notifyModerators() {
		reviewTypeRequest.setNotifyModerators(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder notifyOnItemUpdate() {
		reviewTypeRequest.setNotifyOnItemUpdate(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder notifyReviewers() {
		reviewTypeRequest.setNotifyReviewers(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder addProjectIdsItem(ProjectId projectId) {
		reviewTypeRequest.addProjectIdsItem(projectId.id());
		return this;
	}

	public ReviewInformationBuilder publicReview() {
		reviewTypeRequest.setPublicReview(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder recursive() {
		reviewTypeRequest.setRecursive(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder rejectedStatusThreshold(int rejectedStatusThreshold) {
		reviewTypeRequest.setRejectedStatusThreshold(rejectedStatusThreshold);
		return this;
	}

	public ReviewInformationBuilder rejectedThresholdChangeOption(ThresholdChangeOption rejectedThresholdChangeOption) {
		reviewTypeRequest.setRejectedThresholdChangeOption(
				ReviewTypeRequest.RejectedThresholdChangeOptionEnum.fromValue(rejectedThresholdChangeOption.getValue()));
		return this;
	}

	public ReviewInformationBuilder releaseIds(List<Integer> releaseIds) {
		reviewTypeRequest.setReleaseIds(releaseIds);
		return this;
	}

	public ReviewInformationBuilder requiresSignature() {
		reviewTypeRequest.setRequiresSignature(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder requiresSignatureFromReviewers() {
		reviewTypeRequest.setRequiresSignatureFromReviewers(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder reviewFindingSettings(ProjectId projectId, TrackerId trackerId,
			TrackerFieldId referenceFieldId) {
		reviewTypeRequest.setReviewFindingSettings(new ReviewFindingSettings()
				.trackerId(trackerId.id())
				.projectId(projectId.id())
				.referenceFieldId(referenceFieldId.id()));
		return this;
	}

	public ReviewInformationBuilder reviewType(ReviewType reviewType) {
		reviewTypeRequest.reviewType(ReviewTypeRequest.ReviewTypeEnum.fromValue(reviewType.getValue()));
		return this;
	}

	public ReviewInformationBuilder addUserAsReviewer(String userName) {
		this.reviewers.add(getUserString(userName));
		return this;
	}

	public ReviewInformationBuilder addGroupAsReviewer(String groupName) {
		this.reviewers.add(getGroupString(groupName));
		return this;
	}

	public ReviewInformationBuilder addRoleAsReviewer(String roleName) {
		this.reviewers.add(getRoleString(roleName));
		return this;

	}

	public ReviewInformationBuilder addUserAsModerator(String userName) {
		this.moderators.add(getUserString(userName));
		return this;
	}

	public ReviewInformationBuilder addGroupAsModerator(String groupName) {
		this.moderators.add(getGroupString(groupName));
		return this;
	}

	public ReviewInformationBuilder addRoleAsModerator(String roleName) {
		this.moderators.add(getRoleString(roleName));
		return this;
	}

	public ReviewInformationBuilder addUserAsViewer(String userName) {
		this.viewers.add(getUserString(userName));
		return this;
	}

	public ReviewInformationBuilder addGroupAsViewer(String groupName) {
		this.viewers.add(getGroupString(groupName));
		return this;
	}

	public ReviewInformationBuilder addRoleAsViewer(String roleName) {
		this.viewers.add(getRoleString(roleName));
		return this;
	}

	public ReviewInformationBuilder signWithUsernameAndPassword() {
		reviewTypeRequest.setSignWithUsernameAndPassword(Boolean.TRUE);
		return this;
	}

	public ReviewInformationBuilder addTrackerIdsItem(TrackerId trackerId) {
		reviewTypeRequest.addTrackerIdsItem(trackerId.id());
		return this;
	}

	public ReviewInformationBuilder addTrackerItemIdsItem(TrackerItemId trackerItemId) {
		reviewTypeRequest.addTrackerItemIdsItem(trackerItemId.id());
		return this;
	}

	public ReviewTypeRequest build() {
		reviewTypeRequest.reviewers(String.join(",", reviewers));
		reviewTypeRequest.moderators(String.join(",", moderators));
		reviewTypeRequest.viewers(String.join(",", viewers));
		return reviewTypeRequest;
	}

	private String getUserString(String userName) {
		User user = this.userService.findUserByName(userName);
		return user.getArtifactType().getValue() + ARTIFACTTYPE_ENTITYID_SEPARATOR + user.getId();
	}

	private String getRoleString(String roleName) {
		Role role = this.roleApiService.findRoleByName(roleName);
		return role.getArtifactType().getValue() + ARTIFACTTYPE_ENTITYID_SEPARATOR + role.getId();
	}

	private String getGroupString(String groupName) {
		UserGroup userGroup = userGroupApiService.findUserGroupByName(groupName);
		return ArtifactType.ARTIFACT + ARTIFACTTYPE_ENTITYID_SEPARATOR + userGroup.id().id();
	}
}
