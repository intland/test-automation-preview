package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

public class ReleaseDashboardReleaseAssertions extends
		AbstractReleaseDashboardReleaseAssertions<ReleaseDashboardReleaseComponent, ReleaseDashboardReleaseAssertions> {

	protected ReleaseDashboardReleaseAssertions(ReleaseDashboardReleaseComponent component) {
		super(component);
	}

	public ReleaseDashboardReleaseAssertions isTransitionAvailable(List<String> transitions) {
		return assertAll("Should have an entry for '%s'".formatted(transitions), () -> {
			for (String transition : transitions) {
				assertThat(getComponent().getTransition(transition)).isVisible();
			}
		});
	}

	public ReleaseDashboardReleaseAssertions isReleaseDescriptionVisible() {
		return assertAll("Release description should be visible.",
				() -> assertThat(getComponent().getReleaseDescription()).isVisible());
	}
}
