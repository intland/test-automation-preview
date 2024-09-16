package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplate;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class ManageItemTemplatesDialog extends AbstractCodebeamerDialog {

	private final Tracker tracker;

	@Component("Manage item templates form")
	private final ManageItemTemplatesFormComponent manageItemTemplatesFormComponent;

	public ManageItemTemplatesDialog(CodebeamerPage codebeamerPage, Tracker tracker) {
		// UI-AUTOMATION: on tracker table view page, the manage item templates dialog needs a proper identifier
		super(codebeamerPage, "#inlinedPopupIframe[src*='/itemtemplates/manageDialog.spr?trackerId=%s']".formatted(
				Integer.valueOf(tracker.id().id())));
		this.tracker = tracker;
		this.manageItemTemplatesFormComponent = new ManageItemTemplatesFormComponent(getCodebeamerPage(),
				this.getDialogLocator(), tracker);
	}

	public ManageItemTemplatesNavigation editTrackerItemTemplate(TrackerItemTemplate trackerItemTemplate) {
		this.manageItemTemplatesFormComponent.edit(trackerItemTemplate.id());
		return new ManageItemTemplatesNavigation(getCodebeamerPage(), tracker);
	}

	public ManageItemTemplatesNavigation editTrackerItemTemplate(String trackerItemTemplateName) {
		this.manageItemTemplatesFormComponent.edit(trackerItemTemplateName);
		return new ManageItemTemplatesNavigation(getCodebeamerPage(), tracker);
	}

	public ManageItemTemplatesNavigation close() {
		this.manageItemTemplatesFormComponent.close();
		return new ManageItemTemplatesNavigation(getCodebeamerPage(), tracker);
	}

	public ManageItemTemplatesDialog isActive() {
		return this;
	}

}
