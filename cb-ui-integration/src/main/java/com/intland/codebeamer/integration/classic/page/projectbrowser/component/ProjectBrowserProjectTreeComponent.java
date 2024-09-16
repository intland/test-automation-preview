package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class ProjectBrowserProjectTreeComponent
		extends AbstractProjectBrowserTab<ProjectBrowserProjectTreeComponent, ProjectBrowserProjectTreeAssertion> {
	public ProjectBrowserProjectTreeComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, new TabId("#project-hierarchical-categories"));
	}

	@Override
	public ProjectBrowserProjectTreeAssertion assertThat() {
		return new ProjectBrowserProjectTreeAssertion(this);
	}
}
