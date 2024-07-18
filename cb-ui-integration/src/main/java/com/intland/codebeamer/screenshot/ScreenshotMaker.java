package com.intland.codebeamer.screenshot;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.options.ScreenshotType;

public class ScreenshotMaker {

	private Page page;

	private ScreenshotOptionsFactory screenshotOptionsFactory;

	private ScreenshotType screenshotType;

	private String screenshotDirectory;

	private ScreenshotUriBuilder screenshotUriBuilder;

	public ScreenshotMaker(Page page, String screenshotDirectory, ScreenshotOptionsFactory screenshotOptionsFactory, ScreenshotUriBuilder screenshotUriBuilder) {
		this.page = page;
		this.screenshotDirectory = screenshotDirectory;
		this.screenshotOptionsFactory = screenshotOptionsFactory;
		this.screenshotType = ScreenshotType.PNG;
		this.screenshotUriBuilder = screenshotUriBuilder;
	}

	public URI screenshot(List<CodebeamerLocator> locators) {
		String randomFileName = getRandomFileName();
		createScreenshot(locators, Paths.get(this.screenshotDirectory, randomFileName));
		return screenshotUriBuilder.build(randomFileName);
	}
	
	public URI screenshot(CodebeamerLocator locator) {
		return screenshot(Optional.ofNullable(locator).map(List::of).orElse(Collections.emptyList()));
	}
	
	public URI screenshot() {
		return screenshot(Collections.emptyList());
	}

	private String getRandomFileName() {
		return UUID.randomUUID().toString() + "." + screenshotType.name().toLowerCase();
	}

	private void createScreenshot(List<CodebeamerLocator> locators, Path randomFileNamePath) {
		page.screenshot(getScreenshotOptions(locators, randomFileNamePath));
	}

	private ScreenshotOptions getScreenshotOptions(List<CodebeamerLocator> locators, Path randomFileNamePath) {
		if (CollectionUtils.isEmpty(locators)) {
			return screenshotOptionsFactory.create(randomFileNamePath, screenshotType);			
		}
		return screenshotOptionsFactory.create(locators, randomFileNamePath, screenshotType);	
	}
	
}
