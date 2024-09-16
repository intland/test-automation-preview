package com.intland.codebeamer.integration.classic.page.trackeritem.create;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class TrackerItemCreatePageNavigation {

	private static final String TRACKER_ITEM_URL_PATTERN = ".*/(issue|item)/.*";

	private CodebeamerPage codebeamerPage;

	private TrackerId trackerId;

	private String frameLocator;

	public TrackerItemCreatePageNavigation(CodebeamerPage codebeamerPage, TrackerId trackerId) {
		this(codebeamerPage, trackerId, null);
	}

	public TrackerItemCreatePageNavigation(CodebeamerPage codebeamerPage, TrackerId trackerId, String frameLocator) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.frameLocator = frameLocator;
	}

	@Action("redirectedToTrackerItemCreatePage")
	public TrackerItemCreatePage redirectedToTrackerItemCreatePage() {
		return new TrackerItemCreatePage(codebeamerPage, trackerId, frameLocator).isActive();
	}

	@Action("redirectedToTrackerItemPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, trackerId, getTrackerItemId()).isActive();
	}

	private TrackerItemId getTrackerItemId() {
		codebeamerPage.waitForUrlRegexp(TRACKER_ITEM_URL_PATTERN);
		String pageUrl = codebeamerPage.getPageUrl();
		return new TrackerItemId(Integer
				.valueOf(Objects.requireNonNull(getIdFromUrl(pageUrl), "ID of tracker item cannot be computed from %s".formatted(pageUrl)))
				.intValue());
	}

	private String getIdFromUrl(String pageUrl) {
		return StringUtils.substringAfterLast(pageUrl, "/");
	}

}
