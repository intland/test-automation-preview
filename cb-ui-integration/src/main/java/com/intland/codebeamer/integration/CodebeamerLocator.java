package com.intland.codebeamer.integration;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Locator.FillOptions;
import com.microsoft.playwright.Locator.PressSequentiallyOptions;
import com.microsoft.playwright.Mouse.MoveOptions;
import com.microsoft.playwright.Page;

public class CodebeamerLocator {

	private CodebeamerAssertionUtil assertionUtil;

	private Page page;

	private Locator locator;

	public CodebeamerLocator(CodebeamerAssertionUtil assertionUtil, Page page, Locator locator) {
		this.assertionUtil = assertionUtil;
		this.page = page;
		this.locator = locator;
	}

	public CodebeamerLocator drag(CodebeamerLocator target) {
		return drag(target, 1);
	}
	
	public CodebeamerLocator drag(CodebeamerLocator target, int steps) {
		assertTrue(steps >= 1, "Steps must be greater than or equal to 1");
		assertionUtil.assertThat("Element should be dropped to an other element", () -> {
			this.locator.hover();
			target.scrollIntoView(); // In case the element is out of view we should scroll there
					
			page.mouse().down();
			page.mouse().move(target.getLocator().boundingBox().x, target.getLocator().boundingBox().y, new MoveOptions().setSteps(steps));
			page.mouse().up();	
		});
		
		return this;
	}
	
	public String getAttribute(String name) {
		return this.locator.getAttribute(name);
	}
	
	public CodebeamerLocator readonlyWrapper(Consumer<CodebeamerLocator> locatorConsumer) {
		locator.evaluate("el => el.removeAttribute('readonly')");
		try {
			locatorConsumer.accept(this);
		} finally {
			this.locator.evaluate("el => el.setAttribute('readonly', 'readonly')");
		}
		return this;
	}
	
	public CodebeamerLocator concat(String locator) {
		return new CodebeamerLocator(assertionUtil, page, this.locator.locator(page.locator(locator)));
	}

	public CodebeamerLocator fill(String value) {
		fill(value, null);
		return this;
	}

	public CodebeamerLocator clear() {
		assertionUtil.assertThat("Value should be cleared", () -> locator.clear());
		return this;
	}
	
	public CodebeamerLocator selectOption(String... value) {
		assertionUtil.assertThat("Value should be selected to given field", () -> locator.selectOption(value));
		return this;
	}
	
	public CodebeamerLocator type(String value) {
		type(value, null);
		return this;
	}
	
	public String text() {
		return this.locator.textContent();
	}

	public List<CodebeamerLocator> all() {
		return this.locator.all().stream().map(l -> new CodebeamerLocator(assertionUtil, page, l)).toList();
	}
	
	public CodebeamerLocator type(String value, PressSequentiallyOptions options) {
		assertionUtil.assertThat("Value should be set to given field", () -> locator.pressSequentially(value, options));
		return this;
	}
	
	public CodebeamerLocator fill(String value, FillOptions options) {
		assertionUtil.assertThat("Value should be set to given field", () -> locator.fill(value, options));
		return this;
	}

	public CodebeamerLocator click() {
		assertionUtil.assertThat("Element should be clickable", () -> locator.click());
		return this;
	}

	public CodebeamerLocator click(ClickOptions clickOptions) {
		assertionUtil.assertThat("Element should be clickable", () -> locator.click(clickOptions));
		return this;
	}
	
	public CodebeamerLocator doubleClick() {
		assertionUtil.assertThat("Element should be double clickable", () -> locator.dblclick());
		return this;
	}

	public CodebeamerLocator hover() {
		assertionUtil.assertThat("Element should be in hover state", () -> locator.hover());
		return this;
	}
	
	public CodebeamerLocator scrollIntoView() {
		assertionUtil.assertThat("Page should be scrolled into view", () -> locator.scrollIntoViewIfNeeded());
		return this;
	}

	public CodebeamerLocator press(String value) {
		assertionUtil.assertThat("Item should be pressed with: %s".formatted(value), () -> locator.press(value));
		return this;
	}

	public CodebeamerLocator check(boolean value) {
		assertionUtil.assertThat("Checkbox should be: %s".formatted(value), () -> locator.setChecked(value));
		return this;
	}

	public CodebeamerLocator pressEnter() {
		assertionUtil.assertThat("Enter should be pressed on item", () -> locator.press("Enter"));
		return this;
	}

	public CodebeamerLocator pressEnter(int pressCount) {
		return repeate(pressCount, this::pressEnter);
	}

	public CodebeamerLocator pressControlLeft() {
		assertionUtil.assertThat("Ctrl + Left arrow should be pressed on item", () -> locator.press("Control+ArrowLeft"));
		return this;
	}

	public CodebeamerLocator pressControlLeft(int pressCount) {
		return repeate(pressCount, this::pressControlLeft);
	}

	public CodebeamerLocator pressControlRight() {
		assertionUtil.assertThat("Ctrl + Right arrow should be pressed on item", () -> locator.press("Control+ArrowRight"));
		return this;
	}

	public CodebeamerLocator pressControlRight(int pressCount) {
		return repeate(pressCount, this::pressControlRight);
	}

	public CodebeamerLocator repeate(int repeatCount, Runnable action) {
		IntStream.range(0, repeatCount).forEach(i -> action.run());
		return this;
	}

	public int count() {
		return locator.count();
	}

	/**
	 * Do not use this method from your code, it is only allowed for the framework 
	 */
	public Locator getLocator() {
		return locator;
	}

}
