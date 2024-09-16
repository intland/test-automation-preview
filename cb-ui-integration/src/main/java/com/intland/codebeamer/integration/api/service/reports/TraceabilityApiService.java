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

import java.util.Objects;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.TraceabilityApi;
import com.intland.swagger.client.model.Identifier;
import com.intland.swagger.client.model.TraceabilitySaveReportRequest;

public class TraceabilityApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger(TraceabilityApiService.class);

	private TraceabilityApi traceabilityApi;

	public TraceabilityApiService(ApplicationConfiguration applicationConfiguration) {
		super(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public TraceabilityApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public TraceabilityApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public TraceabilityApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.traceabilityApi = new TraceabilityApi(getUserApiClient());
	}

	public Report savePresetReport(String reportName,
			Function<TraceabilitySaveReportRequestBuilder, TraceabilitySaveReportRequestBuilder> builder) {
		TraceabilitySaveReportRequest traceabilitySaveReportRequest = builder.apply(
				new TraceabilitySaveReportRequestBuilder(new TraceabilitySaveReportRequest().name(reportName))).build();
		try {
			Identifier report = traceabilityApi.savePresetReport(traceabilitySaveReportRequest);
			return new Report(new ReportId(Objects.requireNonNull(report.getId()).intValue()), reportName,
					traceabilitySaveReportRequest.getCbQL());
		} catch (Exception e) {
			logger.debug("Failed to save preset report", e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
