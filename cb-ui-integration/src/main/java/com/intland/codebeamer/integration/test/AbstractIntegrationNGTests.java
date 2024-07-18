package com.intland.codebeamer.integration.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.intland.codebeamer.integration.CodebeamerAssertionUtil;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.configuration.DebugConfiguration;
import com.intland.codebeamer.integration.configuration.TimeoutConfiguration;
import com.intland.codebeamer.integration.configuration.TracingConfiguration;
import com.intland.codebeamer.integration.configuration.ViewportConfiguration;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.codebeamer.screenshot.ScreenshotMaker;
import com.intland.codebeamer.screenshot.ScreenshotOptionsFactory;
import com.intland.codebeamer.screenshot.ScreenshotUriBuilder;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public abstract class AbstractIntegrationNGTests extends AbstractBaseNGTests {

	private static final Logger logger = LogManager.getLogger();

	private Playwright playwright;
	
	private Browser browser;

	private BrowserContext context;
	
	private CodebeamerPage codebeamerPage;

	@BeforeClass(alwaysRun = true)
	public void launchBrowser() throws Exception {
		LaunchOptions setHeadless = new LaunchOptions()
				.setChannel("chrome")
				.setSlowMo(getSlowMo())
				.setHeadless(isHeadless());
		
		playwright = Playwright.create();
		browser = playwright.chromium().launch(setHeadless);
		
		logger.info("Browser type: {} ({})", browser.browserType().name(), browser.version());
		generateDataBeforeClass();
	}

	@AfterClass(alwaysRun = true)
	public void closeBrowser() throws Exception {
		try {
			playwright.close();
		} finally {
			cleanUpDataAfterClass();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void createContextAndPage(ITestResult result) {
		ViewportConfiguration viewportConfiguration = getConfiguration().viewportConfiguration();
		context = browser.newContext(new Browser.NewContextOptions()
				.setViewportSize(viewportConfiguration.width(), viewportConfiguration.height()));
		
		Path traceFileUri = null;
		Path tracePreviewUrl = null;
		if (isTracing()) {
			traceFileUri = getTraceFileUrl(result);
			tracePreviewUrl = getTracePreviewUrl(result);
			context.tracing().start(new Tracing.StartOptions()
					.setScreenshots(isScreenshotsRequired())
					.setSnapshots(isSnapshotsRequired())
					.setSources(isSourcesRequired()));
		}
		
		Page page = context.newPage();
		page.setDefaultNavigationTimeout(getDefaultNavigationTimeout());
		page.setDefaultTimeout(getDefaultTimeout());
		
		if (isInspector()) {
			page.pause();
		}
				
		ScreenshotUriBuilder screenshotUriBuilder = getScreenshotUriBuilder();
		ScreenshotMaker codebeamerScreenshot = new ScreenshotMaker(page, directoryPath(), new ScreenshotOptionsFactory(), screenshotUriBuilder);
		CodebeamerAssertionUtil codebeamerAssertions = new CodebeamerAssertionUtil(page, getApplicationUrl(), traceFileUri, tracePreviewUrl, codebeamerScreenshot);
		codebeamerPage = new CodebeamerPage(getApplicationUrl(), getApplicationApiUrl(), page, codebeamerScreenshot, codebeamerAssertions);
	}

	@AfterMethod(alwaysRun = true)
	public void closeContext(ITestResult result) {
		Path path = getTraceFilePath(result);

		if (isTracing() && isFailed(result)) {
			context.tracing().stop(new Tracing.StopOptions().setPath(path));
			logger.info("Trace file has been created: {}", path.toString());
		}
		
		context.close();
		
		if (isTraceApplicationRequired() && isFailed(result)) {	
			showTraceApplication(path);
		}
		
	}

	public DataManagerService getDataManagerService() {
		return new DataManagerService(getApplicationConfiguration());
	}
	
	protected void generateDataBeforeClass() throws Exception {
		
	}
	
	protected void cleanUpDataAfterClass() throws Exception {
		
	}
	
	protected void switchToClassicUI(User activeUser) throws Exception {
		logger.info("Switch to classic UI for {}", activeUser.getName());
		new DataManagerService(getApplicationConfiguration()).getUserApiService(activeUser).switchToClassicUI();
	}
	
	protected void switchToNextgenUI(User activeUser) throws Exception {
		logger.info("Switch to nextgen UI for {}", activeUser.getName());
		new DataManagerService(getApplicationConfiguration()).getUserApiService(activeUser).switchToNextgenUI();
	}
	
	protected Path getFilePath(String reourceName) {
		try {
			return Paths.get(this.getClass().getResource(reourceName).toURI());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	protected Path getFilePath(String reourceName, String name) {
		try {
			File originalFile = new File(getFilePath(reourceName).toUri());
			File destFile = new File(createTmpDirectory(), name);
			
			FileUtils.copyFile(originalFile, destFile, true);
			
			return destFile.toPath();
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	CodebeamerPage getCodebeamerPage() {
		return codebeamerPage;
	}

	private ScreenshotUriBuilder getScreenshotUriBuilder() {
		String screenshotUri = Optional.ofNullable(this.getConfiguration().screenshotConfiguration().uri())
				.orElse(directoryPath());
		
		return randomFileName -> URI.create(isUrl(screenshotUri) ? screenshotUri.replace("_screenshot_", randomFileName)
				: "file://%s/%s".formatted(screenshotUri.replace("\\", "/"), randomFileName));
	}

	private boolean isUrl(String uri) {
		try {
			String scheme = new URI(uri).getScheme();
			return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
		} catch (Exception e1) {
			return false;
		}
	}

	private Path getTraceFilePath(ITestResult result) {
		return Paths.get(traceDirectoryPath(), "%s.zip".formatted(getTraceFileName(result)));
	}
	
	private Path getTraceFileUrl(ITestResult result) {
		return computeTraceUri(result, this.getConfiguration().tracingConfiguration().uri());
	}
	
	private Path getTracePreviewUrl(ITestResult result) {
		return computeTraceUri(result, this.getConfiguration().tracingConfiguration().previewUri());
	}

	private Path computeTraceUri(ITestResult result, String traceUri) {
		if (StringUtils.isEmpty(traceUri)) {
			return getTraceFilePath(result);
		}
		
		String fileName = "%s.zip".formatted(getTraceFileName(result));

		if (StringUtils.contains(traceUri, "_trace_")) {
			return Paths.get(StringUtils.replace(traceUri, "_trace_", fileName));
		}
		return Paths.get(traceUri, fileName);
	}
	
	private String traceDirectoryPath() {
		return Optional.ofNullable(this.getConfiguration().tracingConfiguration().traceDirectoryPath()).orElseGet(() -> {
			try {
				File file = new File(getDefaultTempDirectory(), "trace");
				FileUtils.forceMkdir(file);
				return file.getAbsolutePath();
			} catch (IOException e) {
				throw new IllegalStateException("Trace directory cannot be created");
			}
		});
	}
	
	private String directoryPath() {
		return Optional.ofNullable(this.getConfiguration().screenshotConfiguration().directoryPath()).orElse(getDefaultTempDirectory());
	}

	private double getDefaultTimeout() {
		return Optional.ofNullable(this.getConfiguration().timeoutConfiguration())
				.map(TimeoutConfiguration::defaultTimeout)
				.orElse(Long.valueOf(TimeUnit.SECONDS.toMillis(10)).doubleValue());
	}
	
	private double getDefaultNavigationTimeout() {
		return Optional.ofNullable(this.getConfiguration().timeoutConfiguration())
				.map(TimeoutConfiguration::defaultNavigationTimeout)
				.orElse(Long.valueOf(TimeUnit.SECONDS.toMillis(10)).doubleValue());
	}
	
	private Boolean isInspector() {
		return Optional.ofNullable(this.getConfiguration().debugConfiguration())
				.map(DebugConfiguration::inspector)
				.orElse(Boolean.FALSE);
	}

	private Boolean isTracing() {
		return isScreenshotsRequired() || isSnapshotsRequired() || isScreenshotsRequired();
	}
	
	private boolean isTraceApplicationRequired() {
		return isTracing() && Optional.ofNullable(this.getConfiguration().tracingConfiguration())
				.map(TracingConfiguration::openTraceApplication)
				.orElse(Boolean.FALSE);
	}
	
	private boolean isSourcesRequired() {
		return Optional.ofNullable(this.getConfiguration().tracingConfiguration())
				.map(TracingConfiguration::sources)
				.orElse(Boolean.FALSE);
	}

	private boolean isSnapshotsRequired() {
		return Optional.ofNullable(this.getConfiguration().tracingConfiguration())
				.map(TracingConfiguration::snapshots)
				.orElse(Boolean.FALSE);
	}

	private boolean isScreenshotsRequired() {
		return Optional.ofNullable(this.getConfiguration().tracingConfiguration())
				.map(TracingConfiguration::screenshots)
				.orElse(Boolean.FALSE);
	}
	
	private Boolean isHeadless() {
		return Optional.ofNullable(this.getConfiguration().debugConfiguration())
				.map(DebugConfiguration::headless)
				.orElse(Boolean.TRUE);
	}

	private Integer getSlowMo() {
		return Optional.ofNullable(this.getConfiguration().debugConfiguration())
				.map(DebugConfiguration::slowMo)
				.orElse(Integer.valueOf(0));
	}
	
	private void showTraceApplication(Path path) throws IllegalAccessError {
		try {
			com.microsoft.playwright.CLI.main(new String[] { "show-trace", path.toString() });
		} catch (Exception e) {
			throw new IllegalAccessError("Show trace window cannot be opened");
		}
	}
	
	private String getDefaultTempDirectory() {
		return System.getProperty("java.io.tmpdir");
	}
		
	private String getTraceFileName(ITestResult result) {
		String packageName = StringUtils.replace(result.getMethod().getRealClass().getPackageName(), ".", "_");
		String className = result.getMethod().getRealClass().getSimpleName();
		String methodName = result.getMethod().getMethodName();
		return "%s_%s_%s".formatted(packageName, className, methodName);
	}
	
	private boolean isFailed(ITestResult result) {
		return result.getStatus() == ITestResult.FAILURE;
	}
	
	private File createTmpDirectory() throws IOException {
		return FileUtils.createParentDirectories(new File(FileUtils.getTempDirectory(), String.valueOf(System.currentTimeMillis())));
	}
	
	@FunctionalInterface
	public interface DataManagerSupplier<T extends DataManagerService> {
		
		T create() throws Exception;
		
	}
}
