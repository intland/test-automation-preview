/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class TrackerItemEditPageNavigation {

	private static final Logger logger = LogManager.getLogger(TrackerItemEditPageNavigation.class);

	private static final String TRACKER_ITEM_URL_PATTERN = ".*/(issue|item)/\\d+";

	private CodebeamerPage codebeamerPage;

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	public TrackerItemEditPageNavigation(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	@Action("redirectedToTrackerItemEditPage")
	public TrackerItemEditPage redirectedToTrackerItemEditPage() {
		return new TrackerItemEditPage(codebeamerPage, trackerId, trackerItemId).isActive();
	}

	@Action("redirectedToTrackerItemPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, trackerId, getTrackerItemId()).isActive();
	}

	private TrackerItemId getTrackerItemId() {
		codebeamerPage.waitForUrlRegexp(TRACKER_ITEM_URL_PATTERN);
		String pageUrl = codebeamerPage.getPageUrl();

		logger.info("Getting tracker item id from url: {}", pageUrl);

		return new TrackerItemId(Integer
				.valueOf(Objects.requireNonNull(getIdFromUrl(pageUrl), "ID of tracker item cannot be computed from %s"
						.formatted(pageUrl)))
				.intValue());
	}

	private String getIdFromUrl(String pageUrl) {
		return StringUtils.substringAfterLast(pageUrl, "/");
	}

}
