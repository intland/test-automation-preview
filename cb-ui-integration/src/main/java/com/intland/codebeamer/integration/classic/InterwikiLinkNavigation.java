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

package com.intland.codebeamer.integration.classic;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class InterwikiLinkNavigation {

	protected final CodebeamerPage codebeamerPage;

	public InterwikiLinkNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToTrackerItemPage")
	public TrackerItemPage redirectedToTrackerItemPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId).isActive();
	}
}
