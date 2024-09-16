package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormComponent;

public class GenerateTestRunsFormComponent extends TrackerItemFieldEditFormComponent {

	public GenerateTestRunsFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator);
	}

	public GenerateTestRunsFormComponent summary(String summary) {
		getSummaryField().fill(summary);
		return this;
	}

	public CodebeamerLocator getSummaryField() {
		return this.locator("input#summary");
	}

	public GenerateTestRunsFormComponent runOnlyAcceptedTestCases(boolean runOnlyAcceptedTestCases) {
		if (runOnlyAcceptedTestCases) {
			this.locator("input#runOnlyAcceptedTestCases1").click();
		} else {
			this.locator("input#runOnlyAcceptedTestCases2").click();
		}
		return this;
	}

	public GenerateTestRunsFormComponent runOnlyAcceptedTestCases() {
		return runOnlyAcceptedTestCases(true);
	}

	public void save() {
		getSaveButton().click();
		this.waitForResponse(urlEndsWith("/createtestrun.spr"), 200, 60000);
	}

	@Override
	public GenerateTestRunsFormAssertions assertThat() {
		return new GenerateTestRunsFormAssertions(this);
	}
}