package com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu.template.SaveTrackerItemAsTemplateDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class MoreActionMenuComponent extends AbstractCodebeamerComponent<MoreActionMenuComponent, MoreActionMenuAssertions> {

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	public MoreActionMenuComponent(CodebeamerPage codebeamerPage, String selector, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, selector);
		this.trackerId = trackerId;
		this.trackerItemId = trackerItemId;
	}

	public SaveTrackerItemAsTemplateDialog selectSaveItemAsTemplate() {
		getCreateTemplateFromItemLink().click();
		return new SaveTrackerItemAsTemplateDialog(getCodebeamerPage(), trackerId, trackerItemId);
	}
	
	public CodebeamerLocator getCreateTemplateFromItemLink() {
		return locatorByTestId("saveAsItemTemplateButton");
	}

	public CodebeamerLocator getCopyWorkItemButton() {
		return locatorByTestId("copyWikiLinkButton");
	}
	
	@Override
	public MoreActionMenuAssertions assertThat() {
		return new MoreActionMenuAssertions(this);
	}

}
