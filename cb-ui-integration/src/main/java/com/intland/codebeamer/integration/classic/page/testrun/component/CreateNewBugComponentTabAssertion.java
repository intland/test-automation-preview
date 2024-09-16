package com.intland.codebeamer.integration.classic.page.testrun.component;

import com.intland.codebeamer.integration.classic.page.trackeritem.component.AbstractTrackerItemComponentTabAssertion;

public class CreateNewBugComponentTabAssertion extends AbstractTrackerItemComponentTabAssertion<CreateNewBugComponentTab, CreateNewBugComponentTabAssertion> {
	protected CreateNewBugComponentTabAssertion(CreateNewBugComponentTab component) {
		super(component);
	}

	public CreateNewBugComponentTabAssertion selectableProjectsSizeIs(int projectListSize) {
		return assertAll("There should be %d selectable projects".formatted(Integer.valueOf(projectListSize)),
				() -> assertThat(getComponent().getProjects()).hasCount(projectListSize));
	}

	public CreateNewBugComponentTabAssertion selectableTrackerSizeIs(int trackerListSize) {
		return assertAll("There should be %d selectable trackers".formatted(Integer.valueOf(trackerListSize)),
				() -> assertThat(getComponent().getTrackers()).hasCount(trackerListSize));
	}

	public CreateNewBugComponentTabAssertion copyPropertiesFromTestRun(boolean copyPropertiesFromTestRun) {
		return assertAll("copyPropertiesFromTestRun should be %b".formatted(Boolean.valueOf(copyPropertiesFromTestRun)), () -> {
			if (copyPropertiesFromTestRun) {
				assertThat(getComponent().copyPropertiesFromTestRun()).isChecked();
			} else {
				assertThat(getComponent().copyPropertiesFromTestRun()).not().isChecked();
			}
		});
	}

	public CreateNewBugComponentTabAssertion copyPropertiesFromTestCase(boolean copyPropertiesFromTestCase) {
		return assertAll("copyPropertiesFromTestCase should be %b".formatted(Boolean.valueOf(copyPropertiesFromTestCase)), () -> {
			if (copyPropertiesFromTestCase) {
				assertThat(getComponent().copyPropertiesFromTestCase()).isChecked();
			} else {
				assertThat(getComponent().copyPropertiesFromTestCase()).not().isChecked();
			}
		});
	}

}
