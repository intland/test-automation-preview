package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseDashboardBacklogComponent extends AbstractCodebeamerComponent<ReleaseDashboardBacklogComponent, ReleaseDashboardBacklogAssertions> {

	protected ReleaseDashboardBacklogComponent(CodebeamerPage codebeamerPage,
			TrackerItemId trackerItemId) {
		super(codebeamerPage, "div#backlog_%d".formatted(trackerItemId.id()));
	}

	public CodebeamerLocator getSelfLocator() {
		return selfLocator();
	}

	public CodebeamerLocator getCompositeBacklogItems() {
		return this.locator("tr.trackerItem");
	}

	public List<CodebeamerLocator> getBacklogItems() {
		return getCompositeBacklogItems().all();
	}

	@Override
	public ReleaseDashboardBacklogAssertions assertThat() {
		return new ReleaseDashboardBacklogAssertions(this);
	}
}
