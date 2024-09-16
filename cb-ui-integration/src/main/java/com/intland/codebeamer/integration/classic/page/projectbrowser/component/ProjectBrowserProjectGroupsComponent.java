package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class ProjectBrowserProjectGroupsComponent
		extends AbstractProjectBrowserTab<ProjectBrowserProjectGroupsComponent, ProjectBrowserProjectGroupsAssertion> {
	public ProjectBrowserProjectGroupsComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, new TabId("#project-mySets"));
	}

	@Override
	public ProjectBrowserProjectGroupsAssertion assertThat() {
		return new ProjectBrowserProjectGroupsAssertion(this);
	}
}
