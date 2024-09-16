package com.intland.codebeamer.integration;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.opentest4j.AssertionFailedError;

import com.intland.codebeamer.screenshot.ScreenshotMaker;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PageAssertions.HasURLOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class CodebeamerAssertionUtil {

	private static final String SLASH = "/";

	private static String ERROR_MESSAGE_FORMAT = """
			%s
			
			[Screenshot|%s]
			
			[Trace preview|%s]
			
			[Trace download|%s]
			""";
	
	private Page page;

	private String applicationUrl;

	private String traceFilePath;

	private String tracePreviewUrl;

	private ScreenshotMaker codebeamerScreenshot;

	public CodebeamerAssertionUtil(Page page, String applicationUrl, String traceFilePath, String tracePreviewUrl, ScreenshotMaker codebeamerScreenshot) {
		this.page = page;
		this.applicationUrl = applicationUrl;
		this.traceFilePath = traceFilePath;
		this.tracePreviewUrl = tracePreviewUrl;
		this.codebeamerScreenshot = codebeamerScreenshot;
	}

	public void assertUrl(String expected, String message) {
		assertThat(message, () -> PlaywrightAssertions.assertThat(page)
				.hasURL(concatToBaseUrl(expected), new HasURLOptions().setIgnoreCase(true).setTimeout(TimeUnit.MINUTES.toMillis(1))));
	}

	public void assertUrl(Pattern expected, String message) {
		assertThat(message, () -> PlaywrightAssertions.assertThat(page)
				.hasURL(expected, new HasURLOptions().setIgnoreCase(true).setTimeout(TimeUnit.MINUTES.toMillis(1))));
	}

	public void assertThat(CodebeamerLocator locator, String message, Assertion assertion) {
		try {
			assertion.check();
		} catch (Error | Exception e) {
			URI randomFileNamePath = codebeamerScreenshot.screenshot(locator);
			String errorMessage = ERROR_MESSAGE_FORMAT.formatted(message, randomFileNamePath.toString(), getTracePreviewUrl(), getTraceFilePath());
			throw new AssertionError(errorMessage, e);
		}
	}
	
	public void assertThat(String message, Assertion assertion) {
		assertThat(null, message, assertion);
	}

	private String concatToBaseUrl(String expected) {
		return StringUtils.removeEnd(applicationUrl, SLASH) + SLASH + StringUtils.removeStart(expected, SLASH);
	}
	
	@FunctionalInterface
	public interface Assertion {
		void check() throws AssertionFailedError;
	}

	private String getTraceFilePath() {
		if (traceFilePath == null) {
			return StringUtils.EMPTY;
		}
		return traceFilePath;
	}

	private String getTracePreviewUrl() {
		if (tracePreviewUrl == null) {
			return StringUtils.EMPTY;
		}
		return tracePreviewUrl;
	}
	
}
