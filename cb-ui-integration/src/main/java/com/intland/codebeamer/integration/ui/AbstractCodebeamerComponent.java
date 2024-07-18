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

	private boolean isIframe;

	public AbstractCodebeamerComponent(CodebeamerPage codebeamerPage, String componentLocator) {
		this.codebeamerPage = codebeamerPage;
		this.componentLocator = componentLocator;
	}

	public AbstractCodebeamerComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		this(codebeamerPage, frameLocator, componentLocator, false);
	}
	
	public AbstractCodebeamerComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator, boolean isIframe) {
		this.codebeamerPage = codebeamerPage;
		this.frameLocator = frameLocator;
		this.componentLocator = componentLocator;
		this.isIframe = isIframe;
	}

	protected Optional<CodebeamerLocator> locatorIfPresent(String selector) {
		CodebeamerLocator locator = locator(selector);

		try {
			PlaywrightAssertions.assertThat(locator.getLocator()).isAttached(new IsAttachedOptions().setTimeout(TimeUnit.SECONDS.toMillis(1)));
			return Optional.of(locator);
		} catch (AssertionFailedError e) {
			return Optional.empty();
		}
	}
	
	protected CodebeamerLocator locator(String selector) {
		if (isIframe) {
			return codebeamerPage.frameLocator(frameLocator, getSelector(selector));
		}
		
		if (StringUtils.isBlank(frameLocator)) {
			return codebeamerPage.locator(getSelector(selector));
		}
		
		return codebeamerPage.locator(getSelector(frameLocator, getSelector(selector)));
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
	
	public void waitForNetworkidle() {
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
		
	protected String escapeApostrophe(String text) {
		return text.replaceAll("'", "\\\\'");
	}

	protected Predicate<Response> responseEndsWith(String path) {
		return response -> {
			String url = response.url();
			return url.endsWith(path);
		};
	}
	
	protected String getSelector(String selector) {
		return getSelector(componentLocator, selector);
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
