package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.ArrayList;
import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class TrackerItemHistoryTabComponent
		extends AbstractTrackerItemComponentTab<TrackerItemHistoryTabComponent, TrackerItemHistoryTabAssertions> {

	@Component("History component list")
	private List<TrackerItemHistoryComponent> trackerItemHistoryComponents;

	public TrackerItemHistoryTabComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#task-details #task-details-history");
	}

	public List<TrackerItemHistoryComponent> getHistoryList() {
		if (trackerItemHistoryComponents == null) {
			reloadHistoryList();
		}
		return trackerItemHistoryComponents;
	}

	public CodebeamerLocator getHistoryListLocator() {
		return locator("#changeSet > tbody > tr");
	}

	public CodebeamerLocator getHistoryListSizeLocator() {
		return this.locator(".show-all-history-link");
	}

	public List<TrackerItemHistoryComponent> showAllHistoryItems() {
		getShowAllHistoryItemButton().scrollIntoView().click();
		getLoadingDialogElement().waitForDetached();
		reloadHistoryList();
		return trackerItemHistoryComponents;
	}

	public CodebeamerLocator getShowAllHistoryItemButton() {
		return locator(".show-all-history-link");
	}

	@Override
	public TrackerItemHistoryTabAssertions assertThat() {
		return new TrackerItemHistoryTabAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#task-details-history-tab";
	}

	@Override
	protected String getTabName() {
		return "History";
	}

	private void reloadHistoryList() {
		trackerItemHistoryComponents = new ArrayList<>();
		int historyCount = getHistoryListLocator().all().size();
		for (int i = 1; i <= historyCount; ++i) {
			trackerItemHistoryComponents.add(
					new TrackerItemHistoryComponent(getCodebeamerPage(), "#changeSet tbody tr:nth-child(%d)".formatted(i)));
		}
	}

	private CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}
}