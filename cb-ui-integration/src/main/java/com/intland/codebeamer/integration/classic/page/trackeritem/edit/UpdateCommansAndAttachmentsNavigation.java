package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class UpdateCommansAndAttachmentsNavigation {

	private CodebeamerPage codebeamerPage;
	
	private TrackerItemId trackerItemId;

	public UpdateCommansAndAttachmentsNavigation(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		this.codebeamerPage = codebeamerPage;
		this.trackerItemId = trackerItemId;
	}

	@Action("redirectedToLoginPage")
	public TrackerItemPage redirectedToTrackerItemPage() {
		return new TrackerItemPage(codebeamerPage, this.trackerItemId).isActive();
	}

	@Action("redirectedToUpdateCommansAndAttachmentsDialog")
	public UpdateCommansAndAttachmentsDialog redirectedToUserMyWikiPage() {
		return new UpdateCommansAndAttachmentsDialog(codebeamerPage, trackerItemId).isActive();
	}
	
}
