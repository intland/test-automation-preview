package com.intland.codebeamer.integration.classic.page.trackeritem.component.artifactlinksdialog;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ArtifactLinksNavigation {

	private final CodebeamerPage codebeamerPage;

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	public ArtifactLinksNavigation(CodebeamerPage codebeamerPage,
			TrackerId trackerId, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	@Action("navigatedToTrackerItemCreatePage")
	public TrackerItemCreatePage navigatedToTrackerItemCreatePage() {
		return new TrackerItemCreatePage(codebeamerPage, trackerId).isActive();
	}

	@Action("navigatedToTrackerItemEditPage")
	public TrackerItemEditPage navigatedToTrackerItemEditPage() {
		return new TrackerItemEditPage(codebeamerPage, trackerId, trackerItemId).isActive();
	}
}
