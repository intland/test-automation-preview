package com.intland.codebeamer.integration.network;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import com.microsoft.playwright.Request;

public class Requests {

	private String applicationUrl;

	private List<Request> requests;

	public Requests(String applicationUrl, List<Request> requests) {
		this.applicationUrl = applicationUrl;
		this.requests = requests;
	}

	public Requests assertUrlCallExecuted(String... urls) {
		return assertUrlCallExecuted(true, urls);
	}
	
	public Requests assertUrlCallExecuted(boolean clearQueryParams, String... urls) {
		List<String> calledUrls = requests.stream()
			.filter(r -> StringUtils.isEmpty(r.failure()))
			.map(Request::url)
			.map(u -> StringUtils.removeStart(u, this.applicationUrl))
			.map(u -> clearQueryParams ? StringUtils.substringBefore(u, "?") : u)
			.toList();
		
		MatcherAssert.assertThat(calledUrls, Matchers.hasItems(urls));
		return this;
	}
	
}
