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

import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.ReportsApi;
import com.intland.swagger.client.model.SaveReportRequest;

public class ReportsApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger(ReportsApiService.class);

	private ReportsApi reportsApi;

	public ReportsApiService(ApplicationConfiguration applicationConfiguration) {
		super(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public ReportsApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public ReportsApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public ReportsApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.reportsApi = new ReportsApi(getUserApiClient());
	}

	public Report createReport(String name, Function<ReportInformationBuilder, ReportInformationBuilder> builder) {
		SaveReportRequest saveReportRequest = builder
				.apply(new ReportInformationBuilder(name))
				.build();
		try {
			com.intland.swagger.client.model.SaveReportResponse saveReportResponse = reportsApi.saveReport(saveReportRequest);
			return new Report(new ReportId(Objects.requireNonNull(saveReportResponse.getId()).intValue()),
					saveReportResponse.getName(),
					saveReportResponse.getQueryString());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public boolean deleteReports(Report... reports) {
		List<Integer> reportIds = Arrays.stream(reports)
				.map(report -> report.id().id())
				.toList();
		if (CollectionUtils.isEmpty(reportIds)) {
			throw new IllegalArgumentException("Report IDs list cannot be null or empty");
		}
		try {
			reportsApi.deleteMultipleReportByIds(reportIds);
			return true;
		} catch (Exception e) {
			logger.debug("Failed to delete reports", e);
			return false;
		}
	}

	public Report getReport(Integer reportId) {
		try {
			com.intland.swagger.client.model.CbqlReportResponse cbqlReportResponse = reportsApi.getReportByIdInternal(reportId);
			assert cbqlReportResponse.getUri() != null;
			String id = Objects.requireNonNull(cbqlReportResponse.getUri()).replaceAll("\\D+", "");
			if (id.isEmpty()) {
				throw new IllegalArgumentException("No id found in URI");
			}
			return new Report(new ReportId(Integer.parseInt(id)), cbqlReportResponse.getName(),
					cbqlReportResponse.getQueryString());
		} catch (Exception e) {
			logger.debug("Failed to get reports", e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
