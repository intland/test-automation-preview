package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class UpdateCommentsAndAttachmentsDialog extends AbstractCodebeamerDialog {

	@Component("Update comments form")
	private UpdateCommentsAndAttachmentsFormComponent commentsAndAttachmentsComponent;

	private UpdateCommansAndAttachmentsNavigation updateCommansAndAttachmentsNavigation;

	public UpdateCommentsAndAttachmentsDialog(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='/proj/tracker/addAttachment.spr']");
		this.commentsAndAttachmentsComponent = new UpdateCommentsAndAttachmentsFormComponent(getCodebeamerPage(),
				this.getDialogLocator(), trackerId, trackerItemId);
		this.updateCommansAndAttachmentsNavigation = new UpdateCommansAndAttachmentsNavigation(getCodebeamerPage(), trackerId,
				trackerItemId);
	}

	public UpdateCommentsAndAttachmentsDialog commentsAndAttachmentsComponent(
			Consumer<UpdateCommentsAndAttachmentsFormComponent> formConsumer) {
		formConsumer.accept(this.commentsAndAttachmentsComponent);
		return this;
	}

	public UpdateCommansAndAttachmentsNavigation save() {
		this.commentsAndAttachmentsComponent.save();
		return updateCommansAndAttachmentsNavigation;
	}

	public UpdateCommentsAndAttachmentsDialog isActive() {
		return this;
	}

}
