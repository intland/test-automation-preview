package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class UpdateCommansAndAttachmentsDialog extends AbstractCodebeamerDialog {
	
	private UpdateCommentsAndAttachmentsFormComponent commentsAndAttachmentsComponent;
	
	private TrackerItemId trackerItemId;
	
	public UpdateCommansAndAttachmentsDialog(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='/proj/tracker/addAttachment.spr']");
		this.trackerItemId = trackerItemId;
		this.commentsAndAttachmentsComponent = new UpdateCommentsAndAttachmentsFormComponent(getCodebeamerPage(), this.getDialogLocator(), trackerItemId);
	}

	public UpdateCommansAndAttachmentsDialog commentsAndAttachmentsComponent(Consumer<UpdateCommentsAndAttachmentsFormComponent> formConsumer) {
		formConsumer.accept(this.commentsAndAttachmentsComponent);
		return this;
	}

	public UpdateCommansAndAttachmentsNavigation save() {
		this.commentsAndAttachmentsComponent.save();
		return new UpdateCommansAndAttachmentsNavigation(getCodebeamerPage(), trackerItemId);
	}

	public UpdateCommansAndAttachmentsDialog isActive() {
		return this;
	}
	
}
