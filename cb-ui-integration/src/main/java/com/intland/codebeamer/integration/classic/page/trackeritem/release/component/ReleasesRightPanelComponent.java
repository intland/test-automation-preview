package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleasesRightPanelComponent extends AbstractCodebeamerComponent<ReleasesRightPanelComponent, ReleasesRightPanelAssertions> {

	@Component("Navigation tree")
	private ReleasesNavigationTreeComponent releasesNavigationTreeComponent;

	public ReleasesRightPanelComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "div.versionShortcuts");
		this.releasesNavigationTreeComponent = new ReleasesNavigationTreeComponent(codebeamerPage);
	}

	public ReleasesRightPanelComponent releasesNavigationTree(Consumer<ReleasesNavigationTreeComponent> consumer) {
		consumer.accept(this.releasesNavigationTreeComponent);
		return this;
	}

	@Override
	public ReleasesRightPanelAssertions assertThat() {
		return new ReleasesRightPanelAssertions(this);
	}
}
