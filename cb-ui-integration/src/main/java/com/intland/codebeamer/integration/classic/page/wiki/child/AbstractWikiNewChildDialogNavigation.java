package com.intland.codebeamer.integration.classic.page.wiki.child;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;

public abstract class AbstractWikiNewChildDialogNavigation {

	protected final CodebeamerPage codebeamerPage;

	public AbstractWikiNewChildDialogNavigation(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	@Action("redirectedToWikiNewChildDialog")
	public abstract AbstractWikiNewChildDialog redirectedToWikiNewChildDialog();
}
