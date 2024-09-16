package com.intland.codebeamer.integration.classic.page.trackeritem.component;

public class TestStepsTabAssertions extends AbstractTrackerItemComponentTabAssertion<TestStepsTabComponent, TestStepsTabAssertions> {

	protected TestStepsTabAssertions(TestStepsTabComponent component) {
		super(component);
	}

	public TestStepsTabAssertions hasNumberOfTestSteps(int numberOfComment) {
		return assertAll("Number of test steps should be %d".formatted(numberOfComment),
				() -> {
					assertThat(getComponent().getTestStepsContainerElement()).isAttached();
					assertThat(getComponent().getTestStepElement()).hasCount(numberOfComment);
				});
	}

	public TestStepsTabAssertions hasNoTestStep() {
		return assertAll("There should be no test step",
				() -> assertThat(getComponent().getTestStepsContainerElement()).not().isVisible());
	}
}
