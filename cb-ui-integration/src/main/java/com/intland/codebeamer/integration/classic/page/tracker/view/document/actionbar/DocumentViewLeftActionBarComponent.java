package com.intland.codebeamer.integration.classic.page.tracker.view.document.actionbar;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.actionbar.TrackerActionButtonsComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DocumentViewLeftActionBarComponent extends
		AbstractCodebeamerComponent<DocumentViewLeftActionBarComponent, DocumentViewLeftActionBarAssertions> {

	@Component("Action buttons component")
	private final TrackerActionButtonsComponent actionButtonsComponent;

	public DocumentViewLeftActionBarComponent(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "#west .actionBar");
		this.actionButtonsComponent = new TrackerActionButtonsComponent(codebeamerPage, "", tracker);
	}

	@Override
	public DocumentViewLeftActionBarAssertions assertThat() {
		return new DocumentViewLeftActionBarAssertions(this);
	}

	public TrackerItemCreatePage createTrackerItemFromTemplate(String trackerItemTemplateName) {
		return actionButtonsComponent.createTrackerItemFromTemplate(trackerItemTemplateName);
	}
}
