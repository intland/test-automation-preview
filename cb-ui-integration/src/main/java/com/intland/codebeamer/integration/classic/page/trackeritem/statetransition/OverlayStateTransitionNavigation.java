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

package com.intland.codebeamer.integration.classic.page.trackeritem.statetransition;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class OverlayStateTransitionNavigation {

	private CodebeamerPage codebeamerPage;

	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	public OverlayStateTransitionNavigation(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	@Action("redirectedToTrackerItemPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId).isActive();
	}
}
