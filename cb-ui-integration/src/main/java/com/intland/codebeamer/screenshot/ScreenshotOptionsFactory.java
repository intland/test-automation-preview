package com.intland.codebeamer.screenshot;

import java.nio.file.Path;
import java.util.List;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.ScreenshotOptions;
import com.microsoft.playwright.options.ScreenshotType;

public class ScreenshotOptionsFactory {

	public ScreenshotOptions create(Path screenshotPath, ScreenshotType screenshotType) {
		return new Page.ScreenshotOptions()
				.setType(screenshotType)
				.setFullPage(true)
				.setPath(screenshotPath);
	}
	
	public ScreenshotOptions create(CodebeamerLocator locator, Path screenshotPath, ScreenshotType screenshotType) {
		return create(List.of(locator), screenshotPath, screenshotType);
	}
	
	public ScreenshotOptions create(List<CodebeamerLocator> locator, Path screenshotPath, ScreenshotType screenshotType) {
		return create(screenshotPath, screenshotType).setMask(locator.stream().map(CodebeamerLocator::getLocator).toList());
	}
	
}
