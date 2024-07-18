package com.intland.codebeamer.integration.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.intland.swagger.client.internal.ApiClient;
import com.intland.swagger.client.internal.ServerConfiguration;
import com.intland.swagger.client.internal.auth.HttpBasicAuth;

import okhttp3.OkHttpClient;

public class CodebeamerApiClientFactory {

	private static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	private static final String BASIC_AUTH_NAME = "BasicAuth";

	public ApiClient getApiClient(String basePath, String username, String password) {
		try {

			ApiClient apiClient = authenticateApiClient(setBaseUrl(new ApiClient(), basePath), username, password)
					.setDateFormat(getApiDateFormat());

			OkHttpClient httpClient = apiClient.getHttpClient()
					.newBuilder()
					.connectTimeout(Duration.ofSeconds(30L))
					.writeTimeout(Duration.ofSeconds(30L))
					.readTimeout(Duration.ofMinutes(5L))
					.build();

			apiClient.setHttpClient(httpClient);

			return apiClient;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private ApiClient authenticateApiClient(ApiClient apiClient, String userName, String password) {
		HttpBasicAuth basicAuth = (HttpBasicAuth) apiClient.getAuthentication(BASIC_AUTH_NAME);
		basicAuth.setUsername(userName);
		basicAuth.setPassword(password);

		return apiClient;
	}

	private ApiClient setBaseUrl(ApiClient apiClient, String baseUrl) throws MalformedURLException, URISyntaxException {
		String basePath = new URI(StringUtils.removeEnd(baseUrl , "/") + "/api").toURL().toString();

		apiClient.setServers(List.of(new ServerConfiguration(basePath, "", Collections.emptyMap())));
		apiClient.setBasePath(basePath);

		return apiClient;
	}

	private DateFormat getApiDateFormat() {
		DateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT);
		dateFormat.setTimeZone(TimeZone.getDefault());
		return dateFormat;
	}

}
