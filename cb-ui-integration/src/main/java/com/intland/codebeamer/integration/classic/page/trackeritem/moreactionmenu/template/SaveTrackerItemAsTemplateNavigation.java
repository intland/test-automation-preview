package com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu.template;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class SaveTrackerItemAsTemplateNavigation {

	private final CodebeamerPage codebeamerPage;

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	public SaveTrackerItemAsTemplateNavigation(CodebeamerPage codebeamerPage,
			TrackerId trackerId, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	@Action("navigatedToTrackerItemPage")
	public TrackerItemPage navigatedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId).isActive();
	}
}
