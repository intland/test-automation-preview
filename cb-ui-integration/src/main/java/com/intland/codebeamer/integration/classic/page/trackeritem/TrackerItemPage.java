package com.intland.codebeamer.integration.classic.page.trackeritem;

import java.util.Objects;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.component.FooterComponent;
import com.intland.codebeamer.integration.classic.component.OverlayMessageBoxComponent;
import com.intland.codebeamer.integration.classic.component.field.read.TestRunResultsPluginComponent;
import com.intland.codebeamer.integration.classic.component.loadingIndicator.LoadingIndicatorComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.CommentsAndAttachmentsTabAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.CommentsAndAttachmentsTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TestCasesAndSetsTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TestStepsTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarComponent;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemBreadcrumbAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemBreadcrumbComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemDownstreamReferencesTabAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemDownstreamReferencesTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldFormAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldFormComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemHistoryTabComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemTraceabilitySectionComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.statetransition.OverlayStateTransitionComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemPage")
public class TrackerItemPage extends AbstractCodebeamerPage<TrackerItemPage> {

	private static final String TRACKER_ITEM_URL_PATTERN = ".*/(issue|item)/%d.*";
	private static final String TRACKER_ITEM_URL_WITH_REVISION_PATTERN = ".*/(issue|item)/%d\\?revision=%d.*";
	private static final String TRACKER_ITEM_URL_WITH_TRACEABILITY_TAB_PATTERN = "item/%s#task-traceability-tab";
	private static final String TRACKER_ITEM_URL_WITH_REVISION_AND_TRACEABILITY_TAB_PATTERN = "item/%s?revision=%d#task-traceability-tab";

	private final TrackerItemId trackerItemId;

	private final Baseline baseline;

	@Component("Action menu bar")
	private final ActionMenuBarComponent trackerItemActionMenuBarComponent;

	@Component("Breadcrumb")
	private final TrackerItemBreadcrumbComponent breadcrumbComponent;

	@Component("Action bar")
	private final TrackerItemActionBarComponent trackerItemActionBarComponent;

	@Component(value = "Field form", details = "Can be used to assert field values and to inline edit fields")
	private final TrackerItemFieldFormComponent trackerItemFieldFormComponent;

	@Component("Traceability section")
	private final TrackerItemTraceabilitySectionComponent trackerItemTraceabilitySectionComponent;

	@Component("Test steps tab")
	private final TestStepsTabComponent testStepsTabComponent;

	@Component("Comments and attachments tab")
	private final CommentsAndAttachmentsTabComponent commentsAndAttachmentsTabComponent;

	@Component("Downstream references tab")
	private final TrackerItemDownstreamReferencesTabComponent downstreamReferencesTabComponent;

	@Component("History tab")
	private final TrackerItemHistoryTabComponent trackerItemHistoryTabComponent;

	@Component("Test cases and sets tab")
	private final TestCasesAndSetsTabComponent testCasesAndSetsTabComponent;

	@Component("Overlay messages")
	private final OverlayMessageBoxComponent overlayMessageBoxComponent;

	@Component("Footer")
	private final FooterComponent footerComponent;

	@Component("Test run results")
	private final TestRunResultsPluginComponent testRunResultsPluginComponent;

	@Component("Overlay state transition dialog")
	private final OverlayStateTransitionComponent overlayStateTransitionComponent;

