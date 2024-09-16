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

package com.intland.codebeamer.integration.classic.page.trackeritem.statetransition;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldEditFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class OverlayStateTransitionComponent extends
		AbstractCodebeamerComponent<OverlayStateTransitionComponent, OverlayStateTransitionAssertions> {

	private static final String TRANSITION_DIALOG_LOCATOR = "#inlinedPopupIframe[src*='issue/%d/edit']";

	@Component("Edit tracker item form")
	private final TrackerItemFieldEditFormComponent trackerItemFieldEditFormComponent;

	private final OverlayStateTransitionNavigation overlayStateTransitionNavigation;

	public OverlayStateTransitionComponent(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		// UI-AUTOMATION: state transition popup dialog needs a proper identifier
		super(codebeamerPage, TRANSITION_DIALOG_LOCATOR.formatted(Integer.valueOf(trackerItemId.id())));
		this.trackerItemFieldEditFormComponent = new TrackerItemFieldEditFormComponent(codebeamerPage, TRANSITION_DIALOG_LOCATOR.formatted(Integer.valueOf(trackerItemId.id())));
		this.overlayStateTransitionNavigation = new OverlayStateTransitionNavigation(codebeamerPage, trackerId, trackerItemId);
	}

	public OverlayStateTransitionComponent fieldFormComponent(Consumer<TrackerItemFieldEditFormComponent> formConsumer) {
		formConsumer.accept(trackerItemFieldEditFormComponent);
		return this;
	}

	public OverlayStateTransitionComponent assertFieldFormComponent(Consumer<TrackerItemFieldEditFormAssertions> assertion) {
		assertion.accept(trackerItemFieldEditFormComponent.assertThat());
		return this;
	}

	public OverlayStateTransitionComponent clickSave() {
		this.trackerItemFieldEditFormComponent.getSaveButton().click();
		return this;
	}

	public OverlayStateTransitionNavigation save() {
		this.trackerItemFieldEditFormComponent.getSaveButton().click();
		this.getLoadingDialogElement().waitForDetached();
		return overlayStateTransitionNavigation;
	}

	public OverlayStateTransitionNavigation cancel() {
		this.trackerItemFieldEditFormComponent.getCancelButton().click();
		return overlayStateTransitionNavigation;
	}

	@Override
	public OverlayStateTransitionAssertions assertThat() {
		return new OverlayStateTransitionAssertions(this);
	}

	private CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}
}
