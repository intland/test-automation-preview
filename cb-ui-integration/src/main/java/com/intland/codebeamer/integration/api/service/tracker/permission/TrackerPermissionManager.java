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

package com.intland.codebeamer.integration.api.service.tracker.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerPermissionApi;
import com.intland.swagger.client.model.PermissionIdsRequest;
import com.intland.swagger.client.model.RoleReference;
import com.intland.swagger.client.model.RoleWithPermissions;
import com.intland.swagger.client.model.TrackerPermissionReference;

public class TrackerPermissionManager {

	private final TrackerPermissionApi trackerPermissionApi;

	public TrackerPermissionManager(TrackerPermissionApi trackerPermissionApi) {
		this.trackerPermissionApi = trackerPermissionApi;
	}

	public void updateTrackerPermissions(TrackerId trackerId,
			Function<TrackerPermissionBuilder, TrackerPermissionBuilder> permBuilder) {
		Map<String, Set<PermissionWithOp>> input = permBuilder.apply(new TrackerPermissionBuilder()).build();

		List<RoleWithPermissions> existingTrackerPermissions = getTrackerPermissions(trackerId);

		for (Map.Entry<String, Set<PermissionWithOp>> entry : input.entrySet()) {
			String roleName = entry.getKey();
			Set<PermissionWithOp> permissionChanges = entry.getValue();

			List<TrackerPermissionReference> permissionsToAdd = collectPermissonsToAdd(permissionChanges);
			List<Integer> permissionsToRevoke = collectPermissionsToRevoke(permissionChanges);

			RoleWithPermissions existingRoleWithPermissions = existingTrackerPermissions.stream()
					.filter(p -> roleName.equalsIgnoreCase(p.getRole().getName()))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("Role ('%s') does not exist in project!".formatted(roleName)));

			List<TrackerPermissionReference> trackerPermissions = new ArrayList<>(existingRoleWithPermissions
					.getTrackerPermissions());

			trackerPermissions.addAll(permissionsToAdd);
			trackerPermissions.removeIf(i -> permissionsToRevoke.contains(i.getId()));

			updatePermissionsOfRole(trackerId, existingRoleWithPermissions.getRole(), trackerPermissions);
		}

	}

	private List<TrackerPermissionReference> collectPermissonsToAdd(Set<PermissionWithOp> entry) {
		return entry.stream()
				.filter(pr -> PermissionOperation.ADD.equals(pr.operation()))
				.map(this::mapTrackerPermissionReference)
				.toList();
	}

	private TrackerPermissionReference mapTrackerPermissionReference(PermissionWithOp permissionWithOp) {
		TrackerPermissionReference trackerPermissionReference = new TrackerPermissionReference();
		trackerPermissionReference.setId(permissionWithOp.permission().ordinal());
		trackerPermissionReference.setName(permissionWithOp.permission().name().toLowerCase());

		return trackerPermissionReference;
	}

	private List<Integer> collectPermissionsToRevoke(Set<PermissionWithOp> value) {
		return value.stream()
				.filter(p -> PermissionOperation.REVOKE.equals(p.operation()))
				.map(PermissionWithOp::permission)
				.map(Enum::ordinal)
				.toList();
	}

	private void updatePermissionsOfRole(TrackerId trackerId, RoleReference role,
			List<TrackerPermissionReference> trackerPermissions) {
		PermissionIdsRequest permissionIdsRequest = new PermissionIdsRequest();
		permissionIdsRequest.setPermissions(trackerPermissions);

		try {
			trackerPermissionApi.updatePermission(trackerId.id(), role.getId(), permissionIdsRequest);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private List<RoleWithPermissions> getTrackerPermissions(TrackerId trackerId) {
		try {
			return trackerPermissionApi.getTrackerPermissionsWithRoles(trackerId.id(), null, null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
