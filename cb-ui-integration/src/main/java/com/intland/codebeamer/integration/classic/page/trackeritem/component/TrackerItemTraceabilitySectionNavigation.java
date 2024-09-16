package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.ReleasePage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class TrackerItemTraceabilitySectionNavigation {

	private final CodebeamerPage codebeamerPage;

	public TrackerItemTraceabilitySectionNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("Redirected to Release page")
	public ReleasePage redirectedToReleasePage(TrackerItemId trackerItemId, Baseline baseline) {
		return new ReleasePage(codebeamerPage, trackerItemId, baseline).isActive();
	}
}
