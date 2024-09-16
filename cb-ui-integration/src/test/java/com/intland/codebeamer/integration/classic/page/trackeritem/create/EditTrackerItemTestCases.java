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

package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.time.Duration;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.Country;
import com.intland.codebeamer.integration.api.service.trackeritem.Language;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.classic.component.model.RoleReference;
import com.intland.codebeamer.integration.classic.component.model.UserReference;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "EditTrackerItemTestCases")
public class EditTrackerItemTestCases extends AbstractIntegrationClassicNGTests {

	private User projectAdmin;

	private Project project;

	private Tracker taskTracker;

	private Tracker userStoryTracker;

	private TrackerItemId userStory;

	private TrackerItemId testCase;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {
		// Given
		UserApiService userService = getDataManagerService().getUserApiServiceWithConfigUser();

		projectAdmin = userService.createUser()
				.addToRegularUserGroup()
				.build();

		ProjectApiService projectApiService = getDataManagerService().getProjectApiService(projectAdmin);

		User systemUser = getDataManagerService().getUserApiServiceWithConfigUser().findUserByName("bond");
		
		project = projectApiService.createProject(getRandomText("MyProject"))
				.addUserAs(systemUser, "Developer")
				.build();
		
		TrackerApiService trackerApiService = getDataManagerService().getTrackerApiService(projectAdmin);
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		
		userStoryTracker = trackerApiService.createDefaultUserStoryTracker(project, "MyUserStory");

		taskTracker = trackerApiService
			.createTaskTracker(project, "MyTasks")
			.createTextField("My Text Field")
			.createIntegerField("My Integer Field")
			.createDecimalField("My Decimal Field")
			.createDateField("My Date Field")
			.createBooleanField("My Boolean Field")
			.createLanguageField("My Language Field", f -> f.multipleSelection())
			.createCountryField("My Country Field", f -> f.multipleSelection())
			.createWikiTextField("My WikiText Field")
			.createDurationField("My Duration Field")
			.createColorField("My Color Field")
			.createWikiLinkUrlField("My Url Field")
			.createWikiLinkUrlField("My Simple Url Field")
			.createWikiLinkUrlField("My Wiki Link Field")
			.createWikiLinkUrlField("My Other Wiki Link Field")
			.createReferenceFieldOfTrackers("My Reference Field",
					filter -> filter.addTrackerFilter(project, "MyUserStory"),
					field -> field.multipleSelection())
			.buildAndAdd();

		// When
		userStory = trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
				.name("Basic Radio")
				.description("description"));

		trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
				.name("Available Radio Stations")
				.description("description"));

		trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
				.name("MyTrackerItem - A")
				.description("description"));
		
		trackerItemApiService.createTrackerItem(project, "MyUserStory", builder -> builder
				.name("MyTrackerItem - B")
				.description("description"));
		
		testCase = trackerItemApiService.createTrackerItem(project, "Test Cases", builder -> builder
				.name("Test Case 1")
				.description("description"));
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(projectAdmin).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(projectAdmin);
	}

	@Test(description = "User is able to open and see the already saved values")
	public void userIsAbleToOpenAndSeeTheAlreadySavedValues() {
		// Given
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);

		User systemUser = getDataManagerService().getUserApiServiceWithConfigUser().findUserByName("bond");
		Role developer = getDataManagerService().getRoleApiService(projectAdmin).findRoleByName("Developer");
		Role tester = getDataManagerService().getRoleApiService(projectAdmin).findRoleByName("Tester");
		TrackerItem basicRadioTrackerItem = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Basic Radio");
		TrackerItem availableRadioStationsTrackerItem = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Available Radio Stations");
	
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTasks", builder -> builder
				.name("Test Summary")
				.description("Test Description")
				.priority("Low")
				.resolution("Successful")
				.severity("Major")
				.setTextFor("My Text Field", "My Text")
				.setIntegerFor("My Integer Field", 42)
				.setDecimalFor("My Decimal Field", 42.42)
				.setDateFor("My Date Field", 1984, 8, 20, 11, 58)
				.setBooleanFor("My Boolean Field", true)
				.setLanguageFor("My Language Field", Language.AE, Language.GA)
				.setCountryFor("My Country Field", Country.AD, Country.AI)
				.setWikiTextFor("My WikiText Field", "My WikiText Text")
				.setDurationFor("My Duration Field", Duration.ofHours(1))
				.setColorFor("My Color Field", HtmlColor.COLOR_005C50)
				.setUrlFor("My Url Field", "MYCOM", "http://my.com")
				.setUrlFor("My Simple Url Field", "http://my.com")
				.setWikiLinkFor("My Wiki Link Field", userStory)
				.setWikiLinkFor("My Other Wiki Link Field", testCase)
				.setTrackerItemFor("My Reference Field", project, "MyUserStory", "Available Radio Stations", "Basic Radio")
				.setTrackerItemFor("Subject", project, "MyUserStory", "Basic Radio")
				.setUserFor("Assigned to", projectAdmin.getName(), "bond")
				.setRoleFor("Assigned to", "Developer", "Tester"));
		
		// When / Then
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemEditPage(taskTracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.textField("Summary", a -> a.is("Test Summary"))
						.wikiTextField("Description", a -> a.is("Test Description"))
						.choiceField("Priority", a -> a.is("Low"))
						.choiceField("Resolution", a -> a.is("Successful"))
						.choiceField("Severity", a -> a.is("Major"))
						.textField("My Text Field", a -> a.is("My Text"))
						.wikiTextField("My WikiText Field", a -> a.contains("My WikiText Text"))
						.integerField("My Integer Field", a -> a.is(42))
						.decimalField("My Decimal Field", a -> a.is(42.42))
						.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
						.booleanField("My Boolean Field", a -> a.isTrue())
						.durationField("My Duration Field", a -> a.is("1:00h"))
						.countryField("My Country Field", a -> a.sameAs(Country.AD, Country.AI))
						.languageField("My Language Field", a -> a.sameAs(Language.AE, Language.GA))
						.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
						.urlField("My Url Field", a -> a
								.isAlias("MYCOM")
								.isUrl("http://my.com"))
						.urlField("My Simple Url Field", a -> a
								.isAlias("http://my.com")
								.isUrl("http://my.com"))
						// wiki link
						.referenceField("Subject", a -> a.is(basicRadioTrackerItem))
						.referenceField("My Reference Field", a -> a.is(availableRadioStationsTrackerItem))
						.memberField("Assigned to", a -> a
								.contains(systemUser, projectAdmin, developer, tester))
						);
	}

	@Test(description = "User is able to edit already saved values")
	public void userIsAbleToEditAlreadySavedValues() {
		// Given
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);

		User systemUser = getDataManagerService().getUserApiServiceWithConfigUser().findUserByName("bond");
		Role developer = getDataManagerService().getRoleApiService(projectAdmin).findRoleByName("Developer");
		TrackerItem basicRadioTrackerItem = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Basic Radio");
		TrackerItem trackerItemB = trackerItemApiService.findTrackerItemByName(userStoryTracker, "MyTrackerItem - B");

		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTasks", builder -> builder
				.name("Test Summary")
				.description("Test Description")
				.priority("Low")
				.resolution("Successful")
				.severity("Major")
				.setTextFor("My Text Field", "My Text")
				.setIntegerFor("My Integer Field", 42)
				.setDecimalFor("My Decimal Field", 42.42)
				.setDateFor("My Date Field", 1984, 8, 20, 11, 58)
				.setBooleanFor("My Boolean Field", true)
				.setLanguageFor("My Language Field", Language.AE, Language.GA)
				.setCountryFor("My Country Field", Country.AD, Country.AI)
				.setWikiTextFor("My WikiText Field", "My WikiText Text")
				.setDurationFor("My Duration Field", Duration.ofHours(1))
				.setColorFor("My Color Field", HtmlColor.COLOR_005C50)
				.setUrlFor("My Url Field", "MYCOM", "http://my.com")
				.setUrlFor("My Simple Url Field", "http://my.com")
				.setWikiLinkFor("My Wiki Link Field", userStory)
				.setWikiLinkFor("My Other Wiki Link Field", testCase)
				.setTrackerItemFor("My Reference Field", project, "MyUserStory", "Available Radio Stations", "Basic Radio")
				.setTrackerItemFor("Subject", project, "MyUserStory", "Basic Radio")
				.setUserFor("Assigned to", projectAdmin.getName(), "bond")
				.setRoleFor("Assigned to", "Developer", "Tester"));
		
		// When / Then
		TrackerItemEditPage trackerItemEditPage = getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemEditPage(taskTracker, trackerItemId)
				.fieldFormComponent(form -> form
						.setTextField("Summary", "Test Summary")
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
						.setDurationField("My Duration Field", "12:00h")
						.setColorField("My Color Field", HtmlColor.COLOR_005C50)
						// Country
						.clearCountryField("My Country Field")
						.addCountryField("My Country Field", Country.BA)
						.removeCountryField("My Country Field", Country.BA)
						.addCountryField("My Country Field", Country.HK)
						// Language
						.clearLanguageField("My Language Field")
						.addLanguageField("My Language Field", Language.HU)
						.removeLanguageField("My Language Field", Language.HU)
						.addLanguageField("My Language Field", Language.EE)
						// References
						.clearReferenceField("My Reference Field")
						.addReferenceTo("My Reference Field", "MyTrackerItem - A")
						.removeReferenceFrom("My Reference Field", "MyTrackerItem - A")
						.addReferenceTo("My Reference Field", "MyTrackerItem - B")
						// Members
						.clearMemberField("Assigned to")
						.addMemberTo("Assigned to",
								new UserReference("bond"),
								new RoleReference("Developer"))
						.removeMemberFrom("Assigned to", new UserReference("bond"))
						.addMemberTo("Assigned to", new UserReference("bond"))
						.clearUrlField("My Url Field")
						.setUrlField("My Url Field", "http://wiki.com", "WIKI")
						.removeUrlFrom("My Wiki Link Field", "Basic Radio")
						.setWikiLinkField("My Wiki Link Field", testCase, "testTrackerItem"))

				.assertFieldFormComponent(f -> f
						.textField("Summary", a -> a.is("Test Summary"))
						.wikiTextField("Description", a -> a.is("Test Description"))
						.choiceField("Priority", a -> a.is("Low"))
						.choiceField("Resolution", a -> a.is("Successful"))
						.choiceField("Severity", a -> a.is("Major"))
						.textField("My Text Field", a -> a.is("My Text"))
						.wikiTextField("My WikiText Field", a -> a.contains("My WikiText Text"))
						.integerField("My Integer Field", a -> a.is(42))
						.decimalField("My Decimal Field", a -> a.is(42.42))
						.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
						.booleanField("My Boolean Field", a -> a.isFalse())
						.durationField("My Duration Field", a -> a.is("12:00h"))
						.countryField("My Country Field", a -> a.sameAs(Country.HK))
						.languageField("My Language Field", a -> a.sameAs(Language.EE))
						.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
						.urlField("My Url Field", a -> a
								.isAlias("WIKI")
								.isUrl("http://wiki.com"))
						// wiki link
						.urlField("My Wiki Link Field", a -> a
								.isAlias("testTrackerItem")
								.isUrl("ISSUE:%d".formatted(Integer.valueOf(testCase.id()))))
						.referenceField("Subject", a -> a.is(basicRadioTrackerItem))
						.referenceField("My Reference Field", a -> a.is(trackerItemB))
						.memberField("Assigned to", a -> a
								.contains(systemUser)
								.sameAs(developer, systemUser))
						);
	
		trackerItemEditPage
			.save()
			.redirectedToTrackerItemPage()
			.assertFieldFormComponent(f -> f
					.description(a -> a.is("Test Description"))
					.choiceField("Priority", a -> a.is("Low"))
					.choiceField("Resolution", a -> a.is("Successful"))
					.choiceField("Severity", a -> a.is("Major"))
					.textField("My Text Field", a -> a.is("My Text"))
					.wikiTextField("My WikiText Field", a -> a.contains("My WikiText Text"))
					.integerField("My Integer Field", a -> a.is(42))
					.decimalField("My Decimal Field", a -> a.is(42.42))
					.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
					.booleanField("My Boolean Field", a -> a.isFalse())
					.durationField("My Duration Field", a -> a.is("12:00h"))
					.countryField("My Country Field", a -> a.sameAs(Country.HK))
					.languageField("My Language Field", a -> a.sameAs(Language.EE))
					.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
					.urlField("My Url Field", a -> a
							.isAlias("WIKI")
							.isUrl("http://wiki.com"))
					.urlField("My Wiki Link Field", a -> a
							.isAlias("testTrackerItem")
							.isWikiLink("/item/%d".formatted(Integer.valueOf(testCase.id()))))
					.referenceField("Subject", a -> a.contains(basicRadioTrackerItem))
					.referenceField("My Reference Field", a -> a.contains(trackerItemB))
					.memberField("Assigned to", a -> a
							.contains(systemUser)
							.sameAs(developer, systemUser)))
			.edit()
			.assertFieldFormComponent(f -> f
					.textField("Summary", a -> a.is("Test Summary"))
					.wikiTextField("Description", a -> a.is("Test Description"))
					.choiceField("Priority", a -> a.is("Low"))
					.choiceField("Resolution", a -> a.is("Successful"))
					.choiceField("Severity", a -> a.is("Major"))
					.textField("My Text Field", a -> a.is("My Text"))
					.wikiTextField("My WikiText Field", a -> a.contains("My WikiText Text"))
					.integerField("My Integer Field", a -> a.is(42))
					.decimalField("My Decimal Field", a -> a.is(42.42))
					.dateField("My Date Field", a -> a.is(1984, 8, 20, 11, 58))
					.booleanField("My Boolean Field", a -> a.isFalse())
					.durationField("My Duration Field", a -> a.is("12:00h"))
					.countryField("My Country Field", a -> a.sameAs(Country.HK))
					.languageField("My Language Field", a -> a.sameAs(Language.EE))
					.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_005C50))
					.urlField("My Url Field", a -> a
							.isAlias("WIKI")
							.isUrl("http://wiki.com"))
					.urlField("My Wiki Link Field", a -> a
							.isAlias("testTrackerItem")
							.isWikiLink(String.valueOf(testCase.id())))
					.referenceField("Subject", a -> a.is(basicRadioTrackerItem))
					.referenceField("My Reference Field", a -> a.is(trackerItemB))
					.memberField("Assigned to", a -> a
							.contains(systemUser)
							.sameAs(developer, systemUser))
					);
		
	}

	@Test(description = "User is able to inline edit field values")
	public void userIsAbleToInlineEditFieldValues() {
		// Given
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);

		User systemUser = getDataManagerService().getUserApiServiceWithConfigUser().findUserByName("bond");
		Role developer = getDataManagerService().getRoleApiService(projectAdmin).findRoleByName("Developer");
		TrackerItem basicRadioTrackerItem = trackerItemApiService.findTrackerItemByName(userStoryTracker, "Basic Radio");
		TrackerItem trackerItemB = trackerItemApiService.findTrackerItemByName(userStoryTracker, "MyTrackerItem - B");

		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTasks", builder -> builder
				.name("Test Summary")
				.description("Test Description")
				.priority("Low")
				.resolution("Successful")
				.severity("Major")
				.setTextFor("My Text Field", "My Text")
				.setIntegerFor("My Integer Field", 42)
				.setDecimalFor("My Decimal Field", 42.42)
				.setDateFor("My Date Field", 1984, 8, 20, 11, 58)
				.setBooleanFor("My Boolean Field", true)
				.setLanguageFor("My Language Field", Language.AE, Language.GA)
				.setCountryFor("My Country Field", Country.AD, Country.AI)
				.setWikiTextFor("My WikiText Field", "My WikiText Text")
				.setDurationFor("My Duration Field", Duration.ofHours(1))
				.setColorFor("My Color Field", HtmlColor.COLOR_005C50)
				.setUrlFor("My Url Field", "MYCOM", "http://my.com")
				.setUrlFor("My Simple Url Field", "http://my.com")
				.setWikiLinkFor("My Wiki Link Field", userStory)
				.setWikiLinkFor("My Other Wiki Link Field", testCase)
				.setTrackerItemFor("My Reference Field", project, "MyUserStory", "Available Radio Stations", "Basic Radio")
				.setTrackerItemFor("Subject", project, "MyUserStory", "Basic Radio")
				.setUserFor("Assigned to", projectAdmin.getName(), "bond")
				.setRoleFor("Assigned to", "Developer", "Tester"));

		// When / Then
		getClassicCodebeamerApplication(projectAdmin)
				.visitTrackerItemPage(taskTracker, trackerItemId)
				.fieldFormComponent(form -> form
						.editBooleanField("My Boolean Field", false)
						.editDateField("My Date Field", 1988, 8, 21, 11, 58)
						.editIntegerField("My Integer Field", 55)
						.editDecimalField("My Decimal Field", 54.31)
						.editDurationField("My Duration Field", "12h")
						.editColorField("My Color Field", HtmlColor.COLOR_454545)
						.editWikiText("My WikiText Field", f -> f
								.setComment("My WikiText Text")
								.addAttachment(getFilePath("test-file.txt"))
								.addAttachment(getFilePath("test-file.tar.gz"))
								.addAttachment(getFilePath("test-file.zip"))
								.save())
						.editWikiText("My WikiText Field", "My WikiText Text changed")
						.assertThat(a -> a.textField("My Text Field", t -> t.is("My Text")))
						.editTextField("My Text Field", "text changed")
						.editChoiceField("Severity", "Minor")
						// Country
						.clearCountryField("My Country Field")
						.setCountryField("My Country Field", Country.BA)
						.removeCountryField("My Country Field", Country.BA)
						.setCountryField("My Country Field", Country.HK)
						// Language
						.clearLanguageField("My Language Field")
						.setLanguageField("My Language Field", Language.HU)
						.removeLanguageField("My Language Field", Language.HU)
						.setLanguageField("My Language Field", Language.EE)
						// Members
						.clearMemberField("Assigned to")
						.addMemberTo("Assigned to",
								new UserReference("bond"),
								new RoleReference("Developer"))
						.removeMemberFrom("Assigned to", new UserReference("bond"))
						.addMemberTo("Assigned to", new UserReference("bond"))
						// URL - WIKI
						.clearUrlField("My Url Field")
						.editUrlField("My Url Field", "http://wiki.com", "WIKI")
						.removeUrlFrom("My Wiki Link Field", "Basic Radio")
						.editWikiLinkField("My Wiki Link Field", testCase, "testTrackerItem")
						// Reference
						.clearReferenceField("My Reference Field")
						.addReferenceTo("My Reference Field", "MyTrackerItem - A")
						.removeReferenceFrom("My Reference Field", "MyTrackerItem - A")
						.addReferenceTo("My Reference Field", "MyTrackerItem - B")
				)
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
				.assertFieldFormComponent(f -> f
						.choiceField("Priority", a -> a.is("Low"))
						.choiceField("Resolution", a -> a.is("Successful"))
						.choiceField("Severity", a -> a.is("Minor"))
						.textField("My Text Field", a -> a.is("text changed"))
						.wikiTextField("My WikiText Field", a -> a.contains("My WikiText Text changed"))
						.integerField("My Integer Field", a -> a.is(55))
						.decimalField("My Decimal Field", a -> a.is(54.31))
						.dateField("My Date Field", a -> a.is(1988, 8, 21, 11, 58))
						.booleanField("My Boolean Field", a -> a.isFalse())
						.durationField("My Duration Field", a -> a.is("12:00h"))
						.countryField("My Country Field", a -> a.sameAs(Country.HK))
						.languageField("My Language Field", a -> a.sameAs(Language.EE))
						.colorField("My Color Field", a -> a.is(HtmlColor.COLOR_454545))
						.urlField("My Url Field", a -> a
								.isAlias("WIKI")
								.isUrl("http://wiki.com"))
						.urlField("My Wiki Link Field", a -> a
								.isAlias("testTrackerItem")
								.isWikiLink(String.valueOf(testCase.id())))
						.referenceField("Subject", a -> a.containsOnly(basicRadioTrackerItem))
						.referenceField("My Reference Field", a -> a.containsOnly(trackerItemB))
						.memberField("Assigned to", a -> a
								.contains(systemUser)
								.sameAs(developer, systemUser))
				);
	}
	
}
