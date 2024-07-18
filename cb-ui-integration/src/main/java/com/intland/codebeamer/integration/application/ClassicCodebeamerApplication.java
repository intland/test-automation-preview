package com.intland.codebeamer.integration.application;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.login.LoginPage;
import com.intland.codebeamer.integration.classic.page.project.ProjectLandingPage;
import com.intland.codebeamer.integration.classic.page.projectbrowser.ProjectBrowserPage;
import com.intland.codebeamer.integration.classic.page.tracker.config.TrackerConfigurationPage;
import com.intland.codebeamer.integration.classic.page.tracker.main.TrackersPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.TrackerItemImportPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.sitemap.annotation.Application;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Application("Codebeamer")
public class ClassicCodebeamerApplication {

	private CodebeamerPage codebeamerPage;

	private DataManagerService dataManagerService;

	public ClassicCodebeamerApplication(CodebeamerPage codebeamerPage, DataManagerService dataManagerService) {
		this.codebeamerPage = codebeamerPage;
		this.dataManagerService = dataManagerService;
	}

	public LoginPage visitLoginPage() {
		return new LoginPage(codebeamerPage).visit();
	}
	
	public ProjectBrowserPage visitProjectBrowserPage() {
		return new ProjectBrowserPage(codebeamerPage).visit();
	}
	
	public UserMyWikiPage visitUserMyWikiPage() {
		return new UserMyWikiPage(codebeamerPage).visit();
	}

	public ProjectLandingPage visitProjectLandingPage(Project project) {
		return new ProjectLandingPage(codebeamerPage, project).visit();
	}

	public TrackersPage visitTrackersPage(Project project) {
		return new TrackersPage(codebeamerPage, project).visit();
	}

	public TrackerItemPage visitTrackerItemPage(TrackerItemId trackerItemId) {
		return new TrackerItemPage(codebeamerPage, trackerItemId).visit();
	}

	public TrackerItemPage visitTrackerItemPage(TrackerItemId trackerItemId, Baseline baseline) {
		return new TrackerItemPage(codebeamerPage, trackerItemId, baseline).visit();
	}

	public TrackerItemCreatePage visitTrackerItemCreatePage(Tracker tracker) {
		return new TrackerItemCreatePage(dataManagerService, codebeamerPage, tracker).visit();
	}

	public TrackerConfigurationPage visitTrackerConfigPage(Tracker tracker) {
		return new TrackerConfigurationPage(codebeamerPage, tracker).visit();
	}

	public TrackerItemImportPage visitTrackerItemImportPage(Tracker tracker) {
		return new TrackerItemImportPage(codebeamerPage, tracker).visit();
	}

}
