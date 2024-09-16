package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.manageitemtemplates.edititemtemplates;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class EditItemTemplatesDialog extends AbstractCodebeamerDialog {

	private final Tracker tracker;

	@Component("Edit item templates form")
	private final EditItemTemplatesFormComponent editItemTemplatesFormComponent;

	public EditItemTemplatesDialog(CodebeamerPage codebeamerPage, Tracker tracker) {
		// UI-AUTOMATION: on tracker table view page, the edit templates dialog needs a proper identifier
		super(codebeamerPage, "#inlinedPopupIframe[src*='/itemtemplates/manageDialog.spr?trackerId=%s']".formatted(
				Integer.valueOf(tracker.id().id())));
		this.tracker = tracker;
		this.editItemTemplatesFormComponent = new EditItemTemplatesFormComponent(getCodebeamerPage(), this.getDialogLocator());
	}

	public EditItemTemplatesDialog editItemTemplateComponent(Consumer<EditItemTemplatesFormComponent> formConsumer) {
		formConsumer.accept(this.editItemTemplatesFormComponent);
		return this;
	}

	public EditItemTemplatesNavigation save() {
		this.editItemTemplatesFormComponent.save();
		return new EditItemTemplatesNavigation(getCodebeamerPage(), tracker);
	}

	public EditItemTemplatesDialog isActive() {
		return this;
	}

}
