package com.intland.codebeamer.integration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.intland.codebeamer.integration.network.Requests;
import com.intland.codebeamer.integration.test.testdata.CopiedFile;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
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

	private DataManagerService dataManagerService;

	public CodebeamerPage(String applicationUrl, String applicationApiUrl, Page page, ScreenshotMaker codebeamerScreenshot,
			CodebeamerAssertionUtil codebeamerAssertions, DataManagerService dataManagerService) {
		this.applicationUrl = applicationUrl;
		this.applicationApiUrl = applicationApiUrl;
		this.page = page;
		this.codebeamerScreenshot = codebeamerScreenshot;
		this.codebeamerAssertions = codebeamerAssertions;
		this.dataManagerService = dataManagerService;
	}

	public void navigate(String relativePath) {
		this.getAssertions().assertThat("User should be navigated to %s".formatted(relativePath),
				() -> this.page.navigate(new UriPathBuilder(applicationUrl).path(relativePath).build()));
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

	public CodebeamerLocator locatorByTestId(String testId) {
		return new CodebeamerLocator(this.getAssertions(), getPage(), this.getPage().getByTestId(testId));
	}
	
	public void uploadFiles(Runnable callback, CopiedFile... attachment) {
		try {
			Path[] paths = Arrays.stream(attachment)
					.map(CopiedFile::file)
					.toArray(Path[]::new);
			
			Arrays.stream(paths)
				.forEach(p -> assertTrue(p.toFile().exists(), "%s does not exist".formatted(p)));
			
			uploadFiles(callback, paths);
		} catch (AssertionError e) {
			Arrays.stream(attachment)
				.map(CopiedFile::file)
				.forEach(a -> FileUtils.deleteQuietly(a.toFile()));
			throw e;
		}
	}
	
	public void uploadFiles(Runnable callback, Path... attachment) {
		assertNotNull(attachment, "Attachment array cannot be null");
		
		String pathsAsString = Arrays.stream(attachment)
				.map(Path::toString)
				.collect(Collectors.joining(", "));
		
		codebeamerAssertions.assertThat("Files should be selected. Files: %s".formatted(pathsAsString), 
				() -> this.page.waitForFileChooser(callback).setFiles(attachment));
	}

	public void waitForURLStartsWith(String url) {
		waitForURL(url + "*");
	}

	public void waitForURL(String urlPattern) {
		codebeamerAssertions.assertThat("Page should be loaded. urlPattern: %s".formatted(urlPattern),
				() -> getPage().waitForURL(new UriPathBuilder(applicationUrl).path(urlPattern).build()));
	}

	public void waitForUrlRegexp(String pattern) {
		codebeamerAssertions.assertThat("Page should be loaded. pattern: %s".formatted(pattern), 
				() -> getPage().waitForURL(url -> url.matches(pattern)));
	}
	
	public void waitForResponse(Predicate<Response> predicate, int expectedStatus) {
		codebeamerAssertions.assertThat("Response should be executed", 
				() -> getPage().waitForResponse(predicate.and(responseStatus(expectedStatus)), () -> {}
			));
	}

	public void waitForResponse(Predicate<Response> predicate, int expectedStatus, double timeout) {
		Page.WaitForResponseOptions waitForResponseOptions = new Page.WaitForResponseOptions();
		waitForResponseOptions.setTimeout(timeout);

		waitForResponse(predicate, expectedStatus, waitForResponseOptions);
	}

	public void waitForResponse(Predicate<Response> predicate, int expectedStatus, Page.WaitForResponseOptions waitForResponseOptions) {
		codebeamerAssertions.assertThat("Response should be executed",
				() -> getPage().waitForResponse(predicate.and(responseStatus(expectedStatus)), waitForResponseOptions, () -> {}));
	}
	
	public void waitForRequest(Predicate<Request> predicate, int expectedStatus) {
		codebeamerAssertions.assertThat("Request should be executed", 
				() -> getPage().waitForRequest(predicate.and(requestStatus(expectedStatus)), () -> {
		}));
	}
	
	public void waitForNetworkidle() {
		codebeamerAssertions.assertThat("Network usage should be ended", () -> getPage().waitForLoadState(LoadState.NETWORKIDLE));
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

	public void setDefaultTimeout(long timeoutInSeconds) {
		this.getPage().setDefaultTimeout(SECONDS.toMillis(timeoutInSeconds));
	}

	public void setDefaultNavigationTimeout(long timeoutInSeconds) {
		this.getPage().setDefaultNavigationTimeout(SECONDS.toMillis(timeoutInSeconds));
	}

	public DataManagerService getDataManagerService() {
		return dataManagerService;
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

	public void pressKey(String key) {
		this.page.keyboard().press(key);
	}

	public void holdKey(String key) {
		this.page.keyboard().down(key);
	}

	public void releaseKey(String key) {
		this.page.keyboard().up(key);
	}

	public void holdControlKey() {
		this.holdKey("Control");
	}

	public void releaseControlKey() {
		this.releaseKey("Control");
	}
	
	private Predicate<Request> requestStatus(int status) {
		return request -> request.response().status() == status;
	}

}
