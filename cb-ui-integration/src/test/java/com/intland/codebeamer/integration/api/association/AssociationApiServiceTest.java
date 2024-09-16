package com.intland.codebeamer.integration.api.association;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.association.AssociationType;
import com.intland.codebeamer.integration.api.service.artifact.AssociationId;
import com.intland.codebeamer.integration.api.service.association.AssociationApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.model.Association;

@Test(groups = "AssociationApiService")
public class AssociationApiServiceTest extends AbstractBaseNGTests {

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private AssociationApiService associationApiService;

	private Project project;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		UserApiService userService = new UserApiService(getApplicationConfiguration());
		User testUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = new ProjectApiService(getApplicationConfiguration(), testUser.getName());
		trackerApiService = new TrackerApiService(getApplicationConfiguration(), testUser.getName());
		trackerItemApiService = new TrackerItemApiService(getApplicationConfiguration(), testUser.getName());
		associationApiService = new AssociationApiService(getApplicationConfiguration(), testUser.getName());

		project = projectApiService.createProjectFromTemplate(getRandomText("MyProject"));
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		projectApiService.deleteProject(project);
	}


	@Test(description = "Association creation and deletion")
	public void testCreateAssociation() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultTaskTracker(project, getRandomText("Tasks"));

		TrackerItemId task1 = trackerItemApiService.createTrackerItem(tracker, builder -> builder
				.name(getRandomText("Task1"))
				.description(getRandomText())
		);
		TrackerItemId task2 = trackerItemApiService.createTrackerItem(tracker, builder -> builder
				.name(getRandomText("Task2"))
				.description(getRandomText())
		);

		// WHEN
		AssociationId associationId = associationApiService.createAssociation(associationBuilder -> associationBuilder
				.from(task1)
				.to(task2)
				.type(AssociationType.DEPENDS)
				.propagateSuspects()
				.bidirectionalPropagation()
		);

		// THEN
		Association association = findAssociation(associationId);
		assertEquals(association.getFrom().getId(), task1.id());
		assertEquals(association.getTo().getId(), task2.id());
		assertTrue(association.getPropagatingSuspects());
		assertTrue(association.getBiDirectionalPropagation());
		assertFalse(association.getReversePropagation());
		assertFalse(association.getPropagatingDependencies());

		// WHEN
		associationApiService.deleteAssociation(associationId);

		// THEN
		try {
			findAssociation(associationId);
			fail();
		} catch (Exception e) {
			assertEquals(((ApiException) e.getCause()).getCode(), 404);
		}
	}
}
