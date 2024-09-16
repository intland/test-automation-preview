package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class ProjectBrowserCompactListComponent
		extends AbstractProjectBrowserTab<ProjectBrowserCompactListComponent, ProjectBrowserCompactListAssertion> {
	public ProjectBrowserCompactListComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, new TabId("#project-list"));
	}

	@Override
	public ProjectBrowserCompactListAssertion assertThat() {
		return new ProjectBrowserCompactListAssertion(this);
	}
}
