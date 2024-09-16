package com.intland.codebeamer.integration.classic.page.trackeritem.component;

public class TrackerItemHistoryTabAssertions
		extends AbstractTrackerItemComponentTabAssertion<TrackerItemHistoryTabComponent, TrackerItemHistoryTabAssertions> {

	protected TrackerItemHistoryTabAssertions(TrackerItemHistoryTabComponent component) {
		super(component);
	}

	/**
	 * It shows the number of history elements shown in the history tab. It may not shows all the history items, if there's to many.
	 */
	public TrackerItemHistoryTabAssertions hasNumberOfHistoryItemsShown(int num) {
		return assertAll("Number of history items should be %d".formatted(Integer.valueOf(num)),
				() -> assertThat(getComponent().getHistoryListLocator()).hasCount(num));
	}

	/**
	 * Returns the total number of history item, if the 'show all' button is present.
	 */
	public TrackerItemHistoryTabAssertions hasTotalNumberOfHistoryItems(int num) {
		return hasAllHistoryItemShown().assertAll("Total number of history items should be %d".formatted(Integer.valueOf(num)),
				() -> assertThat(getComponent().getHistoryListSizeLocator()).hasAttribute("data-item-version", Integer.toString(num)));
	}

	public TrackerItemHistoryTabAssertions hasAllHistoryItemShown() {
		return assertAll("All history items are shown on the page",
				() -> assertThat(getComponent().getShowAllHistoryItemButton()).isVisible());
	}
}
