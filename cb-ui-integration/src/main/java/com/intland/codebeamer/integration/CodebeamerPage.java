package com.intland.codebeamer.integration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;

import com.intland.codebeamer.integration.network.Requests;
import com.intland.codebeamer.integration.util.UriPathBuilder;
import com.intland.codebeamer.screenshot.ScreenshotMaker;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.LoadState;

public class CodebeamerPage {

	private String applicationUrl;

	private String applicationApiUrl;
	
	private Page page;

	private CodebeamerAssertionUtil codebeamerAssertions;

	private ScreenshotMaker codebeamerScreenshot;

	public CodebeamerPage(String applicationUrl, String applicationApiUrl, Page page, ScreenshotMaker codebeamerScreenshot,
			CodebeamerAssertionUtil codebeamerAssertions) {
		this.applicationUrl = applicationUrl;
		this.applicationApiUrl = applicationApiUrl;
		this.page = page;
		this.codebeamerScreenshot = codebeamerScreenshot;
		this.codebeamerAssertions = codebeamerAssertions;
	}

	public void navigate(String relativePath) {
		try {
			this.page.navigate(new UriPathBuilder(applicationUrl).path(relativePath).build());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public CodebeamerLocator locator(String locator) {
		return new CodebeamerLocator(this.getAssertions(), getPage(), getPage().locator(locator));
	}

	public CodebeamerLocator locatorAll(String locator) {
		return new CodebeamerLocator(this.getAssertions(), getPage(), getPage().locator(locator));
	}
	
	public List<CodebeamerLocator> frameLocatorAll(String frameLocator, String locator) {
		return new CodebeamerLocator(this.getAssertions(), getPage(), this.getPage().frameLocator(frameLocator).locator(locator)).all();
	}
	
	public CodebeamerLocator frameLocator(String frameLocator, String locator) {
		return new CodebeamerLocator(this.getAssertions(), getPage(), this.getPage().frameLocator(frameLocator).locator(locator));
	}
	
	public void uploadFiles(Runnable callback, Path... attachment) {
		try {
			this.page.waitForFileChooser(callback).setFiles(attachment);
		} catch (AssertionError e) {
			Arrays.stream(attachment).forEach(a -> FileUtils.deleteQuietly(a.toFile()));
			throw e;
		}
	}
	
	public void waitForURL(String urlPatter) {
		getPage().waitForURL(new UriPathBuilder(applicationUrl).path(urlPatter).build());
	}

	public void waitForUrlRegexp(String pattern) {
		getPage().waitForURL(url -> url.matches(pattern));
	}
	
	public void waitForResponse(Predicate<Response> predicate, int expectedStatus) {
		getPage().waitForResponse(predicate.and(responseStatus(expectedStatus)), () -> {
		});
	}
	
	public void waitForRequest(Predicate<Request> predicate, int expectedStatus) {
		getPage().waitForRequest(predicate.and(requestStatus(expectedStatus)), () -> {
		});
	}
	
	public void waitForNetworkidle() {
		getPage().waitForLoadState(LoadState.NETWORKIDLE);
	}
	
	public ScreenshotMaker getCodebeamerScreenshot() {
		return codebeamerScreenshot;
	}

	public CodebeamerAssertionUtil getAssertions() {
		return codebeamerAssertions;
	}

	public String getPageUrl() {
		return page.url();
	}

	public void pause() {
		page.pause();
	}
	
	public UriPathBuilder getUriPathBuilder() {
		return new UriPathBuilder(applicationUrl);
	}
	
	public UriPathBuilder getApiUriPathBuilder() {
		return new UriPathBuilder(applicationApiUrl);
	}

	public Requests collectRequests(Runnable action) {
		List<Request> requests = new LinkedList<Request>();
		Consumer<Request> handler = requests::add;
		
		this.getPage().onRequest(handler);
		action.run();
		this.waitForNetworkidle();
		this.getPage().offRequest(handler);
		
		return new Requests(this.applicationUrl, requests);
	}
	
	protected Page getPage() {
		return page;
	}

	private Predicate<Response> responseStatus(int status) {
		return response -> {
			int status2 = response.status();
			return status2 == status;
		};
	}
	
	private Predicate<Request> requestStatus(int status) {
		return request -> request.response().status() == status;
	}

}
