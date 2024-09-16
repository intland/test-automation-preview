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

package com.intland.codebeamer.integration.nextgen.page.topbar.component;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TopbarComponent extends AbstractCodebeamerComponent<TopbarComponent, TopbarAssertions> {

	@Component("Profile menu component")
	private ProfileMenuComponent profileMenuComponent;

	public TopbarComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "cb-topbar");
		this.profileMenuComponent = new ProfileMenuComponent(getCodebeamerPage());
	}

	public ProfileMenuComponent getProfileMenuComponent() {
		return profileMenuComponent;
	}

	@Override
	public TopbarAssertions assertThat() {
		return new TopbarAssertions(this);
	}
}
