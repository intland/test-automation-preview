package com.intland.codebeamer.integration.classic.component.actionmenubar;

import java.util.regex.Pattern;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ActionMenuBarAssertions extends AbstractCodebeamerComponentAssert<ActionMenuBarComponent, ActionMenuBarAssertions> {

	public static final Pattern BASELINE_BLUE_COLOR_GRADIENT_CSS_PATTERN = Pattern.compile(
			"linear-gradient\\(to right, rgb\\(69, 69, 69\\) 0(%|px), rgb\\(69, 69, 69\\) 50%, rgb\\(0, 147, 184\\) 66%, rgb\\(0, 147, 184\\) 100%\\)");

	public ActionMenuBarAssertions(ActionMenuBarComponent component) {
		super(component);
	}

	public ActionMenuBarAssertions summaryEqualsTo(final String summary) {
		return assertAll("Summary should be %s".formatted(summary), () ->
				assertThat(getComponent().getSummaryElement()).hasText(summary));
	}

	public ActionMenuBarAssertions idEqualsTo(final Integer id) {
		return assertAll("Id should be #%s".formatted(id), () ->
				assertThat(getComponent().getIdElement()).hasText("#%d".formatted(id)));
	}

	public ActionMenuBarAssertions hasNoCopyItemLink() {
		return assertAll("There should not be a 'Copy work item' link",
				() -> assertThat(getComponent().getCopyItemLink()).not().isAttached()
		);
	}

	public ActionMenuBarAssertions hasCopyItemLink() {
		return assertAll("There should be 'Copy work item' link", () -> {
					assertThat(getComponent().getCopyItemLink()).isVisible();
					assertThat(getComponent().getCopyItemLink()).hasCSS("cursor", "pointer");
					assertThat(getComponent().getCopyItemLink()).hasAttribute("title", "Copy Work Item Link (Alt + W)");
				}
		);
	}

	public ActionMenuBarAssertions hasCopyLinkIcon() {
		return assertAll("There is a link icon in the header, next to the item version",
				() -> {
					assertThat(getComponent().getItemVersion()).isVisible();
					assertThat(getComponent().getCopyItemLink()).isVisible();
					assertThat(getComponent().getActionMenuBarElement(
							".copy-wiki-link-to-clipboard-button:near(span.breadcrumbs-item-version, 10)")).isVisible();
				});
	}

	public ActionMenuBarAssertions copyItemLinkIsBlue() {
		return assertAll("'Copy work item' link should be blue",
				() -> assertThat(getComponent().getCopyItemLink()).hasCSS("color", "rgb(42, 176, 209)"));
	}

	public ActionMenuBarAssertions hasTooltip(String title) {
		return assertAll(
				"Hovering over the icon shows a tooltip: 'Copy Work Item Link (Alt + W)'",
				() -> {
					assertThat(getComponent().getCopyItemLink()).hasAttribute("title", title);
				});
	}

	public ActionMenuBarAssertions hasNoDocumentEditViewButton() {
		return assertAll("There should be no 'Document Edit View' button", () ->
				assertThat(getComponent().getDocumentEditViewActionIcon()).not().isVisible()
		);
	}

	public ActionMenuBarAssertions hasDocumentEditViewButton() {
		return assertAll("There should be 'Document Edit View' button present", () ->
				assertThat(getComponent().getDocumentEditViewActionIcon()).isVisible()
		);
	}

	public ActionMenuBarAssertions hasBaselineColorGradient() {
		return assertAll("Baseline color gradient should be present",
				() -> assertThat(getComponent().getSelfLocator()).hasCSS("background", BASELINE_BLUE_COLOR_GRADIENT_CSS_PATTERN));
	}

	public ActionMenuBarAssertions hasBaselineNamePresent(String baselineName) {
		return assertAll("Baseline name '%s' should be present".formatted(baselineName),
				() -> assertThat(getComponent().getBaselineNameElement()).hasText(baselineName));
	}
}
