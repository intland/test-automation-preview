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

package com.intland.codebeamer.integration.ui;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opentest4j.AssertionFailedError;

import com.intland.codebeamer.integration.CodebeamerAssertionUtil;
import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.network.Requests;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.LocatorAssertions.IsAttachedOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public abstract class AbstractCodebeamerComponent<C extends AbstractCodebeamerComponent<C, A>, A extends AbstractCodebeamerComponentAssert<C, A>> {

    private static final Logger logger = LogManager.getLogger(AbstractCodebeamerComponent.class);
	
	private final CodebeamerPage codebeamerPage;

	private String frameLocator;
	
	private final String componentLocator;

	protected AbstractCodebeamerComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		this(codebeamerPage, null, componentLocator);
	}

	protected AbstractCodebeamerComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		this.codebeamerPage = codebeamerPage;
		this.frameLocator = frameLocator;
		this.componentLocator = componentLocator;
	}

	protected Optional<CodebeamerLocator> locatorIfPresent(String selector, long timeout) {
		CodebeamerLocator locator = locator(selector);

		try {
			PlaywrightAssertions.assertThat(locator.getLocator()).isAttached(new IsAttachedOptions().setTimeout(TimeUnit.SECONDS.toMillis(timeout)));
			return Optional.of(locator);
		} catch (AssertionFailedError e) {
			return Optional.empty();
		}
	}

	protected Optional<CodebeamerLocator> locatorIfPresent(String selector) {
		return locatorIfPresent(selector, 1);
	}

	protected CodebeamerLocator locatorByTestId(String testId) {
		return codebeamerPage.locatorByTestId(testId);
	}
	
	protected CodebeamerLocator locator(String selector) {
		if (StringUtils.isNotBlank(frameLocator)) {
			return codebeamerPage.frameLocator(frameLocator, getSelector(selector));
		}
		
		return codebeamerPage.locator(getSelector(selector));
	}
	
	protected Requests collectRequests(Runnable action) {
		return codebeamerPage.collectRequests(action);
	}
	
	protected void waitForPutApiResponse(String requestUrl, int expectedStatus) {
		waitForApiResponse(requestUrl, "PUT", expectedStatus);
	}
		
	protected void waitForApiResponse(String requestUrl, String requestMethod, int expectedStatus) {
		String absoluteRequestUrl = codebeamerPage.getApiUriPathBuilder().path(requestUrl).build();
		codebeamerPage.waitForResponse(isRequestEqualsIgnoreCase(absoluteRequestUrl).and(isRequestMethod(requestMethod)), expectedStatus);
	}

	public void waitForResponse(Predicate<Response> predicate, int expectedStatus) {
		codebeamerPage.waitForResponse(predicate, expectedStatus);
	}

	public void waitForResponse(Predicate<Response> predicate, int expectedStatus, double timeout) {
		codebeamerPage.waitForResponse(predicate, expectedStatus, timeout);
	}

	public void waitForNetworkIdle() {
		codebeamerPage.waitForNetworkidle();
	}
	
	public C sleep(long seconds) {
		try {
			Thread.sleep(Duration.ofSeconds(seconds));
			return getMySelf();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public CodebeamerPage getCodebeamerPage() {
		return this.codebeamerPage;
	}
	
	public CodebeamerAssertionUtil getAssertions() {
		return this.codebeamerPage.getAssertions();
	}

	public abstract A assertThat();

	public boolean hasNoChild() {
		return this.selfLocator().hasNoChild();
	}

	protected String getFrameLocator() {
		return frameLocator;
	}

	public CodebeamerLocator getLocator() {
		return this.locator("");
	}

	protected String escapeApostrophe(String text) {
		return text.replaceAll("'", "\\\\'");
	}

	protected Predicate<Response> urlEndsWith(String path) {
		return response -> {
			String url = response.url().split("\\?")[0];
			return url.endsWith(path);
		};
	}

	protected String getSelector() {
		return componentLocator;
	}

	protected String getSelector(String selector) {
		if (StringUtils.isBlank(selector)) {
			return componentLocator;
		}

		return getSelector(componentLocator, selector);
	}

	protected CodebeamerLocator selfLocator() {
		return this.locator("");
	}
	
	private String getSelector(String selectorParent, String selector) {
		return "%s %s".formatted(selectorParent, selector);
	}
	
	private C getMySelf() {
		return (C) this;
	}

	private Predicate<Response> isRequestEqualsIgnoreCase(String expectedUrl) {
		return response -> {
			logger.debug("Expected:{} - Actual:{}", expectedUrl, response.url());
			return response.url().equalsIgnoreCase(expectedUrl);
		};
	}
	
	private Predicate<Response> isRequestMethod(String expectedMethod) {
		return response -> {
			String actualMethod = response.request().method();
			logger.debug("Expected:{} - Actual:{} - Url: {}", expectedMethod, actualMethod, response.url());
			return actualMethod.equalsIgnoreCase(expectedMethod);
		};
	}
}
