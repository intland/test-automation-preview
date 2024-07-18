package com.intland.codebeamer.integration.api.trackeritem;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractBaseNGTests;
import com.intland.swagger.client.model.AbstractFieldValue;
import com.intland.swagger.client.model.BoolFieldValue;
import com.intland.swagger.client.model.ChoiceFieldValue;
import com.intland.swagger.client.model.ColorFieldValue;
import com.intland.swagger.client.model.CountryFieldValue;
import com.intland.swagger.client.model.DateFieldValue;
import com.intland.swagger.client.model.DecimalFieldValue;
import com.intland.swagger.client.model.DurationFieldValue;
import com.intland.swagger.client.model.IntegerFieldValue;
import com.intland.swagger.client.model.LanguageFieldValue;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerItem;
import com.intland.swagger.client.model.UrlFieldValue;
import com.intland.swagger.client.model.WikiTextFieldValue;

@Test(groups = "TrackerItemApiService")
public class TrackerItemApiServiceTest extends AbstractBaseNGTests {

	private static final Logger logger = LogManager.getLogger(TrackerItemApiServiceTest.class);
	
	@Test(description = "Tracker item can be created via API")
	public void createTrackerItemViaApi() {
		// Given
		UserApiService userService = new UserApiService(getApplicationConfiguration());
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());

		User testUser = userService.createUser()
				.addToRegularUserGroup()
				.build();
		
		Project project = projectApiService.createProject("MyProject")
					.addUserAs(testUser, "Project Admin")
					.build();
		
		TrackerApiService trackerApiService = new TrackerApiService(this.getApplicationConfiguration(), testUser.getName());
		TrackerItemApiService trackerItemApiService = new TrackerItemApiService(this.getApplicationConfiguration(), testUser.getName());
		
		try {

			Tracker myUserStory = trackerApiService.createDefaultUserStoryTracker(project, "MyUserStory");

			Tracker myTask = trackerApiService
					.createTaskTracker(project, "MyTasks")
					.createTextField("My Text Field")
					.createIntegerField("My Integer Field")
					.createDecimalField("My Decimal Field")
					.createDateField("My Date Field")
					.createBooleanField("My Boolean Field")
					.createLanguageField("My Language Field")
					.createCountryField("My Country Field")
					.createWikiTextField("My WikiText Field")
					.createDurationField("My Duration Field")
					.createColorField("My Color Field")
					.createUrlField("My Url Field")
					.createReferenceFieldOfTrackers("My Reference Field", filter -> filter.addTrackerFilter(project, "MyUserStory"))
					.buildAndAdd();

			// When
			trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
					.name("Basic Radio")
					.description("description"));

			trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
					.name("Available Radio Stations")
					.description("description"));

			trackerItemApiService.createTrackerItem(project, "MyTasks", builder -> builder
					.name("name")
					.description("description")
					.priority("Low")
					.setTextFor("My Text Field", "Text")
					.setIntegerFor("My Integer Field", 1)
					.setDecimalFor("My Decimal Field", 2.2)
					.setDateFor("My Date Field", new Date())
					.setBooleanFor("My Boolean Field", true)
					.setLanguageFor("My Language Field", Language.DE)
					.setCountryFor("My Country Field", Country.DE)
					.setWikiTextFor("My WikiText Field", "Text")
					.setDurationFor("My Duration Field", Duration.ofHours(1))
					.setColorFor("My Color Field", "#ffffff")
					.setUrlFor("My Url Field", "http://my.com")
					.setTrackerItemFor("My Reference Field", project, "MyUserStory", "Available Radio Stations")
					.setTrackerItemFor("Subject", project, "MyUserStory", "Basic Radio")
					.setUserFor("Assigned to", testUser.getName(), "bond")
					.setRoleFor("Assigned to", "Developer", "Tester")
					);

			// Then
			// Assert user story
			TrackerItemId userStoriesItem1Id = trackerItemApiService.findTrackerItemByName(myUserStory, "Basic Radio");
			assertNotNull(userStoriesItem1Id, "Tracker item is null");

			TrackerItem userStoriesItem1 = getTrackerItem(userStoriesItem1Id);
			assertNotNull(userStoriesItem1, "Tracker item is null");
			assertEquals(userStoriesItem1.getName(), "Basic Radio");
			assertEquals(userStoriesItem1.getDescription(), "description");

			// Assert user story
			TrackerItemId userStoriesItem2Id = trackerItemApiService.findTrackerItemByName(myUserStory, "Available Radio Stations");
			assertNotNull(userStoriesItem2Id, "Tracker item is null");

			TrackerItem userStoriesItem2 = getTrackerItem(userStoriesItem2Id);
			assertNotNull(userStoriesItem2, "Tracker item is null");
			assertEquals(userStoriesItem2.getName(), "Available Radio Stations");
			assertEquals(userStoriesItem2.getDescription(), "description");

			//MyTasks tracker item
			TrackerItemId tasksTrackerItemId = trackerItemApiService.findTrackerItemByName(myTask, "name");
			assertNotNull(tasksTrackerItemId, "Tracker item is null");
			
			TrackerItem tasksTrackerItem = getTrackerItem(tasksTrackerItemId);
			assertNotNull(tasksTrackerItem, "Tracker item is null");
			assertNotNull(tasksTrackerItem.getPriority());
			assertEquals(tasksTrackerItem.getPriority().getName(), "Low");

			List<AbstractFieldValue> tasksTrackerItemCustomFields = tasksTrackerItem.getCustomFields();
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Text Field", TextFieldValue.class).getValue(), "Text");
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Integer Field", IntegerFieldValue.class).getValue(), 1);
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Decimal Field", DecimalFieldValue.class).getValue(), 2.2);

			assertNotNull(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Date Field", DateFieldValue.class).getValue());
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Boolean Field", BoolFieldValue.class).getValue(), Boolean.TRUE);
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Language Field", LanguageFieldValue.class).getValues(), Set.of(Language.DE.name()));
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Country Field", CountryFieldValue.class).getValues(), Set.of(Country.DE.name()));
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My WikiText Field", WikiTextFieldValue.class).getValue(), "Text");
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Duration Field", DurationFieldValue.class).getValue(), Duration.ofHours(1).toMillis());
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Color Field", ColorFieldValue.class).getValue(), "#ffffff");
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Url Field", UrlFieldValue.class).getValue(), "http://my.com");
			assertEquals(getCustomFieldByNameAndType(tasksTrackerItemCustomFields, "My Reference Field", ChoiceFieldValue.class).getValues().getFirst().getName(), "Available Radio Stations");

			assertEquals(Optional.ofNullable(tasksTrackerItem.getAssignedTo())
					.orElse(List.of())
					.stream()
					.map(ref -> ref.getType())
					.filter("RoleReference"::equals)
					.toList().size(), 2);

			assertTrue(Optional.ofNullable(tasksTrackerItem.getAssignedTo())
					.orElse(List.of())
					.stream()
					.map(ref -> ref.getName())
					.anyMatch("bond"::equals));

		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Tracker item can be updated via API")
	public void updateTrackerItemViaApi() {
		// Given
		UserApiService userService = new UserApiService(getApplicationConfiguration());
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());
		TrackerItemApiService trackerItemApiService = new TrackerItemApiService(this.getApplicationConfiguration());
		
		User testUser = userService.createUser()
			.addToRegularUserGroup()
			.build();
		
		Project project = projectApiService.createProjectFromTemplate();

		try {
			trackerItemApiService.createTrackerItem(project, "User Stories", builder -> builder
					.name("Basic Radio")
					.description("description"));

			trackerItemApiService.createTrackerItem(project, "User Stories", builder -> builder
					.name("Available Radio Stations")
					.description("description"));

			TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, "Tasks", builder -> builder
					.name("name")
					.description("description")
					.priority("Low")
					.setDurationFor("Planned Effort", Duration.ofHours(1))
					.setTrackerItemFor("Upstream Reference", project, "User Stories", "Basic Radio")
					.setUserFor("Assigned to", testUser.getName(), "bond")
					.setRoleFor("Assigned to", "Developer", "Tester"));

			// When
			trackerItemApiService.updateTrackerItem(trackerItem, builder -> builder
					.name("updated name")
					.description("updated description")
					.priority("High")
					.status("In Progress")
					.setDurationFor("Planned Effort", Duration.ofHours(2))
					.setTrackerItemFor("Upstream Reference", project, "User Stories", "Available Radio Stations")
					.setUserFor("Assigned to", "bond")
					.setRoleFor("Assigned to", "Developer")
					);

			// Then
			TrackerId tasksTrackerId = projectApiService.findTrackerByName(project, "Tasks");
			TrackerItemId tasksTrackerItemId = trackerItemApiService.findTrackerItemByName(tasksTrackerId, "updated name");
			assertNotNull(tasksTrackerItemId, "Tracker item is null");

			TrackerItem tasksTrackerItem = getTrackerItem(tasksTrackerItemId);
			assertNotNull(tasksTrackerItem, "Tracker item is null");
			assertEquals(tasksTrackerItem.getName(), "updated name");
			assertEquals(tasksTrackerItem.getDescription(), "updated description");
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Tracker item can be deleted via API")
	public void deleteTrackerItemViaApi() {
		// GIVEN
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());
		TrackerApiService trackerApiService = new TrackerApiService(getApplicationConfiguration());
		TrackerItemApiService trackerItemApiService = new TrackerItemApiService(this.getApplicationConfiguration());

		Project project = projectApiService.createProjectFromTemplate();

		try {
			Tracker myTask = trackerApiService.createDefaultTaskTracker(project, "MyTasks");

			TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(myTask.id(), builder -> builder
					.name("Basic Task")
					.description("description"));

			TrackerItemId basicTask = trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");

			assertNotNull(basicTask, "Tracker item cannot be null");

			// WHEN
			trackerItemApiService.deleteTrackerItem(trackerItem);

			// THEN
			try {
				trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");
				fail("Tracker item should have been deleted");
			} catch (Exception e) {
				logger.info(e.getMessage());
				assertTrue(Pattern.matches("Tracker item is not found by name:.*Basic Task.*", e.getMessage()));
			}
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	@Test(description = "Tracker item can be deleted by name via API")
	public void deleteTrackerItemByNameViaApi() {
		// GIVEN
		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration());
		TrackerApiService trackerApiService = new TrackerApiService(getApplicationConfiguration());
		TrackerItemApiService trackerItemApiService = new TrackerItemApiService(this.getApplicationConfiguration());

		Project project = projectApiService.createProjectFromTemplate();

		try {
			Tracker myTask = trackerApiService.createDefaultTaskTracker(project, "MyTasks");

			trackerItemApiService.createTrackerItem(myTask.id(), builder -> builder
					.name("Basic Task")
					.description("description"));

			TrackerItemId basicTask = trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");

			assertNotNull(basicTask, "Tracker item cannot be null");

			// WHEN
			trackerItemApiService.deleteTrackerItemByName(myTask, "Basic Task");

			// THEN
			try {
				trackerItemApiService.findTrackerItemByName(myTask, "Basic Task");
				fail("Tracker item should have been deleted");
			} catch (Exception e) {
				logger.info(e.getMessage());
				assertTrue(Pattern.matches("Tracker item is not found by name:.*Basic Task.*", e.getMessage()));
			}
		} finally {
			projectApiService.deleteProject(project.id());
		}
	}

	private <T> T getCustomFieldByNameAndType(List<AbstractFieldValue> customFieldList, String fieldName, Class<T> clazz) {
		AbstractFieldValue abstractFieldValue = Optional.ofNullable(customFieldList)
				.orElse(List.of())
				.stream()
				.filter(f -> fieldName.equals(f.getName()))
				.findFirst().orElse(null);
		assertNotNull(abstractFieldValue, String.format("Custom field %s is null", fieldName));

		return Optional.ofNullable(abstractFieldValue)
				.filter(clazz::isInstance)
				.map(clazz::cast)
				.orElseThrow(() -> new IllegalStateException("%s cannot be converted to %s".formatted(abstractFieldValue.getClass(), clazz)));
	}
}
