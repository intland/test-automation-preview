package com.intland.codebeamer.integration.classic.page.testrun.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.AbstractTrackerItemComponentTab;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePageNavigation;

public class CreateNewBugComponentTab extends AbstractTrackerItemComponentTab<CreateNewBugComponentTab, CreateNewBugComponentTabAssertion> {

	protected CreateNewBugComponentTab(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "#createNewBug");
	}

	@Override
	protected String getTabId() {
		return "#createNewBug-tab";
	}

	@Override
	protected String getTabName() {
		return "Create a new Bug";
	}

	public CodebeamerLocator getProjects() {
		return this.locator("#project option");
	}

	public CodebeamerLocator getTrackers() {
		return this.locator("select[name='tracker_id'] option");
	}

	private int getSelectedTrackerId() {
		return Integer.parseInt(this.locator("select[name='tracker_id']").value());
	}

	public CreateNewBugComponentTab setProject(String projectName) {
		this.locator("#project").selectOption(projectName);
		return this;
	}

	public CreateNewBugComponentTab copyPropertiesFromTestRun(boolean copyProperties) {
		copyPropertiesFromTestRun().check(copyProperties);
		return this;
	}

	public CodebeamerLocator copyPropertiesFromTestRun() {
		return this.locator("#copyPropertiesFromTestRun1");
	}

	public CreateNewBugComponentTab copyPropertiesFromTestCase(boolean copyProperties) {
		copyPropertiesFromTestCase().check(copyProperties);
		return this;
	}

	public CodebeamerLocator copyPropertiesFromTestCase() {
		return this.locator("#copyPropertiesFromTestCase1");
	}

	public CreateNewBugComponentTab setTracker(String trackerName) {
		this.locator("select[name='tracker_id']").selectOption(trackerName);
		return this;
	}

	public TrackerItemCreatePageNavigation next() {
		TrackerId trackerId = new TrackerId(getSelectedTrackerId());
		this.locator("input[type='submit']").click();
		return new TrackerItemCreatePageNavigation(getCodebeamerPage(), trackerId, "#inlinedPopupIframe");
	}

	@Override
	public CreateNewBugComponentTabAssertion assertThat() {
		return new CreateNewBugComponentTabAssertion(this);
	}
}
