package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.edititemtemplates;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.ManageItemTemplatesDialog;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public class EditItemTemplatesNavigation {

	private final CodebeamerPage codebeamerPage;

	private final Tracker tracker;

	public EditItemTemplatesNavigation(CodebeamerPage codebeamerPage, Tracker tracker) {
		this.codebeamerPage = codebeamerPage;
		this.tracker = tracker;
	}

	@Action("redirectedToManageItemTemplatesDialog")
	public ManageItemTemplatesDialog redirectedToManageItemTemplatesDialog() {
		return new ManageItemTemplatesDialog(codebeamerPage, tracker).isActive();
	}
}
