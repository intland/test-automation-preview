package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleasesNavigationTreeComponent extends AbstractCodebeamerComponent<ReleasesNavigationTreeComponent, ReleasesNavigationTreeAssertions> {

	protected ReleasesNavigationTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div#releaseTreeContainer");
	}

	public CodebeamerLocator getTreeItem(TrackerItemId trackerItemId) {
		return this.locator("ul li[data-target-version-id='%d']".formatted(trackerItemId.id()));
	}

	public List<CodebeamerLocator> getTreeItems() {
		waitForNetworkIdle();
		return this.locator("ul li").all();
	}

	@Override
	public ReleasesNavigationTreeAssertions assertThat() {
		return new ReleasesNavigationTreeAssertions(this);
	}
}
