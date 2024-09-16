package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.generatetestruns;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.TrackerDocumentViewPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class GenerateTestRunsDialog extends AbstractCodebeamerDialog {

	@Component("Generate test runs form")
	private final GenerateTestRunsFormComponent generateTestRunsFormComponent;

	private final Tracker tracker;

	public GenerateTestRunsDialog(CodebeamerPage codebeamerPage, Tracker tracker, String frameLocator) {
		super(codebeamerPage, frameLocator);
		this.generateTestRunsFormComponent = new GenerateTestRunsFormComponent(codebeamerPage, this.getDialogLocator());
		this.tracker = tracker;
	}

	public GenerateTestRunsDialog generateTestRunsFormComponent(Consumer<GenerateTestRunsFormComponent> formConsumer) {
		formConsumer.accept(generateTestRunsFormComponent);
		return this;
	}

	public TrackerDocumentViewPage save() {
		this.generateTestRunsFormComponent.save();
		return new TrackerDocumentViewPage(getCodebeamerPage(), tracker);
	}
}