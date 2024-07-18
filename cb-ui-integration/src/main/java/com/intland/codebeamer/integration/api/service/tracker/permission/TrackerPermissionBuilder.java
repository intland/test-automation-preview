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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrackerPermissionBuilder {

	private Map<String, Set<PermissionWithOp>> permissionsHolder = new HashMap<>();

	public TrackerPermissionBuilder addPermissionForRole(String roleName, TrackerPermission... permissionsToAdd) {
		addPermissionForRole(roleName, Arrays.stream(permissionsToAdd).toList());

		return this;
	}

	public TrackerPermissionBuilder addPermissionForRole(String roleName, List<TrackerPermission> permissionsToAdd) {
		Set<PermissionWithOp> permissions = convertInputPermissions(PermissionOperation.ADD, permissionsToAdd);

		permissionsHolder.computeIfAbsent(roleName, k -> new HashSet<>()).addAll(permissions);

		return this;
	}

	public TrackerPermissionBuilder revokePermissionForRole(String roleName, TrackerPermission... permissionsToRevoke) {
		revokePermissionForRole(roleName, Arrays.stream(permissionsToRevoke).toList());

		return this;
	}

	public TrackerPermissionBuilder revokePermissionForRole(String roleName, List<TrackerPermission> permissionsToRevoke) {
		Set<PermissionWithOp> permissions = convertInputPermissions(PermissionOperation.REVOKE, permissionsToRevoke);

		permissionsHolder.computeIfAbsent(roleName, k -> new HashSet<>()).addAll(permissions);

		return this;
	}

	public Map<String, Set<PermissionWithOp>> build() {
		return permissionsHolder;
	}

	private Set<PermissionWithOp> convertInputPermissions(PermissionOperation operation, List<TrackerPermission> permissions) {
		return permissions.stream()
				.map(p -> new PermissionWithOp(p, operation))
				.collect(Collectors.toSet());
	}
}
