package com.intland.codebeamer.integration.classic.component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class OverlayMessageBoxComponent
		extends AbstractCodebeamerComponent<OverlayMessageBoxComponent, OverlayMessageBoxAssertion> {

	private static final String SUCCESS_MESSAGE_SELECTOR = ".overlayMessageBox.notification";

	private static final Pattern TRACKER_ITEM_ID_PATTERN = Pattern.compile("/issue/(\\d+)");

	public OverlayMessageBoxComponent(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, ".overlayMessageBoxContainer");
	}

	public CodebeamerLocator getErrorMessageElement() {
		return this.locator(".overlayMessageBox.error");		
	}

	public CodebeamerLocator getSuccessMessageElement() {
		return this.locator(SUCCESS_MESSAGE_SELECTOR);
	}

	public CodebeamerLocator getTrackerItemLinkElementFromSuccessMessage() {
		return this.locator("%s a[href*='/issue'][onclick*='testRuns.runTestRunById']".formatted(SUCCESS_MESSAGE_SELECTOR));
	}

	public Integer getTrackerItemIdFromSuccessMessage() {
		String link = getTrackerItemLinkElementFromSuccessMessage().getAttribute("href");
		Matcher matcher = TRACKER_ITEM_ID_PATTERN.matcher(link);
		if (!matcher.find()) {
			throw new IllegalStateException("the link %s does not contain an id".formatted(link));
		}
		return Integer.valueOf(matcher.group(1));
	}

	@Override
	public OverlayMessageBoxAssertion assertThat() {
		return new OverlayMessageBoxAssertion(this);
	}

}
