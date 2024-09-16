package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import com.intland.codebeamer.integration.CodebeamerPage;

public class TrackerItemDownstreamReferencesTabComponent
		extends AbstractTrackerItemComponentTab<TrackerItemDownstreamReferencesTabComponent, TrackerItemDownstreamReferencesTabAssertions> {

	public TrackerItemDownstreamReferencesTabComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#task-details #task-referring-issues");
	}

	@Override
	public TrackerItemDownstreamReferencesTabAssertions assertThat() {
		return new TrackerItemDownstreamReferencesTabAssertions(this);
	}

	@Override
	protected String getTabId() {
		return "#task-referring-issues-tab";
	}

	@Override
	protected String getTabName() {
		return "Downstream References";
	}
}