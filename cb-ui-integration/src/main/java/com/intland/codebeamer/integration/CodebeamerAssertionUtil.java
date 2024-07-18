package com.intland.codebeamer.integration;

import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.opentest4j.AssertionFailedError;

import com.intland.codebeamer.screenshot.ScreenshotMaker;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.assertions.PageAssertions.HasURLOptions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class CodebeamerAssertionUtil {

	private static final String SLASH = "/";

	private static String ERROR_MESSAGE_FORMAT = """
			%s
			Screenshot: %s
			Trace preview: %s
			Trace download: %s
			Exception:
			""";
	
	private Page page;

	private String applicationUrl;

	private Path traceFilePath;

	private Path tracePreviewUrl;

	private ScreenshotMaker codebeamerScreenshot;

	public CodebeamerAssertionUtil(Page page, String applicationUrl, Path traceFilePath, Path tracePreviewUrl, ScreenshotMaker codebeamerScreenshot) {
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

	public void assertThat(CodebeamerLocator locator, String message, Assertion assertion) {
		try {
			assertion.check();
		} catch (TimeoutError | AssertionError e) {
			URI randomFileNamePath = codebeamerScreenshot.screenshot(locator);
			String errorMessage = ERROR_MESSAGE_FORMAT.formatted(message, randomFileNamePath.toString(), tracePreviewUrl.toString(), traceFilePath.toString());
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

}
