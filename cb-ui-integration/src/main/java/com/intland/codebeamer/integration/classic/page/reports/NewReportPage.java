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
import com.intland.codebeamer.integration.classic.page.reports.component.ReportQueryAccordionAssertions;
import com.intland.codebeamer.integration.classic.page.reports.component.ReportQueryAccordionComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("NewReportPage")
public class NewReportPage extends AbstractReportPage<NewReportPage> {

	@Component("Report query accordion")
	private ReportQueryAccordionComponent reportQueryAccordionComponent;

	public NewReportPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.reportQueryAccordionComponent = new ReportQueryAccordionComponent(codebeamerPage);
	}

	public ReportQueryAccordionComponent getReportQueryAccordionComponent() {
		return reportQueryAccordionComponent;
	}

	public NewReportPage reportQueryAccordionComponent(Consumer<ReportQueryAccordionComponent> formConsumer) {
		formConsumer.accept(reportQueryAccordionComponent);
		return this;
	}

	public NewReportPage assertReportQueryAccordionComponent(Consumer<ReportQueryAccordionAssertions> assertion) {
		assertion.accept(getReportQueryAccordionComponent().assertThat());
		return this;
	}

	@Override
	protected String getPath() {
		return "/new";
	}
}