	public TrackerItemPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId) {
		this(codebeamerPage, trackerId, trackerItemId, null);
	}

	public TrackerItemPage(CodebeamerPage codebeamerPage, TrackerId trackerId, TrackerItemId trackerItemId, Baseline baseline) {
		super(codebeamerPage);
		this.trackerItemId = trackerItemId;
		this.baseline = baseline;
		this.trackerItemActionMenuBarComponent = new ActionMenuBarComponent(codebeamerPage);
		this.breadcrumbComponent = new TrackerItemBreadcrumbComponent(codebeamerPage);
		this.trackerItemActionBarComponent = new TrackerItemActionBarComponent(codebeamerPage, trackerId, trackerItemId);
		this.trackerItemFieldFormComponent = new TrackerItemFieldFormComponent(codebeamerPage);
		this.trackerItemTraceabilitySectionComponent = new TrackerItemTraceabilitySectionComponent(codebeamerPage);
		this.testStepsTabComponent = new TestStepsTabComponent(getCodebeamerPage());
		this.commentsAndAttachmentsTabComponent = new CommentsAndAttachmentsTabComponent(getCodebeamerPage(), trackerId, this.trackerItemId);
		this.downstreamReferencesTabComponent = new TrackerItemDownstreamReferencesTabComponent(getCodebeamerPage());
		this.trackerItemHistoryTabComponent = new TrackerItemHistoryTabComponent(getCodebeamerPage());
		this.testCasesAndSetsTabComponent = new TestCasesAndSetsTabComponent(getCodebeamerPage());
		this.overlayMessageBoxComponent = new OverlayMessageBoxComponent(getCodebeamerPage());
		this.footerComponent = new FooterComponent(codebeamerPage);
		this.testRunResultsPluginComponent = new TestRunResultsPluginComponent(getCodebeamerPage());
		this.overlayStateTransitionComponent = new OverlayStateTransitionComponent(getCodebeamerPage(), trackerId, trackerItemId);
	}

	@Action("Visit")
	public TrackerItemPage visit() {
		this.navigate(createUrl());
		getCodebeamerPage().waitForNetworkidle();
		return isActive();
	}

	@Action("VisitTraceabilityTab")
	public TrackerItemPage visitTraceabilityTab() {
		navigate(createUrlForTraceabilityTab());
		return isActiveOnTraceabilityTab();
	}

	@Action("Edit")
	public TrackerItemEditPage edit() {
		return trackerItemActionBarComponent.edit();
	}

	@Action("CreateItemFromTemplate")
	public TrackerItemEditPage createItemFromTemplate() {
		return trackerItemActionBarComponent.createItemFromTemplate();
	}
	
	@Override
	public TrackerItemPage isActive() {
		getCodebeamerPage().waitForUrlRegexp(createUrlPattern());
		return this;
	}

	public TrackerItemPage isActiveOnTraceabilityTab() {
		assertUrl(createUrlForTraceabilityTab(), "Tracker Item page's Traceability Tab should be the active page");
		return this;
	}

	public TrackerItemPage hasCommentIdInUrl(int commentId) {
		assertUrl("/issue/%d#comment-%d".formatted(
				Integer.valueOf(trackerItemId.id()), Integer.valueOf(commentId)), "Tracker item page should be the active page");
		return this;
	}

	public TrackerItemPage fieldFormComponent(Consumer<TrackerItemFieldFormComponent> fromConsumer) {
		fromConsumer.accept(trackerItemFieldFormComponent);
		return this;
	}

	public TrackerItemPage assertBreadcrumb(Consumer<TrackerItemBreadcrumbAssertions> assertion) {
		assertion.accept(breadcrumbComponent.assertThat());
		return this;
	}

	public TrackerItemPage assertFieldFormComponent(Consumer<TrackerItemFieldFormAssertions> assertion) {
		assertion.accept(trackerItemFieldFormComponent.assertThat());
		return this;
	}

	public TrackerItemFieldFormComponent fieldFormComponent() {
		return trackerItemFieldFormComponent;
	}

	public TrackerItemPage traceabilitySection(Consumer<TrackerItemTraceabilitySectionComponent> componentConsumer) {
		componentConsumer.accept(trackerItemTraceabilitySectionComponent);
		return this;
	}

	public TrackerItemPage assertTraceabilitySection(Consumer<TrackerItemTraceabilitySectionAssertions> assertion) {
		new LoadingIndicatorComponent(getCodebeamerPage()).waitForDetached();
		assertion.accept(trackerItemTraceabilitySectionComponent.assertThat());
		return this;
	}

	public TrackerItemPage changeToTestStepsTab() {
		testStepsTabComponent.activateTab();
		return this;
	}

	public TestStepsTabComponent testStepsTabComponent() {
		return testStepsTabComponent;
	}

	public TrackerItemPage changeToCommentsAndAttachmentsTab() {
		commentsAndAttachmentsTabComponent.activateTab();
		return this;
	}

	public CommentsAndAttachmentsTabComponent commentsAndAttachmentsTabComponent() {
		return commentsAndAttachmentsTabComponent;
	}

	public TrackerItemPage changeToHistoryTab() {
		trackerItemHistoryTabComponent.activateTab();
		getLoadingDialogElement().waitForDetached();
		return this;
	}

	public TrackerItemPage historyTabComponent(Consumer<TrackerItemHistoryTabComponent> fromConsumer) {
		fromConsumer.accept(trackerItemHistoryTabComponent);
		return this;
	}

	public TrackerItemPage changeToTestCasesAndSetsTab() {
		this.testCasesAndSetsTabComponent.activateTab();
		return this;
	}

	public TrackerItemPage testCasesAndSetsTabComponent(Consumer<TestCasesAndSetsTabComponent> testCasesAndSetsTabComponentConsumer) {
		testCasesAndSetsTabComponentConsumer.accept(testCasesAndSetsTabComponent);
		return this;
	}

	public TestCasesAndSetsTabComponent getTestCasesAndSetsTabComponent() {
		return this.testCasesAndSetsTabComponent;
	}

	public TrackerItemPage actionMenuBarComponent(Consumer<ActionMenuBarComponent> componentConsumer) {
		componentConsumer.accept(trackerItemActionMenuBarComponent);
		return this;
	}

	public TrackerItemActionBarComponent actionBarComponent() {
		return this.trackerItemActionBarComponent;
	}

	public TestRunResultsPluginComponent getTestRunResultsPluginComponent() {
		return this.testRunResultsPluginComponent;
	}

	public TrackerItemPage actionBarComponentAssertion(Consumer<TrackerItemActionBarAssertions> assertion){
		assertion.accept(trackerItemActionBarComponent.assertThat());
		return this;
	}

	public TrackerItemPage commentsAndAttachmentsTabComponent(Consumer<CommentsAndAttachmentsTabComponent> componentConsumer) {
		componentConsumer.accept(commentsAndAttachmentsTabComponent);
		return this;
	}

	public TrackerItemPage commentsAndAttachmentsTabAssertion(Consumer<CommentsAndAttachmentsTabAssertions> assertion) {
		assertion.accept(commentsAndAttachmentsTabComponent.assertThat());
		return this;
	}

	public TrackerItemPage assertDownstreamReferencesTab(Consumer<TrackerItemDownstreamReferencesTabAssertions> assertion) {
		assertion.accept(downstreamReferencesTabComponent.assertThat());
		return this;
	}

	public TrackerItemPage overlayMessageBoxComponent(Consumer<OverlayMessageBoxComponent> formConsumer) {
		formConsumer.accept(overlayMessageBoxComponent);
		return this;
	}

	public TrackerItemPage footerComponent(Consumer<FooterComponent> componentConsumer) {
		componentConsumer.accept(footerComponent);
		return this;
	}

	public OverlayStateTransitionComponent initiateOverlayStateTransition(String stateTransition) {
		getTransitionMenuElement().click();
		getTransitionMenuElement().concat("a:has-text('%s')".formatted(stateTransition)).click();
		getLoadingDialogElement().waitForDetached();
		return overlayStateTransitionComponent;
	}

	private CodebeamerLocator getTransitionMenuElement() {
		return getCodebeamerPage().locator("span.transitionMenuContainer");
	}

	private CodebeamerLocator getLoadingDialogElement() {
		return getCodebeamerPage().locator(".showBusySignDialog");
	}

	private String createUrl() {
		return Objects.isNull(baseline)
				? "/issue/%s".formatted(Integer.valueOf(trackerItemId.id()))
				: "/issue/%s?revision=%s".formatted(Integer.valueOf(trackerItemId.id()), Integer.valueOf(baseline.id().id()));
	}

	private String createUrlPattern() {
		return Objects.isNull(baseline)
				? TRACKER_ITEM_URL_PATTERN.formatted(Integer.valueOf(trackerItemId.id()))
				: TRACKER_ITEM_URL_WITH_REVISION_PATTERN.formatted(Integer.valueOf(trackerItemId.id()), Integer.valueOf(baseline.id().id()));
	}

	private String createUrlForTraceabilityTab() {
		return baseline == null ?
				TRACKER_ITEM_URL_WITH_TRACEABILITY_TAB_PATTERN.formatted(trackerItemId.id()) :
				TRACKER_ITEM_URL_WITH_REVISION_AND_TRACEABILITY_TAB_PATTERN.formatted(trackerItemId.id(), baseline.id().id());
	}
}
