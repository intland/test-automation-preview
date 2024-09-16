package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UpdateCommansAndAttachmentsNavigation {

	private CodebeamerPage codebeamerPage;
	
	private TrackerId trackerId;

	private TrackerItemId trackerItemId;

	public UpdateCommansAndAttachmentsNavigation(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	@Action("redirectedToLoginPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, trackerId, this.trackerItemId).isActive();
	}

	@Action("redirectedToUpdateCommansAndAttachmentsDialog")
	public UpdateCommentsAndAttachmentsDialog redirectedToUserMyWikiPage() {
		return new UpdateCommentsAndAttachmentsDialog(codebeamerPage, trackerId, trackerItemId).isActive();
	}
	
}
