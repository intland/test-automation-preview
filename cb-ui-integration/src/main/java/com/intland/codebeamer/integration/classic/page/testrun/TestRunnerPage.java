package com.intland.codebeamer.integration.classic.page.testrun;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.testrun.component.ReportBugComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("Test runner page")
public class TestRunnerPage extends AbstractCodebeamerPage<TestRunnerPage> {

	private static final String TEST_RUNNER_PAGE_PATH = "testset/testRunner.spr?task_id=%d&tracker_id=%d";

	private static final Pattern TEST_RUNNER_PAGE_PATTERN = Pattern.compile(".*/testRunner.spr.*");

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	@Component("Report bug")
	private final ReportBugComponent reportBugComponent;
	
	public TestRunnerPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage);
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
		this.reportBugComponent = new ReportBugComponent(codebeamerPage);
	}

	@Action("Visit")
	public TestRunnerPage visit() {
		navigate(TEST_RUNNER_PAGE_PATH.formatted(trackerItemId.id(), trackerId.id()));
		return isActive();
	}

	@Override
	public TestRunnerPage isActive() {
		assertUrl(TEST_RUNNER_PAGE_PATTERN, "Test runner page should be the active page");
		return this;
	}

	public TrackerItemPage failTestRunWithoutTestSteps(Consumer<ReportBugComponent> reportBugComponentConsumer) {
		getFailButton().click();
		reportNewBug(reportBugComponentConsumer);
		return new TrackerItemPage(getCodebeamerPage(), trackerId, trackerItemId);
	}

	public TrackerItemPage failTestRunWithTestSteps(String conclusion, Consumer<ReportBugComponent> reportBugComponentConsumer) {
		getFailButton().click();
		reportNewBug(reportBugComponentConsumer);
		if (conclusion != null) {
			getCodebeamerPage().locator("#conclusionInDialog").fill(conclusion);
		}
		getCodebeamerPage().locator(".saveButton").click();
		return new TrackerItemPage(getCodebeamerPage(), trackerId, trackerItemId);
	}

	private void reportNewBug(Consumer<ReportBugComponent> reportBugComponentConsumer) {
		if (reportBugComponentConsumer != null) {
			getCodebeamerPage().locator("#reportBugLink #reportBugButton").click();
			reportBugComponentConsumer.accept(reportBugComponent);
		}
	}

	private CodebeamerLocator getFailButton() {
		return getCodebeamerPage().locator("#buttonTable td:nth-child(2) button");
	}
}
