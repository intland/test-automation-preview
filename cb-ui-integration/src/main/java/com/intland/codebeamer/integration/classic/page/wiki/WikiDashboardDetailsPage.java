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

package com.intland.codebeamer.integration.classic.page.wiki;

import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("Wiki/dashboard details page")
public class WikiDashboardDetailsPage extends AbstractCodebeamerPage<WikiDashboardDetailsPage> {

	private static final String SELECTED_WIKI_PAGE_PATH = "wiki/properties/";

	private static final String SELECTED_WIKI_PAGE_PATTERN = ".*/wiki/properties/\\d+";

	private final String selectedWikiPath;

	public WikiDashboardDetailsPage(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, resolveIdFromUrl(codebeamerPage));
	}

	public WikiDashboardDetailsPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage);
		this.selectedWikiPath = SELECTED_WIKI_PAGE_PATH + wikiPageId;
	}

	@Action("Visit")
	public WikiDashboardDetailsPage visit() {
		navigate(selectedWikiPath);
		return isActive();
	}

	@Override
	public WikiDashboardDetailsPage isActive() {
		assertUrl(selectedWikiPath, "Selected wiki/dashboard details page should be the active page");
		return this;
	}

	@Override
	public WikiDashboardDetailsPage assertPage(Consumer<WikiDashboardDetailsPage> assertion) {
		assertion.accept(this);
		return this;
	}

	private static Integer resolveIdFromUrl(CodebeamerPage codebeamerPage) {
		codebeamerPage.waitForUrlRegexp(SELECTED_WIKI_PAGE_PATTERN);
		return Integer.valueOf(StringUtils.substringAfter(codebeamerPage.getPageUrl(), SELECTED_WIKI_PAGE_PATH));
	}

}
