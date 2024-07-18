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

package com.intland.codebeamer.integration.api.project;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEqualsNoOrder;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectPermission;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.ProjectRoles;
import com.intland.swagger.client.model.RoleReference;
import com.intland.swagger.client.model.UserGroupReference;
import com.intland.swagger.client.model.UserReference;

@Test(groups = "ProjectApiService")
public class ProjectApiServiceTest extends AbstractBaseNGTests {

	@Test(description = "Project can be created from zip via API")
	public void createProjectFromZipViaApi() throws URISyntaxException {
		// Given
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());
		TrackerItemApiService trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration());

		File projectTemplateFile = new File(Objects.requireNonNull(getClass().getResource("test_project_template.zip")).toURI());

		// When
		Project project = projectApiService.createProjectFromZip(projectTemplateFile);
		try {

			TrackerId tracker = projectApiService.findTrackerByName(project, "TestBugTracker");
			TrackerItemId item1 = trackerItemApiService.findTrackerItemByName(tracker, "bug1");
			TrackerItemId item2 = trackerItemApiService.findTrackerItemByName(tracker, "bug2");

			// Then
			assertNotNull(item1, "Item 1 is null");
			assertNotNull(item2, "Item 2 is null");

		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Add user with roles for project via API")
	public void addUserWithRolesForProjectViaApi() {
		// Given
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());
		UserApiService userService = new UserApiService(getApplicationConfiguration());
		
		User testUser = userService.createUser()
				.addToRegularUserGroup()
				.build();
		
		List<String> testRoles = List.of("Scrum Master", "Developer");

		// When
		Project project = projectApiService.createProjectFromTemplate();
		try {
			projectApiService.addUserWithRoles(project.id(), List.of(testUser.getName()), testRoles);

			//Then
			List<UserReference> members = getProjectMembers(Integer.valueOf(project.id().id())).stream()
					.filter(UserReference.class::isInstance)
					.map(UserReference.class::cast)
					.toList();
			assertNotNull(members);
			assertTrue(members.stream()
					.map(UserReference::getId)
					.filter(Objects::nonNull)
					.anyMatch(id -> id.equals(testUser.getId())));

			List<RoleReference> userRoles = getProjectRolesOfMember(Integer.valueOf(project.id().id()), testUser.getId());
			assertNotNull(userRoles);
			assertEqualsNoOrder(userRoles.stream()
					.map(AbstractReference::getName)
					.toList(), testRoles);
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Add group with roles for project via API")
	public void addGroupWithRolesForProjectViaApi() {
		// Given
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());

		// When
		Project project = projectApiService.createProjectFromTemplate();
		try {
			projectApiService.addGroupWithRoles(project.id(), List.of("Guest"), List.of("Product Owner", "Developer"));

			//Then
			List<UserGroupReference> userGroups = getProjectMembers(Integer.valueOf(project.id().id())).stream()
					.filter(UserGroupReference.class::isInstance)
					.map(UserGroupReference.class::cast)
					.toList();
			assertNotNull(userGroups);
			assertTrue(userGroups.stream()
					.map(UserGroupReference::getName)
					.anyMatch("guest"::equals));
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Add roles for project via API")
	public void addRolesForProjectViaApi() {
		// Given
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());

		// When
		Project project = projectApiService.createProjectFromTemplate();
		try {
			projectApiService.addRoles(project.id(), "Test role",
					List.of(ProjectPermission.WIKI_SPACE_VIEW, ProjectPermission.SCM_VIEW));

			//Then
			List<ProjectRoles> roles = getProjectRoles(Integer.valueOf(project.id().id()));
			ProjectRoles testRole = roles.stream()
					.filter(r -> Objects.nonNull(r.getRoleModel()))
					.filter(r -> "Test role".equals(r.getRoleModel().getName()))
					.findFirst()
					.orElse(null);
			assertNotNull(testRole);
			assertNotNull(testRole.getPermissionIDs());
			assertEquals(testRole.getPermissionIDs().size(), 2);
			assertEqualsNoOrder(testRole.getPermissionIDs(), List.of(Integer.valueOf(ProjectPermission.WIKI_SPACE_VIEW.bit()),
					Integer.valueOf(ProjectPermission.SCM_VIEW.bit())));
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}
}
