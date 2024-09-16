package com.intland.codebeamer.integration.testcase.classic.tracker.trackertemplate;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerGeneralSettingsBuilder;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.escalation.EscalationViewFormDialog;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "TrackerTemplateTestCases")
public class TrackerTemplateTestCases extends AbstractIntegrationClassicNGTests {

	private User user;

	private Project firstProject;

	private Project secondProject;

	private Project thirdProject;

	private Tracker parentTrackerInFirstProject;

	private Tracker childTrackerInFirstProject;

	private Tracker grandChildTrackerInFirstProject;

	private Tracker childTrackerInSecondProject;

	private Tracker childTrackerInThirdProject;

	private ProjectApiService projectApiService;

	@Override
	protected void generateDataBeforeClass() {
		user = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
		projectApiService = getDataManagerService().getProjectApiService(user);
		firstProject = projectApiService.createProjectFromTemplate();
		secondProject = projectApiService.createProjectFromTemplate();
		thirdProject = projectApiService.createProjectFromTemplate();

		TrackerApiService trackerApiService = getDataManagerService().getTrackerApiService(user);
		parentTrackerInFirstProject = trackerApiService.createUserStoryTracker(firstProject.id(), getRandomString(),
				TrackerGeneralSettingsBuilder::template).buildAndAdd();
		childTrackerInFirstProject = trackerApiService.createUserStoryTracker(firstProject.id(), getRandomString(),
				builder -> builder
						.templateId(parentTrackerInFirstProject)
						.template()).buildAndAdd();
		grandChildTrackerInFirstProject = trackerApiService.createUserStoryTracker(firstProject.id(), getRandomString(),
				builder -> builder
						.templateId(childTrackerInFirstProject)).buildAndAdd();
		childTrackerInSecondProject = trackerApiService.createUserStoryTracker(secondProject.id(), getRandomString(),
				builder -> builder
						.templateId(childTrackerInFirstProject)).buildAndAdd();
		childTrackerInThirdProject = trackerApiService.createUserStoryTracker(thirdProject.id(), getRandomString(),
				builder -> builder
						.templateId(childTrackerInSecondProject)).buildAndAdd();
	}

	@Override
	protected void cleanUpDataAfterClass() {
		projectApiService.deleteProject(firstProject);
		projectApiService.deleteProject(secondProject);
		projectApiService.deleteProject(thirdProject);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(user);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13710714")
	@Test(description = """
			Changes to an original template’s escalation settings are inherited by a tracker created from a second-generation
			 template in a different project, with the second-generation template also created in another project.
			  Direct and indirect child trackers have the same escalation values.""")
	public void escalationChangeInheritanceOnTrackerDuringTemplatingOnDifferentProjectChildToGrandChild() {
		// GIVEN
		String escalationView = getRandomString();

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(user);
		addEscalationRule(application, parentTrackerInFirstProject, escalationView);

		// THEN
		assertEscalationRuleStatus(application, childTrackerInFirstProject, escalationView);
		assertEscalationRuleStatus(application, childTrackerInThirdProject, escalationView);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13710611")
	@Test(description = """
			Changes to a child template’s escalation settings are inherited by a tracker created from this template within the
			 same project.""")
	public void escalationChangeInheritanceOnTrackerDuringTemplatingOnSameProjectChildToDirectChild() {
		// GIVEN
		String escalationView = getRandomString();

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(user);
		addEscalationRule(application, childTrackerInFirstProject, escalationView);

		// THEN
		assertEscalationRuleStatus(application, grandChildTrackerInFirstProject, escalationView);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13679027")
	@Test(description = """
			Changes to a parent template’s escalation settings are inherited by a tracker created from a child template within
			 the same project.""")
	public void escalationChangeInheritanceOnTrackerDuringTemplatingOnSameProjectParentToGrandChild() {
		// GIVEN
		String escalationView = getRandomString();

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(user);
		addEscalationRule(application, parentTrackerInFirstProject, escalationView);

		// THEN
		assertEscalationRuleStatus(application, grandChildTrackerInFirstProject, escalationView);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13678999")
	@Test(description = """
			Changes to a parent template’s escalation settings are inherited by a tracker created directly from this template
			 within the same project.""")
	public void escalationChangeInheritanceOnTrackerDuringTemplatingOnSameProjectParentToDirectChild() {
		// GIVEN
		String escalationView = getRandomString();

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(user);
		addEscalationRule(application, parentTrackerInFirstProject, escalationView);

		// THEN
		assertEscalationRuleStatus(application, childTrackerInFirstProject, escalationView);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13710641")
	@Test(description = """
			Changes to an original template’s escalation settings are inherited by a tracker created from a second-generation
			 template in a different project, with the second-generation template also created in another project.""")
	public void escalationChangeInheritanceOnTrackerDuringTemplatingOnDifferentProjectParentToIndirectChild() {
		// GIVEN
		String escalationView = getRandomString();

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(user);
		addEscalationRule(application, parentTrackerInFirstProject, escalationView);

		// THEN
		assertEscalationRuleStatus(application, childTrackerInThirdProject, escalationView);
	}

	private void assertEscalationRuleStatus(ClassicCodebeamerApplication application, Tracker tracker, String escalationView) {
		application
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigEscalationTab()
				.trackerConfigEscalationTab(tab -> tab
						.assertEscalationRules(escalationView, a -> a
								.updateStatusToIs(1, "Done")));
	}

	private void addEscalationRule(ClassicCodebeamerApplication application, Tracker tracker, String escalationView) {
		application
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigEscalationTab()
				.trackerConfigEscalationTab(tab -> tab
						.addEscalationRules(escalationView, EscalationViewFormDialog::save,
								escalationForm -> escalationForm.selectStatus(1, "Done"))
						.save());
	}

}
