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

package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class InlineActionMenuComponent
		extends AbstractCodebeamerComponent<InlineActionMenuComponent, InlineActionMenuAssertions> {
	
	private UpdateCommansAndAttachmentsDialog updateCommansAndAttachmentsDialog;

	public InlineActionMenuComponent(CodebeamerPage codebeamerPage, String selector, TrackerItemId trackerItemId) {
		super(codebeamerPage, selector);
		this.updateCommansAndAttachmentsDialog = new UpdateCommansAndAttachmentsDialog(codebeamerPage, trackerItemId);
	}

	public UpdateCommansAndAttachmentsDialog edit() {
		getEditLink().click();
		return updateCommansAndAttachmentsDialog;
	}

	public CodebeamerLocator getEditLink() {
		return this.locator("li.ui-menu-item a:text-is('Edit')");
	}

	@Override
	public InlineActionMenuAssertions assertThat() {
		return new InlineActionMenuAssertions(this);
	}

}
