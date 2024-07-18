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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.component.actionbar.AbstractWikiDashboardActionbarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

public abstract class AbstractWikiDashboardPage extends AbstractCodebeamerPage<AbstractWikiDashboardPage> {

	private static final Logger logger = LogManager.getLogger(AbstractWikiDashboardPage.class);

	private static final String SELECTED_WIKI_PAGE_PATH = "wiki" + SLASH;

	private static final String SELECTED_WIKI_PAGE_PATTERN = ".*/wiki/\\d+";

	private final String selectedWikiPath;

	public AbstractWikiDashboardPage(CodebeamerPage codebeamerPage) {
		this(codebeamerPage, resolveIdFromUrl(codebeamerPage));
	}

	public AbstractWikiDashboardPage(CodebeamerPage codebeamerPage, Integer wikiPageId) {
		super(codebeamerPage);
		this.selectedWikiPath = SELECTED_WIKI_PAGE_PATH + wikiPageId;
		logger.debug("New wiki page created. Url: {}", this.selectedWikiPath);
	}

	public abstract AbstractWikiDashboardActionbarComponent getActionbarComponent();

	@Action("Visit")
	public AbstractWikiDashboardPage visit() {
		navigate(selectedWikiPath);
		return isActive();
	}

	@Override
	public AbstractWikiDashboardPage isActive() {
		assertUrl(selectedWikiPath, "Selected wiki page should be the active page");
		return this;
	}

	@Override
	public AbstractWikiDashboardPage assertPage(Consumer<AbstractWikiDashboardPage> assertion) {
		assertion.accept(this);
		return this;
	}

	private static Integer resolveIdFromUrl(CodebeamerPage codebeamerPage) {
		codebeamerPage.waitForUrlRegexp(SELECTED_WIKI_PAGE_PATTERN);
		return Integer.valueOf(StringUtils.substringAfter(codebeamerPage.getPageUrl(), SELECTED_WIKI_PAGE_PATH));
	}

}
