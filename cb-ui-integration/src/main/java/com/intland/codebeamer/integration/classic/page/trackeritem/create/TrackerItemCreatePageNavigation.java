package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

public class TrackerItemCreatePageNavigation {

	private DataManagerService dataManagerService;

	private CodebeamerPage codebeamerPage;

	private Tracker tracker;

	public TrackerItemCreatePageNavigation(DataManagerService dataManagerService, CodebeamerPage codebeamerPage, Tracker tracker) {
		this.dataManagerService = dataManagerService;
		this.codebeamerPage = codebeamerPage;
		this.tracker = tracker;
	}

	@Action("redirectedToTrackerItemCreatePage")
	public TrackerItemCreatePage redirectedToTrackerItemCreatePage() {
		return new TrackerItemCreatePage(dataManagerService, codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToTrackerItemPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, getTrackerItemId()).isActive();
	}

	private TrackerItemId getTrackerItemId() {
		codebeamerPage.waitForURL("/issue/*");
		String pageUrl = codebeamerPage.getPageUrl();
		return new TrackerItemId(Integer
				.valueOf(Objects.requireNonNull(getIdFromUrl(pageUrl), "ID of tracker item cannot be computed from %s".formatted(pageUrl)))
				.intValue());
	}

	private String getIdFromUrl(String pageUrl) {
		return StringUtils.substringAfterLast(pageUrl, "/");
	}

}
