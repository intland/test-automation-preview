/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.List;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ReleaseDashboardComponent
		extends AbstractCodebeamerComponent<ReleaseDashboardComponent, ReleaseDashboardAssertions> {

	private final Tracker tracker;

	private final Baseline baseline;

	public ReleaseDashboardComponent(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, null, null);
	}

	public ReleaseDashboardComponent(CodebeamerPage codebeamerPage, Tracker tracker) {
		this(codebeamerPage, tracker, null);
	}

	public ReleaseDashboardComponent(CodebeamerPage codebeamerPage, Tracker tracker, Baseline baseline) {
		super(codebeamerPage, "#releaseDashboardContainer");
		this.tracker = tracker;
		this.baseline = baseline;
	}

	public ReleaseDashboardBacklogComponent getBacklogSectionForReleaseItem(TrackerItemId trackerItemId) {
		return new ReleaseDashboardBacklogComponent(getCodebeamerPage(), trackerItemId);
	}

	public ReleaseDashboardReleaseComponent getReleaseSection(TrackerItemId trackerItemId) {
		return new ReleaseDashboardReleaseComponent(getCodebeamerPage(), tracker, trackerItemId, baseline);
	}

	public ReleaseDashboardComponent releaseSection(TrackerItemId trackerItemId, Consumer<ReleaseDashboardReleaseComponent> consumer) {
		consumer.accept(new ReleaseDashboardReleaseComponent(getCodebeamerPage(), tracker, trackerItemId, baseline));
		return this;
	}

	public ReleaseDashboardComponent assertReleaseSection(TrackerItemId trackerItemId, Consumer<ReleaseDashboardReleaseAssertions> consumer) {
		consumer.accept(new ReleaseDashboardReleaseComponent(getCodebeamerPage(), tracker, trackerItemId).assertThat());
		return this;
	}

	public ReleaseDashboardComponent assertBacklogSection(TrackerItemId trackerItemId, Consumer<ReleaseDashboardBacklogAssertions> consumer) {
		consumer.accept(new ReleaseDashboardBacklogComponent(getCodebeamerPage(), trackerItemId).assertThat());
		return this;
	}

	public CodebeamerLocator getCompositeSprintSectionsLocatorForReleaseItem(TrackerItemId trackerItemId) {
		return this.locator("div.sprint[data-parent-id='%s']".formatted(trackerItemId.id()));
	}

	public List<CodebeamerLocator> getSprintSectionsForReleaseItem(TrackerItemId trackerItemId) {
		return getCompositeSprintSectionsLocatorForReleaseItem(trackerItemId).all();
	}

	public CodebeamerLocator getCompositeSprintSections() {
		return this.locator("div.version.sprint");
	}

	public List<CodebeamerLocator> getSprintSections() {
		return getCompositeSprintSections().all();
	}

	public ReleaseDashboardSprintComponent getSprintSection(TrackerItemId trackerItemId) {
		return new ReleaseDashboardSprintComponent(getCodebeamerPage(), tracker, trackerItemId, baseline);
	}

	public ReleaseDashboardComponent assertSprintSection(TrackerItemId trackerItemId, Consumer<ReleaseDashboardSprintAssertions> consumer) {
		consumer.accept(new ReleaseDashboardSprintComponent(getCodebeamerPage(), tracker, trackerItemId).assertThat());
		return this;
	}

	public ReleaseDashboardComponent sprintSection(TrackerItemId trackerItemId, Consumer<ReleaseDashboardSprintComponent> consumer) {
		consumer.accept(new ReleaseDashboardSprintComponent(getCodebeamerPage(), tracker, trackerItemId, baseline));
		return this;
	}

	@Override
	public ReleaseDashboardAssertions assertThat() {
		return new ReleaseDashboardAssertions(this);
	}
}
