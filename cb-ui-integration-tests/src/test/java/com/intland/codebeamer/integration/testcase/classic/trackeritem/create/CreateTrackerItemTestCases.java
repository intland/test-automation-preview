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

package com.intland.codebeamer.integration.testcase.classic.trackeritem.create;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "CreateTrackerItemTestCases")
public class CreateTrackerItemTestCases extends AbstractIntegrationClassicNGTests {

	private User systemUser;

	private User projectAdmin;

	private Project project;

	private Tracker taskTracker;

	private Role developer;

	private Role tester;

	private Role stakeholder;

	private List<TrackerItem> testTrackerItems = new ArrayList<>();

	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService services = getDataManagerService();
		TrackerItemApiService trackerItemApiService = services.getTrackerItemApiService();

		developer = services.getRoleApiService().findRoleByName("Developer");
		tester = services.getRoleApiService().findRoleByName("Tester");
		stakeholder = services.getRoleApiService().findRoleByName("Stakeholder");

		systemUser = services.getUserApiService().findUserByName("bond");

		projectAdmin = services.getUserApiService().createUser()
				.addToRegularUserGroup()
				.build();

		project = services.getProjectApiService().createProject(getRandomText("MyProject"))
				.addUserAs(projectAdmin, "Project Admin")
				.build();

		services.getTrackerApiService(projectAdmin).createDefaultBugTracker(project, "MyBug");
		services.getTrackerApiService(projectAdmin).createDefaultTaskTracker(project, "MyTask");

		List<Pair<String, String>> trackerItems = List.of(
				new Pair("MyBug", "My Bug 1"),
				new Pair("MyTask", "My Task 1"),
				new Pair("MyTask", "My Task 2"));

		TrackerItemId trackerItemId;
		for (Pair<String, String> trackerItem : trackerItems) {
			trackerItemId = trackerItemApiService.createTrackerItem(project, trackerItem.first(), builder -> builder
					.name(trackerItem.second())
					.description("description"));

			this.testTrackerItems.add(new TrackerItem(trackerItemId, trackerItem.second()));
		}

