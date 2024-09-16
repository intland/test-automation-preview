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

package com.intland.codebeamer.integration.api.service.reports;

import java.util.Date;

import com.intland.swagger.client.model.SaveReportRequest;

public class ReportInformationBuilder {

	private SaveReportRequest saveReportRequest;

	public ReportInformationBuilder(String name) {
		this.saveReportRequest = getReportRequest(name);
	}

	public ReportInformationBuilder cbql(String cbql) {
		saveReportRequest.setCbQl(cbql);
		return this;
	}

	public ReportInformationBuilder addedPermissions(String addedPermissions) {
		saveReportRequest.setAddedPermissions(addedPermissions);
		return this;
	}

	public ReportInformationBuilder advanced(Boolean advanced) {
		saveReportRequest.setAdvanced(advanced);
		return this;
	}

	public ReportInformationBuilder description(String description) {
		saveReportRequest.setDescription(description);
		return this;
	}

	public ReportInformationBuilder duplicate(Boolean duplicate) {
		saveReportRequest.setDuplicate(duplicate);
		return this;
	}

	public ReportInformationBuilder extraInfoString(String extraInfoString) {
		saveReportRequest.setExtraInfoString(extraInfoString);
		return this;
	}

	public ReportInformationBuilder advanced(String fields) {
		saveReportRequest.setFields(fields);
		return this;
	}

	public ReportInformationBuilder historyBaselineId(Integer historyBaselineId) {
		saveReportRequest.setHistoryBaselineId(historyBaselineId);
		return this;
	}

	public ReportInformationBuilder historyDate(Date historyDate) {
		saveReportRequest.setHistoryDate(historyDate);
		return this;
	}

	public ReportInformationBuilder isPublic(Boolean isPublic) {
		saveReportRequest.setIsPublic(isPublic);
		return this;
	}

	public ReportInformationBuilder logicSlicesString(String logicSlicesString) {
		saveReportRequest.setLogicSlicesString(logicSlicesString);
		return this;
	}

	public ReportInformationBuilder logicString(String logicString) {
		saveReportRequest.setLogicString(logicString);
		return this;
	}

	public ReportInformationBuilder queryId(Integer queryId) {
		saveReportRequest.setQueryId(queryId);
		return this;
	}

	public ReportInformationBuilder showAncestorItems(Boolean showAncestorItems) {
		saveReportRequest.setShowAncestorItems(showAncestorItems);
		return this;
	}

	public ReportInformationBuilder showDescendantItems(Boolean showDescendantItems) {
		saveReportRequest.setShowDescendantItems(showDescendantItems);
		return this;
	}

	public ReportInformationBuilder tablePageSize(Integer tablePageSize) {
		saveReportRequest.setTablePageSize(tablePageSize);
		return this;
	}

	public SaveReportRequest build() {
		return saveReportRequest;
	}

	private SaveReportRequest getReportRequest(String name) {
		return new SaveReportRequest()
				.name(name);
	}
}