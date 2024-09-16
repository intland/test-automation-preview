package com.intland.codebeamer.integration.classic.page.tracker.view.document.actionbar;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.classic.component.reportselector.ReportSelectorComponent;
import com.intland.codebeamer.integration.classic.page.tracker.actionbar.TrackerActionButtonsComponent;
import com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.MoreActionMenuComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class DocumentViewCenterActionBarComponent extends
		AbstractCodebeamerComponent<DocumentViewCenterActionBarComponent, DocumentViewCenterActionBarAssertions> {

	@Component("Action buttons component")
	private final TrackerActionButtonsComponent actionButtonsComponent;

	@Component("Report selector component")
	private final ReportSelectorComponent reportSelectorComponent;

	public DocumentViewCenterActionBarComponent(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, ".actionBar.reportSelectorActionBar");
		this.actionButtonsComponent = new TrackerActionButtonsComponent(codebeamerPage, tracker);
		this.reportSelectorComponent = new ReportSelectorComponent(codebeamerPage);
	}

	@Override
	public DocumentViewCenterActionBarAssertions assertThat() {
		return new DocumentViewCenterActionBarAssertions(this);
	}

	public MoreActionMenuComponent openMoreActionMenu() {
		return actionButtonsComponent.openMoreActionMenu();
	}

	public DocumentViewCenterActionBarComponent reportSelector(Consumer<ReportSelectorComponent> consumer) {
		consumer.accept(reportSelectorComponent);
		return this;
	}
}
