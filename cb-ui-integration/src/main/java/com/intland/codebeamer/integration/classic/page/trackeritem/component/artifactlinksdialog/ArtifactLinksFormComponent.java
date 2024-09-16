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

package com.intland.codebeamer.integration.classic.page.trackeritem.component.artifactlinksdialog;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class ArtifactLinksFormComponent
		extends AbstractCodebeamerComponent<ArtifactLinksFormComponent, ArtifactLinksFormAssertions> {

	public ArtifactLinksFormComponent(CodebeamerPage codebeamerPage, String frameLocator) {
		super(codebeamerPage, frameLocator, "div.contentArea");
	}

	public ArtifactLinksFormComponent setUrl(String url, String alias) {
		getAliasField().fill(alias);
		return setUrl(url);
	}

	public ArtifactLinksFormComponent setUrl(String url) {
		getUrlField().fill(url);
		return this;
	}

	public ArtifactLinksFormComponent setWikiLink(TrackerItemId id, String alias) {
		getAliasField().fill(alias);
		return setWikiLink(id);
	}

	public ArtifactLinksFormComponent setWikiLink(TrackerItemId id) {
		getUrlField().fill("[ISSUE:%d]".formatted(Integer.valueOf(id.id())));
		return this;
	}

	public void insert() {
		getInsertButton().click();
	}

	public CodebeamerLocator getInsertButton() {
		return this.locator("input[type='button'][value='Insert']");
	}

	public CodebeamerLocator getUrlField() {
		return this.locator("input#wikiLink");
	}

	public CodebeamerLocator getAliasField() {
		return this.locator("div#customWikiLinkTabPane input[type='text'][name='alias']");
	}

	public ArtifactLinksFormComponent switchToWikiLinkUrlTab() {
		this.locator("div#customWikiLinkTabPane-tab").click();
		return this;
	}

	@Override
	public ArtifactLinksFormAssertions assertThat() {
		return new ArtifactLinksFormAssertions(this);
	}

}
