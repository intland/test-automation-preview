package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.TrackerTableViewPage;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.edititemtemplates.EditItemTemplatesDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class ManageItemTemplatesNavigation {

	private final CodebeamerPage codebeamerPage;

	private final Tracker tracker;

	public ManageItemTemplatesNavigation(CodebeamerPage codebeamerPage, Tracker tracker) {
		this.codebeamerPage = codebeamerPage;
		this.tracker = tracker;
	}

	@Action("redirectedToEditItemTemplatesDialog")
	public EditItemTemplatesDialog redirectedToEditItemTemplatesDialog() {
		return new EditItemTemplatesDialog(codebeamerPage, tracker).isActive();
	}

	@Action("redirectedToTrackerTableViewPage")
	public TrackerTableViewPage redirectedToTrackerTableViewPage() {
		return new TrackerTableViewPage(codebeamerPage, tracker).isActive();
	}
}
