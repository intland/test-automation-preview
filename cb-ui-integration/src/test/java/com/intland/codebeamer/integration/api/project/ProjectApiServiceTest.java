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
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectGroup;
import com.intland.codebeamer.integration.api.service.project.ProjectPermission;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
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

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private UserApiService userService;

	@BeforeClass
	public void beforeClass() {
		projectApiService = new ProjectApiService(getApplicationConfiguration());
		trackerApiService = new TrackerApiService(getApplicationConfiguration());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration());
		userService = new UserApiService(getApplicationConfiguration());
	}

	@Test(description = "Project can be created from zip via API")
	public void createProjectFromZipViaApi() throws URISyntaxException {
		// Given
		File projectTemplateFile = new File(Objects.requireNonNull(getClass().getResource("test_project_template.zip")).toURI());

		// When
		Project project = projectApiService.createProjectFromZip(projectTemplateFile);
		try {

			Tracker tracker = projectApiService.findTrackerByName(project, "TestBugTracker");
			TrackerItem item1 = trackerItemApiService.findTrackerItemByName(tracker, "bug1");
			TrackerItem item2 = trackerItemApiService.findTrackerItemByName(tracker, "bug2");

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

	@Test(description = "Agile-Scrum demo project can be created via API")
	public void createAgileScrumProject() {
		// GIVEN
		// WHEN
		Project project = projectApiService.createProjectFromTemplate("AGILE project", ProjectTemplate.AGILE_SCRUM);

		// THEN
		try {
			Tracker epic = projectApiService.findTrackerByName(project, "Epics");
			assertNotNull(epic);

			TrackerItem item = trackerItemApiService.findTrackerItemByName(epic, "Video Playback");
			assertNotNull(item);

		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Agile-Waterfall-Hybrid demo project can be created via API")
	public void createAgileWaterfallHybridProject() {
		// GIVEN
		// WHEN
		Project project = projectApiService.createProjectFromTemplate("WATERFALL project",
				ProjectTemplate.AGILE_WATERFALL_HYBRID);

		// THEN
		try {
			Tracker bugs = projectApiService.findTrackerByName(project, "9 Bugs");
			assertNotNull(bugs);

			TrackerItem item = trackerItemApiService.findTrackerItemByName(bugs, "Audio tags does not work");
			assertNotNull(item);
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Create and use template project via API")
	public void createAndUseTemplateProject() {
		// GIVEN
		String taskName = "Task from Template";

		// WHEN
		Project template = projectApiService.createProject("Template project 123")
				.template().build();
		trackerApiService.createDefaultTaskTracker(template, taskName);

		Project useTemplate = projectApiService.createProject("Using Template project 123")
				.useTemplateProject(template).build();

		// THEN
		try {
			Tracker taskFromTemplate = projectApiService.findTrackerByName(template, taskName);
			assertNotNull(taskFromTemplate);

			Tracker taskInUsedTemplate = projectApiService.findTrackerByName(useTemplate, taskName);
			assertNotNull(taskInUsedTemplate, "Tracker should have been copied from template project");
		} finally {
			projectApiService.deleteProject(template.id());
			projectApiService.deleteProject(useTemplate.id());
		}
	}

	@Test(description = "Project group creation via API")
	public void testCreateProjectGroup() {
		// GIVEN
		String testGroupName = "TestProjectGroup";
		ProjectGroup projectGroup = null;

		// WHEN
		try {
			projectApiService.createProjectGroup(testGroupName);

			// THEN
			projectGroup = projectApiService.findAllProjectGroups().stream()
					.filter(pg -> testGroupName.equals(pg.name()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("Project group not found"));
		} finally {
			if (projectGroup != null) {
				projectApiService.deleteProjectGroup(projectGroup.id());
			}
		}
	}

	@Test(description = "Test find project group by ID via API")
	public void testFindProjectGroupById() {
		// GIVEN
		String testGroupName = "TestProjectGroup";
		ProjectGroup projectGroup = null;
		try {
			projectApiService.createProjectGroup(testGroupName);
			projectGroup = projectApiService.findAllProjectGroups().stream()
					.filter(pg -> testGroupName.equals(pg.name()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("Project group not found"));

			//WHEN
			ProjectGroup projectGroupById = projectApiService.findProjectGroupById(Integer.valueOf(projectGroup.id().id()));

			//THEN
			assertEquals(projectGroupById, projectGroup);
		} finally {
			if (projectGroup != null) {
				projectApiService.deleteProjectGroup(projectGroup.id());
			}
		}

	}

	@Test(description = "Delete project group via API")
	public void testDeleteProjectGroup() {
		// GIVEN
		String testGroupName = "TestProjectGroup";
		projectApiService.createProjectGroup(testGroupName);
		List<ProjectGroup> allProjectGroup = projectApiService.findAllProjectGroups();
		ProjectGroup createdProjectGroup = allProjectGroup.stream().filter(pg -> testGroupName.equals(pg.name())).findFirst()
				.orElseThrow(() -> new AssertionError("Project group not found"));

		// WHEN
		projectApiService.deleteProjectGroup(createdProjectGroup.id());

		// THEN
		allProjectGroup = projectApiService.findAllProjectGroups();
		if (!allProjectGroup.isEmpty()) {
			createdProjectGroup = allProjectGroup.stream().filter(pg -> testGroupName.equals(pg.name()))
					.findFirst().orElse(null);
			assertNull(createdProjectGroup);
		}

	}

	@Test(description = "Project can be added to a group via API")
	public void testAddProjectToGroup() {
		// Given
		String testGroupName = "TestProjectGroup";
		projectApiService.createProjectGroup(testGroupName);
		ProjectGroup projectGroup = projectApiService.findAllProjectGroups().stream()
				.filter(pg -> testGroupName.equals(pg.name()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("Project group not found"));
		Project project = projectApiService.createProjectFromTemplate("AGILE project", ProjectTemplate.AGILE_SCRUM);

		try {
			//When
			projectApiService.addProjectToGroup(Integer.valueOf(projectGroup.id().id()), List.of(Integer.valueOf(project.id().id())));
			//Then
			List<Project> projectsByGroupId = projectApiService.findProjectsByGroupId(Integer.valueOf(projectGroup.id().id()));
			assertEquals(projectsByGroupId.getFirst(), project);
		} finally {
			projectApiService.deleteProject(project.id());
			projectApiService.deleteProjectGroup(projectGroup.id());
		}

	}
}
