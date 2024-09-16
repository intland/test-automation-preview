package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import static org.testng.Assert.assertTrue;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;

public class TestStepsTabComponent extends AbstractTrackerItemComponentTab<TestStepsTabComponent, TestStepsTabAssertions> {

	public TestStepsTabComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#task-details #testStepsTab");
	}

	public TestStepComponent getTestStep(int indexOfTestStep) {
		assertTrue(indexOfTestStep >= 1, "indexOfTestStep should be a positive non zero number");
		String selector = getSelector("table#testSteps > tbody > tr:nth-child(%s)").formatted(indexOfTestStep);
		return new TestStepComponent(getCodebeamerPage(), selector);
	}

	public CodebeamerLocator getTestStepsContainerElement() {
		return this.locator("table#testSteps");
	}

	public CodebeamerLocator getTestStepElement() {
		return this.locator("table#testSteps > tbody > tr");
	}

	@Override
	protected String getTabId() {
		return "#testStepsTab-tab";
	}

	@Override
	protected String getTabName() {
		return "Test Steps";
	}

	@Override
	public TestStepsTabAssertions assertThat() {
		return new TestStepsTabAssertions(this);
	}
}
