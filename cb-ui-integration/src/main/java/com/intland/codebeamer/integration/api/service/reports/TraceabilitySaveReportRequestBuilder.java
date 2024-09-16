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

import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.codebeamer.integration.api.service.filter.model.CbQLIntelligentTableViewConfigurationBuilder;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.ReleaseId;
import com.intland.swagger.client.model.CbQLIntelligentTableViewConfiguration;
import com.intland.swagger.client.model.TraceabilitySaveReportRequest;

public class TraceabilitySaveReportRequestBuilder {

	private TraceabilitySaveReportRequest traceabilitySaveReportRequest;

	public TraceabilitySaveReportRequestBuilder(TraceabilitySaveReportRequest traceabilitySaveReportRequest) {
		this.traceabilitySaveReportRequest = traceabilitySaveReportRequest;
	}

	public TraceabilitySaveReportRequest build() {
		return traceabilitySaveReportRequest;
	}

	public TraceabilitySaveReportRequestBuilder filter(String filter) {
		traceabilitySaveReportRequest.setFilter(filter);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder ratingFilters(String ratingFilters) {
		traceabilitySaveReportRequest.setRatingFilters(ratingFilters);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder referenceFilter(String referenceFilter) {
		traceabilitySaveReportRequest.setReferenceFilter(referenceFilter);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder branchStatusFilters(String branchStatusFilters) {
		traceabilitySaveReportRequest.setBranchStatusFilters(branchStatusFilters);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder logicString(String logicString) {
		traceabilitySaveReportRequest.setLogicString(logicString);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder logicSlicesString(String logicSlicesString) {
		traceabilitySaveReportRequest.setLogicSlicesString(logicSlicesString);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder extraInfoString(String extraInfoString) {
		traceabilitySaveReportRequest.setExtraInfoString(extraInfoString);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder showAncestorItems(Boolean showAncestorItems) {
		traceabilitySaveReportRequest.setShowAncestorItems(showAncestorItems);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder showDescendantItems(Boolean showDescendantItems) {
		traceabilitySaveReportRequest.setShowDescendantItems(showDescendantItems);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder showAllChildren(Boolean showAllChildren) {
		traceabilitySaveReportRequest.setShowAllChildren(showAllChildren);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder historyBaselineId(BaselineId historyBaselineId) {
		traceabilitySaveReportRequest.setHistoryBaselineId(historyBaselineId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder historyDate(String historyDate) {
		traceabilitySaveReportRequest.setHistoryDate(historyDate);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder name(String name) {
		traceabilitySaveReportRequest.setCbQL(name);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder description(String description) {
		traceabilitySaveReportRequest.setDescription(description);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder isPublic(Boolean isPublic) {
		traceabilitySaveReportRequest.setIsPublic(isPublic);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder cbQL(String cbQL) {
		traceabilitySaveReportRequest.setCbQL(cbQL);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder reportId(ReportId reportId) {
		traceabilitySaveReportRequest.setReportId(reportId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder projectId(ProjectId projectId) {
		traceabilitySaveReportRequest.setProjectId(projectId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder projectId(Project project) {
		projectId(project.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder trackerId(TrackerId trackerId) {
		traceabilitySaveReportRequest.setTrackerId(trackerId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder presetId(Integer presetId) {
		traceabilitySaveReportRequest.setPresetId(presetId);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder fieldId(Integer fieldId) {
		traceabilitySaveReportRequest.setFieldId(fieldId);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder releaseId(ReleaseId releaseId) {
		traceabilitySaveReportRequest.setReleaseId(releaseId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder fields(String fields) {
		traceabilitySaveReportRequest.setFields(fields);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder panelType(String panelType) {
		traceabilitySaveReportRequest.setPanelType(panelType);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder panelViewConfiguration(String panelViewConfiguration) {
		traceabilitySaveReportRequest.setPanelViewConfiguration(panelViewConfiguration);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder resizeColumnsConfiguration(String resizeColumnsConfiguration) {
		traceabilitySaveReportRequest.setResizeColumnsConfiguration(resizeColumnsConfiguration);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder sprintHistory(Boolean sprintHistory) {
		traceabilitySaveReportRequest.setSprintHistory(sprintHistory);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder columnFiltersCbQL(String columnFiltersCbQL) {
		traceabilitySaveReportRequest.setColumnFiltersCbQL(columnFiltersCbQL);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder showColumnFilters(Boolean showColumnFilters) {
		traceabilitySaveReportRequest.setShowColumnFilters(showColumnFilters);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder isDuplicate(Boolean isDuplicate) {
		traceabilitySaveReportRequest.setIsDuplicate(isDuplicate);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder permissions(String permissions) {
		traceabilitySaveReportRequest.setPermissions(permissions);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder traceabilityPresetProjectId(ProjectId traceabilityPresetProjectId) {
		traceabilitySaveReportRequest.setTraceabilityPresetProjectId(traceabilityPresetProjectId.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder traceabilityPresetProjectId(Project traceabilityPresetProject) {
		traceabilityPresetProjectId(traceabilityPresetProject.id());
		return this;
	}

	public TraceabilitySaveReportRequestBuilder isTraceabilityReport(Boolean isTraceabilityReport) {
		traceabilitySaveReportRequest.isTraceabilityReport(isTraceabilityReport);
		return this;
	}

	public TraceabilitySaveReportRequestBuilder intelligentTableViewConfig(
			Function<CbQLIntelligentTableViewConfigurationBuilder, CbQLIntelligentTableViewConfigurationBuilder> builder) {
		CbQLIntelligentTableViewConfiguration cbQLIntelligentTableViewConfiguration = builder.apply(
				new CbQLIntelligentTableViewConfigurationBuilder(new CbQLIntelligentTableViewConfiguration())).build();
		traceabilitySaveReportRequest.setIntelligentTableViewConfig(cbQLIntelligentTableViewConfiguration);
		return this;
	}
}
