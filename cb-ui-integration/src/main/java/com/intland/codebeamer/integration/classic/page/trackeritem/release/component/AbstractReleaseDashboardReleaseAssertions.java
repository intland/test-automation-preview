package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public abstract class AbstractReleaseDashboardReleaseAssertions<C extends AbstractReleaseDashboardReleaseComponent<C, A>, A extends AbstractReleaseDashboardReleaseAssertions<C, A>>
		extends
		AbstractCodebeamerComponentAssert<C, A> {

	private static final Pattern EXPANDED_CLASS_PATTERN = Pattern.compile("(^expanded$)|(^expanded\\s)|(\\sexpanded\\s)|(\\sexpanded$)");

	protected AbstractReleaseDashboardReleaseAssertions(C component) {
		super(component);
	}

	public A isExpanded() {
		return assertAll("Should be expanded", () -> assertThat(getComponent().getExpander())
				.hasClass(EXPANDED_CLASS_PATTERN));
	}

	public A isClosed() {
		return assertAll("Should be expanded", () -> assertThat(getComponent().getExpander())
				.not().hasClass(EXPANDED_CLASS_PATTERN));
	}

	public A hasVersionBadge() {
		return hasVersionBadge(1);
	}

	public A hasVersionBadge(int versionNumber) {
		return assertAll("Should have version badge with version number '%d'".formatted(versionNumber), () -> {
			CodebeamerLocator versionBadge = getComponent().getVersionBadge();
			assertThat(versionBadge).isVisible();
			assertThat(versionBadge).hasText("v%d".formatted(versionNumber));
		});
	}

	public A isLinkPointingToBaseline(BaselineId baselineId) {
		return assertAll("Link should point to baseline '%d'".formatted(baselineId.id()), () -> {
			assertThat(getComponent().getReleaseItemLink()).hasAttribute("href", Pattern.compile("revision=%s$".formatted(baselineId.id())));
		});
	}

	public A isHeaderPointingToReleaseStatsPage(TrackerItemId releaseItemId) {
		return assertAll("Release name is a link to release stats page", () ->
				assertThat(getComponent().getReleaseItemLink()).hasAttribute("href",
						Pattern.compile("/item/%s/stats".formatted(Integer.valueOf(releaseItemId.id()))))
		);
	}

	public A verifyFirstStatsLineInHeader(String sprints, double percentage, String closed, String open) {
		return assertAll(
				"The 1st stats line in the header includes: %s - %,.1f%% - %s - %s".formatted(sprints, Double.valueOf(percentage),
						closed, open), () -> {
					assertThat(getComponent().getNumberOfSprints()).hasText(sprints);
					assertThat(getComponent().getSprintsProgressBar()).hasText(Pattern.compile("%d%%|%,.1f%%".formatted(
							Integer.valueOf((int) percentage),
							Double.valueOf(percentage))));
					assertThat(getComponent().getNumberOfClosedSprints()).hasText(closed);
					assertThat(getComponent().getNumberOfOpenSprints()).hasText(open);
				}
		);
	}

	public A canBeOpenedAndClosed() {
		return assertAll("Should be able to open and close it", () -> {
			getComponent().expand();
			isExpanded();
			getComponent().close();
			isClosed();
		});
	}

}
