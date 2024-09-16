package com.intland.codebeamer.integration;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Locator.FillOptions;
import com.microsoft.playwright.Locator.PressSequentiallyOptions;
import com.microsoft.playwright.Mouse.MoveOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.Position;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CodebeamerLocator {

	private CodebeamerAssertionUtil assertionUtil;

	private Page page;

	private Locator locator;

	public CodebeamerLocator(CodebeamerAssertionUtil assertionUtil, Page page, Locator locator) {
		this.assertionUtil = assertionUtil;
		this.page = page;
		this.locator = locator;
	}

	public CodebeamerLocator dragBefore(CodebeamerLocator target) {
		return drag(target, 1, 0);
	}

	public CodebeamerLocator drag(CodebeamerLocator target) {
		return drag(target, 1, target.locator.boundingBox().height / 2);
	}

	public CodebeamerLocator dragAfter(CodebeamerLocator target) {
		return drag(target, 1, target.locator.boundingBox().height - 1);
	}
	
	public CodebeamerLocator drag(CodebeamerLocator target, int steps) {
		return drag(target, steps, 0);
	}
	
	public CodebeamerLocator drag(CodebeamerLocator target, int steps, double yOffset) {
		assertTrue(steps >= 1, "Steps must be greater than or equal to 1");
		assertionUtil.assertThat("Element should be dropped to an other element", () -> {
			this.locator.hover();
			target.scrollIntoView(); // In case the element is out of view we should scroll there
					
			page.mouse().down();
			page.mouse().move(target.getLocator().boundingBox().x, target.getLocator().boundingBox().y + yOffset, new MoveOptions().setSteps(steps));
			page.mouse().up();
		});
		
		return this;
	}

	/**
	 * <p>Drags the source element by clicking on its left side, moves it along the x axis (left or right)
	 * and drops it at the target location. The position to which you want to move the source element,
	 * can be given as a percentage. 0% is the left side, 100% is the right side of the target element.</p>
	 *
	 * <p>The right side of the element stays at its original position.</p>
	 * <p>The left side cannot be moved beyond the right side of the element.</p>
	 * <p>The source element should be smaller and be contained by the target element.</p>
	 *
	 * Example
	 * <p>
	 *     Hyphens represents the target element, inside the target is the source element, of which the left side is dragged.
	 * </p>
	 * <p>Before drag and drop</p>
	 * <pre>----------###########-----------</pre>
	 *
	 * <p>After drag and drop</p>
	 * <pre>------###############-----------</pre>
	 *
	 * @param target target element which contains the source element
	 * @param percent 0-100, The position to which you want to move the source element in the ratio of the target element width.
	 * @return the input source element
	 */
	public CodebeamerLocator dragLeftSide(CodebeamerLocator target, int percent) {
		assertionUtil.assertThat("Element should be drag and dropped", () -> {
			locator.hover();

			double leftSideXPosition = locator.boundingBox().x - target.getLocator().boundingBox().x;
			double rightSideXPosition = leftSideXPosition + locator.boundingBox().width;

			// adding 2 pixels to make sure the cursor is over the element
			double targetX = (target.getLocator().boundingBox().width * percent / 100) + 2;
			assertTrue(targetX <= rightSideXPosition, "Cannot move left side of the element further than the right side");

			Position leftSideOfSourceElement = new Position(2, getVerticalCenter(locator));
			Position targetPosition = new Position(targetX, getVerticalCenter(target.getLocator()));

			locator.dragTo(target.getLocator(), new Locator.DragToOptions()
					.setSourcePosition(leftSideOfSourceElement)
					.setTargetPosition(targetPosition)
					.setForce(true));
		});

		return this;
	}

	/**
	 * <p>Drags the source element by clicking on its right side, moves it along the x axis (left or right)
	 * and drops it at the target location. The position to which you want to move the source element,
	 * can be given as a percentage. 0% is the left side, 100% is the right side of the target element.</p>
	 *
	 * <p>The left side of the element stays at its original position.</p>
	 * <p>The right side cannot be moved beyond the left side of the element.</p>
	 * <p>The source element should be smaller and be contained by the target element.</p>
	 *
	 * Example
	 * <p>
	 *     Hyphens represents the target element, inside the target is the source element, of which the right side is dragged.
	 * </p>
	 * <p>Before drag and drop</p>
	 * <pre>----------###########-----------</pre>
	 *
	 * <p>After drag and drop</p>
	 * <pre>----------###############-------</pre>
	 *
	 * @param target target element which contains the source element
	 * @param percent 0-100, The position to which you want to move the source element in the ratio of the target element width.
	 * @return the input source element
	 */
	public CodebeamerLocator dragRightSide(CodebeamerLocator target, int percent) {
		assertionUtil.assertThat("Element should be drag and dropped", () -> {
			locator.hover();

			double leftSideXPosition = locator.boundingBox().x - target.getLocator().boundingBox().x;

			// adding 2 pixels to make sure the cursor is over the element
			double targetX = (target.getLocator().boundingBox().width * percent / 100) + 2;
			assertTrue(targetX > leftSideXPosition, "Cannot move right side of the element further than the left side");

			// subtracting 2 pixels to make sure the cursor is over the element
			Position rightSideOfSourceElement = new Position(locator.boundingBox().width - 2, getVerticalCenter(locator));
			Position targetPosition = new Position(targetX, getVerticalCenter(target.getLocator()));

			locator.dragTo(target.getLocator(), new Locator.DragToOptions()
					.setSourcePosition(rightSideOfSourceElement)
					.setTargetPosition(targetPosition)
					.setForce(true));
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

	public CodebeamerLocator getByText(String text) {
		return getByText(text, false);
	}

	public CodebeamerLocator getByText(String text, boolean exact) {
		return new CodebeamerLocator(assertionUtil, page, this.locator.getByText(text, new Locator.GetByTextOptions().setExact(exact)));
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

	public String value() {
		return this.locator.inputValue();
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

	public CodebeamerLocator rightClick() {
		ClickOptions rightClick = new ClickOptions().setButton(MouseButton.RIGHT);
		assertionUtil.assertThat("Element should be right clickable", () -> locator.click(rightClick));
		return this;
	}
	
	public CodebeamerLocator doubleClick() {
		return doubleClick(new Locator.DblclickOptions());
	}

	public CodebeamerLocator doubleClick(Locator.DblclickOptions options) {
		assertionUtil.assertThat("Element should be double clickable", () -> locator.dblclick(options));
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
		return repeat(pressCount, this::pressEnter);
	}

	public CodebeamerLocator pressControlLeft() {
		assertionUtil.assertThat("Ctrl + Left arrow should be pressed on item", () -> locator.press("Control+Shift+ArrowLeft"));
		return this;
	}

	public CodebeamerLocator pressControlLeft(int pressCount) {
		return repeat(pressCount, this::pressControlLeft);
	}

	public CodebeamerLocator pressControlRight() {
		assertionUtil.assertThat("Ctrl + Right arrow should be pressed on item", () -> locator.press("Control+Shift+ArrowRight"));
		return this;
	}

	public CodebeamerLocator pressControlRight(int pressCount) {
		return repeat(pressCount, this::pressControlRight);
	}

	public CodebeamerLocator repeat(int repeatCount, Runnable action) {
		IntStream.range(0, repeatCount).forEach(i -> action.run());
		return this;
	}

	public boolean isChecked() {
		return locator.isChecked();
	}

	public boolean isEnabled() {
		return locator.isEnabled();
	}

	public boolean isDisabled() {
		return locator.isDisabled();
	}

	public int count() {
		return locator.count();
	}

	/**
	 * Uses the default timeout set in the config.
	 */
	public void waitForAttached() {
		locator.waitFor(getWaitForOptions(ATTACHED));
	}

	public void waitForAttached(long timeoutInSeconds) {
		locator.waitFor(getWaitForOptions(ATTACHED)
				.setTimeout(SECONDS.toMillis(timeoutInSeconds)));
	}

	/**
	 * Uses the default timeout set in the config.
	 */
	public void waitForDetached() {
		locator.waitFor(getWaitForOptions(DETACHED));
	}

	public void waitForDetached(long timeoutInSeconds) {
		locator.waitFor(getWaitForOptions(DETACHED)
				.setTimeout(SECONDS.toMillis(timeoutInSeconds)));
	}

	private Locator.WaitForOptions getWaitForOptions(WaitForSelectorState waitForSelectorState) {
		return new Locator.WaitForOptions()
				.setState(waitForSelectorState);
	}

	/**
	 * Do not use this method from your code, it is only allowed for the framework 
	 */
	public Locator getLocator() {
		return locator;
	}

	public List<String> classes() {
		return Arrays.stream(getAttribute("class").split(" ")).toList();
	}

	public boolean hasNoChild() {
		return locator.locator(" > *").count() == 0;
	}

	private double getVerticalCenter(Locator locator) {
		return locator.boundingBox().height / 2;
	}
}
