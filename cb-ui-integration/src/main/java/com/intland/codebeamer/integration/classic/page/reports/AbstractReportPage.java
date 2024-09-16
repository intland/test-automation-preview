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

package com.intland.codebeamer.integration.classic.page.reports;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.trackeritemtable.TrackerItemTableRowComponent;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportQueryResultComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.UriPathBuilder;

public abstract class AbstractReportPage<P extends AbstractReportPage> extends AbstractCodebeamerPage<P> {

	private static final String REPORT_PAGE_PATH = "query";

	@Component(value = "Query result container")
	protected final ReportQueryResultComponent reportQueryResultComponent;

	public AbstractReportPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.reportQueryResultComponent = new ReportQueryResultComponent(codebeamerPage);
	}

	public P trackerItemTableRowComponent(TrackerItemId trackerItemId,
			Consumer<TrackerItemTableRowComponent> consumer) {
		consumer.accept(reportQueryResultComponent.getTableRow(trackerItemId));
		return (P) this;
	}

	public P trackerTableContentComponent(Consumer<ReportQueryResultComponent> consumer) {
		consumer.accept(reportQueryResultComponent);
		return (P) this;
	}

	@Action("Visit")
	public P visit() {
		navigate(new UriPathBuilder(REPORT_PAGE_PATH).path(getPath()).build());
		return isActive();
	}

	@Override
	public P isActive() {
		assertUrl(new UriPathBuilder(REPORT_PAGE_PATH).path(getPath()).build(), "Report page should be active page");
		return (P) this;
	}

	protected abstract String getPath();
}
