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

package com.intland.codebeamer.integration.api.builder.permission;

import java.util.ArrayList;
import java.util.List;

public class FieldPermissionBuilder {

	private final List<TrackerFieldPermission> permissions = new ArrayList<>();

	public FieldPermissionBuilder addReadAccessForField(String fieldName) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.READ, null));
		return this;
	}

	public FieldPermissionBuilder addWriteAccessForField(String fieldName) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.READ_WRITE, null));
		return this;
	}

	public FieldPermissionBuilder clearReadAccessForField(String fieldName) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.NONE, null));
		return this;
	}

	public FieldPermissionBuilder addReadAccessForFieldAndStatus(String fieldName, String status) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.READ, status));
		return this;
	}

	public FieldPermissionBuilder addWriteAccessForFieldAndStatus(String fieldName, String status) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.READ_WRITE, status));
		return this;
	}

	public FieldPermissionBuilder clearReadAccessForFieldAndStatus(String fieldName, String status) {
		permissions.add(new TrackerFieldPermission(fieldName, SubjectType.FIELD, AccessLevel.NONE, status));
		return this;
	}

	public FieldPermissionBuilder addReadAccessForRole(String roleName) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.READ, null));
		return this;
	}

	public FieldPermissionBuilder addWriteAccessForRole(String roleName) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.READ_WRITE, null));
		return this;
	}

	public FieldPermissionBuilder clearReadAccessForRole(String roleName) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.NONE, null));
		return this;
	}

	public FieldPermissionBuilder addReadAccessForRoleAndStatus(String roleName, String status) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.READ, status));
		return this;
	}

	public FieldPermissionBuilder addWriteAccessForRoleAndStatus(String roleName, String status) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.READ_WRITE, status));
		return this;
	}

	public FieldPermissionBuilder clearReadAccessForRoleAndStatus(String roleName, String status) {
		permissions.add(new TrackerFieldPermission(roleName, SubjectType.ROLE, AccessLevel.NONE, status));
		return this;
	}

	public List<TrackerFieldPermission> build() {
		return permissions;
	}
}
