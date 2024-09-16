package com.intland.codebeamer.integration.classic.page.projectbrowser.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.tab.TabId;

public class ProjectBrowserAvailableToJoinComponent
		extends AbstractProjectBrowserTab<ProjectBrowserAvailableToJoinComponent, ProjectBrowserAvailableToJoinAssertion> {
	public ProjectBrowserAvailableToJoinComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, new TabId("#project-join"));
	}

	@Override
	public ProjectBrowserAvailableToJoinAssertion assertThat() {
		return new ProjectBrowserAvailableToJoinAssertion(this);
	}
}