		taskTracker = services.getTrackerApiService(projectAdmin)
				.createTaskTracker(project, "MyTasks")
				.createTextField("My Text Field")
				.createIntegerField("My Integer Field")
				.createDecimalField("My Decimal Field")
				.createDateField("My Date Field")
				.createBooleanField("My Boolean Field")
				.createDurationField("My Duration Field")
				.createColorField("My Color Field")
				.createReferenceFieldOfTrackers("My Reference Field", referenceFilter -> referenceFilter
						.addTrackerFilter(project, "MyBug")
						.addTrackerFilter(project, "MyTask"))
				.createReferenceFieldOfTrackers("My Multi Reference Field", referenceFilter -> referenceFilter
								.addTrackerFilter(project, "MyBug")
								.addTrackerFilter(project, "MyTask"),
						fieldProp -> fieldProp.multipleSelection(true))
				.createLanguageField("My Language Field", f -> f.multipleSelection(true))
				.createCountryField("My Country Field", f -> f.multipleSelection(true))
				.createWikiTextField("My WikiText Field")
				.createUrlField("My Url Field")
				.buildAndAdd();
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService().deleteProject(project);
		getDataManagerService().getUserApiService().disableUser(projectAdmin);
	}

	@Test(description = "User is able to fill out fields of a new tracker item")
	public void userIsAbleToFillOutFieldOfANewTrackerItem() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemCreatePage(taskTracker)
				.fieldFormComponent(form -> form
						.setTextField("Summary", "Test Summmary")
						.setWikiText("Description", "Test Description")
						.setWikiText("My WikiText Field", f -> f
								.setComment("My WikiText Text")
								.addAttachment(getFilePath("test-file.txt"))
								.addAttachment(getFilePath("test-file.tar.gz"))
								.addAttachment(getFilePath("test-file.zip")))
						.setTextField("My Text Field", "Test Text")
						.setChoiceField("Priority", "Low")
						.setChoiceField("Resolution", "Successful")
						.setChoiceField("Severity", "Major")
						.setIntegerField("My Integer Field", 42)
						.setDecimalField("My Decimal Field", 42.42)
						.setDateField("My Date Field", 1984, 8, 20, 11, 58)
						.setBooleanField("My Boolean Field", Boolean.FALSE)
						.setDurationField("My Duration Field", "12h")
						.setColorField("My Color Field", HtmlColor.COLOR_005C50)
						.addReferenceTo("My Reference Field", "My Bug 1")
						.addReferenceTo("My Multi Reference Field", "My Bug 1", "My Task 1")
						.addCountryField("My Country Field", Country.AI, Country.AD)
						.addLanguageField("My Language Field", Language.AE, Language.GA)
						.addUserTo("Assigned to", "bond")
						.addRoleTo("Assigned to", "Developer", "Tester", "Stakeholder"))
				.assertFieldFormComponent(f -> f
						.textField("Summary", a -> a.is("Test Summmary"))
						.wikiText("Description", a -> a.is("Test Description"))
						.wikiText("My WikiText Field", a -> a.contains("My WikiText Text"))
						.choiceField("Priority", a -> a.is("Low"))
						.choiceField("Resolution", a -> a.is("Successful"))
						.choiceField("Severity", a -> a.is("Major"))
						.textField("My Text Field", a -> a.is("Test Text"))
						.integerField("My Integer Field", a -> a.is(42))
						.decimalField("My Decimal Field", a -> a.is(42.42))
						.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
						.booleanField("My Boolean Field", a -> a.isFalse())
						.durationField("My Duration Field", a -> a.is("12h"))
						.referenceField("My Multi Reference Field", a -> a.is(this.testTrackerItems.subList(0, 1)))
						.countryField("My Country Field", a -> a.sameAs(Country.AI, Country.AD))
						.languageField("My Language Field", a -> a.sameAs(Language.AE, Language.GA))
						.referenceField("My Reference Field", a -> a.is(this.testTrackerItems.getFirst()))
						.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
				);
	}

	@Test(description = "User is able to create a new tracker item")
	public void userIsAbleToCreateANewTrackerItem() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemCreatePage(taskTracker)
				.fieldFormComponent(form -> form
						.setTextField("Summary", "Test Summmary")
						.setWikiText("Description", "Test Description")
						.setWikiText("My WikiText Field", f -> f
								.setComment("My WikiText Text")
								.addAttachment(getFilePath("test-file.txt"))
								.addAttachment(getFilePath("test-file.tar.gz"))
								.addAttachment(getFilePath("test-file.zip")))
						.setTextField("My Text Field", "My Text")
						.setChoiceField("Priority", "Low")
						.setChoiceField("Resolution", "Successful")
						.setChoiceField("Severity", "Major")
						.setIntegerField("My Integer Field", 42)
						.setDecimalField("My Decimal Field", 42.42)
						.setDateField("My Date Field", 1984, 8, 20, 11, 58)
						.setBooleanField("My Boolean Field", Boolean.FALSE)
						.setDurationField("My Duration Field", "12h")
						.setColorField("My Color Field", HtmlColor.COLOR_005C50)
						.addCountryField("My Country Field", Country.AI, Country.AD)
						.addLanguageField("My Language Field", Language.AE, Language.GA)
						.addUserTo("Assigned to", "bond")
						.addReferenceTo("My Reference Field", "My Bug 1")
						.addRoleTo("Assigned to", "Developer", "Tester", "Stakeholder"))
				.save()
				.redirectedToTrackerItemPage()
				.commentsAndAttachmentsTabComponent()
					.addCommentsAndAttachments()
					.setComment("My Comment")
					.addAttachment(getFilePath("test-file.txt"))
					.addAttachment(getFilePath("test-file.tar.gz"))
					.addAttachment(getFilePath("test-file.zip"))
				.save()
				.assertFieldFormComponent(f -> f
						.description(a -> a.is("Test Description"))
						.choiceField("Priority", a -> a.is("Low"))
						.choiceField("Resolution", a -> a.is("Successful"))
						.choiceField("Severity", a -> a.is("Major"))
						.textField("My Text Field", a -> a.is("My Text"))
						.wikiText("My WikiText Field", a -> a.contains("My WikiText Text"))
						.integerField("My Integer Field", a -> a.is(42))
						.decimalField("My Decimal Field", a -> a.is(42.42))
						.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
						.booleanField("My Boolean Field", a -> a.isFalse())
						.durationField("My Duration Field", a -> a.is("12:00h"))
						.countryField("My Country Field", a -> a.sameAs(Country.AI, Country.AD))
						.languageField("My Language Field", a -> a.sameAs(Language.AE, Language.GA))
						.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
						.memberField("Assigned to", a -> a
								.contains(systemUser)
								.sameAs(systemUser, developer, tester, stakeholder)));
	}

	@Test(description = "User is able to add a reference item to a specific position")
	public void userIsAbleToAddANewReferenceItemToPosition() {
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemCreatePage(taskTracker)
				.fieldFormComponent(form -> form
						.setTextField("Summary", "Test Summmary")
						.setWikiText("Description", "Test Description")
						.addReferenceTo("My Multi Reference Field", "My Bug 1", "My Task 2")
						.addReferenceToPosition("My Multi Reference Field", "My Task 1", 1))
				.assertFieldFormComponent(f -> f
						.referenceField("My Multi Reference Field", a -> a.is(this.testTrackerItems)));
	}

}
