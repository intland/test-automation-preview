package com.intland.codebeamer.integration.classic.page.trackeritem.moreactionmenu.template;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class SaveTrackerItemAsTemplateDialog extends AbstractCodebeamerDialog {

	private final TrackerId trackerId;

	private final TrackerItemId trackerItemId;

	@Component("Save tracker item as template form")
	private final SaveTrackerItemAsTemplateFormComponent saveTrackerItemAsTemplateFormComponent;

	public SaveTrackerItemAsTemplateDialog(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='/itemtemplates/createDialog.spr']");
		this.trackerItemId = trackerItemId;
		this.trackerId = trackerId;
		this.saveTrackerItemAsTemplateFormComponent = new SaveTrackerItemAsTemplateFormComponent(getCodebeamerPage(),
				this.getDialogLocator());
	}

	public SaveTrackerItemAsTemplateDialog saveTrackerItemAsTemplateComponent(Consumer<SaveTrackerItemAsTemplateFormComponent> formConsumer) {
		formConsumer.accept(this.saveTrackerItemAsTemplateFormComponent);
		return this;
	}

	public SaveTrackerItemAsTemplateNavigation save() {
		this.saveTrackerItemAsTemplateFormComponent.save();
		return new SaveTrackerItemAsTemplateNavigation(getCodebeamerPage(), trackerId, trackerItemId);
	}

	public SaveTrackerItemAsTemplateDialog isActive() {
		return this;
	}

}
