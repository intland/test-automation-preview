package com.intland.codebeamer.integration.classic.page.trackeritem.component.artifactlinksdialog;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class ArtifactLinksDialog extends AbstractCodebeamerDialog {

	@Component("Artifact links form")
	private final ArtifactLinksFormComponent artifactLinksFormComponent;

	public ArtifactLinksDialog(CodebeamerPage codebeamerPage) {
		// UI-AUTOMATION: on tracker edit page, the artifact links dialog needs a proper identifier
		super(codebeamerPage, "#inlinedPopupIframe[src*='/wysiwyg/plugins/plugin.spr']");
		this.artifactLinksFormComponent = new ArtifactLinksFormComponent(getCodebeamerPage(), this.getDialogLocator());
	}

	public ArtifactLinksDialog artifactLinksComponent(Consumer<ArtifactLinksFormComponent> formConsumer) {
		formConsumer.accept(this.artifactLinksFormComponent);
		return this;
	}

	public void save() {
		this.artifactLinksFormComponent.insert();
	}

	public ArtifactLinksDialog isActive() {
		return this;
	}

}
