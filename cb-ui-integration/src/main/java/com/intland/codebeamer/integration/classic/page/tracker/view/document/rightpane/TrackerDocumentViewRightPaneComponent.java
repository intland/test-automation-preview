package com.intland.codebeamer.integration.classic.page.tracker.view.document.rightpane;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.rightpane.properties.PropertiesTabComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerDocumentViewRightPaneComponent
		extends AbstractCodebeamerComponent<TrackerDocumentViewRightPaneComponent, TrackerDocumentViewRightPaneAssertions> {

	@Component("Properties tab")
	private final PropertiesTabComponent propertiesTab;

	public TrackerDocumentViewRightPaneComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#east");
		this.propertiesTab = new PropertiesTabComponent(codebeamerPage, getSelector());
	}

	public TrackerDocumentViewRightPaneComponent changeToPropertiesTab() {
		propertiesTab.activateTab();
		return this;
	}

	public TrackerDocumentViewRightPaneComponent propertiesTab(Consumer<PropertiesTabComponent> formConsumer) {
		formConsumer.accept(propertiesTab);
		return this;
	}

	@Override
	public TrackerDocumentViewRightPaneAssertions assertThat() {
		return new TrackerDocumentViewRightPaneAssertions(this);
	}

}
