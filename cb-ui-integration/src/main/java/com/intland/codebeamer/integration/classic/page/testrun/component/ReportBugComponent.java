package com.intland.codebeamer.integration.classic.page.testrun.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReportBugComponent extends AbstractCodebeamerComponent<ReportBugComponent, ReportBugAssertion> {

	@Component("Create new bug tab")
	private final CreateNewBugComponentTab createNewBugComponentTab;

	public ReportBugComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe", "#createOrSelectBug");
		this.createNewBugComponentTab = new CreateNewBugComponentTab(codebeamerPage, "#inlinedPopupIframe");
	}

	public ReportBugComponent changeToCreateNewBugTab() {
		createNewBugComponentTab.activateTab();
		return this;
	}

	public ReportBugComponent createNewBugTab(Consumer<CreateNewBugComponentTab> fromConsumer) {
		fromConsumer.accept(createNewBugComponentTab);
		return this;
	}

	public CreateNewBugComponentTab createNewBugTab() {
		return createNewBugComponentTab;
	}

	@Override
	public ReportBugAssertion assertThat() {
		return new ReportBugAssertion(this);
	}
}
